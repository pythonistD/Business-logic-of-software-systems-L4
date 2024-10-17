package cheboksarov.blps_lab4.service.impl;

import cheboksarov.blps_lab4.deserializer.DoBetRequestDeserializer;
import cheboksarov.blps_lab4.dto.DoBetRequest;
import cheboksarov.blps_lab4.model.*;
import cheboksarov.blps_lab4.repository.BetRepository;
import cheboksarov.blps_lab4.repository.CredentialRepository;
import cheboksarov.blps_lab4.service.BetService;
import cheboksarov.blps_lab4.service.CredentialService;
import cheboksarov.blps_lab4.service.MatchService;
import cheboksarov.blps_lab4.service.SiteUserService;
import cheboksarov.blps_lab4.utils.BetUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;


@Service
@Slf4j
@AllArgsConstructor
public class BetServiceImplement implements BetService {
    private BetRepository betRepository;
    private SiteUserService userService;
    private MatchService matchService;
    private CredentialService credentialService;
    private CredentialRepository credentialRepository;
    @Override
    public List<Bet> findAllMyBets() throws Exception {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails;
        if (auth.isAuthenticated()){
            userDetails = (UserDetails) auth.getPrincipal();
            log.info("User details: " + userDetails);
        } else {
            throw new UsernameNotFoundException("Wrong userId provided!");
        }
        Credential credential = credentialService.findByUserName(userDetails.getUsername());
        SiteUser siteUser = userService.findByCredentialId(credential);
        //SiteUser user = userService.findUserById(userId);
        return betRepository.findAllBySiteUser(siteUser);
    }

    @Override
    @Transactional
    public ResponseEntity<?> doBet(DoBetRequest doBetRequest) throws Exception {
        if((doBetRequest.getMatchId() == null) | (doBetRequest.getBet() == null)
            | (doBetRequest.getEvent() == null)
                )
        {
            throw new Exception("Bad request data");
        }
        Match match = matchService.findById(doBetRequest.getMatchId());
        Credential credential = credentialRepository.getReferenceById(doBetRequest.getCredentialId());
        SiteUser user = userService.findByCredentialId(credential);
        if (user.getBalance() < doBetRequest.getBet()){
            throw new Exception("Not enought money in your balance ((");
        }
        user.setBalance(user.getBalance()- doBetRequest.getBet());
        Bet.BetEvent event = BetUtils.validateEventString(doBetRequest.getEvent());
        Coefficient coefficient = match.getCoefficient();
        //errorSimulation();
        Bet bet = Bet.builder().siteUser(user).betEvent(event)
                .coefficient(coefficient).betStatus(Bet.BetStatus.Accepted).amount(doBetRequest.getBet()).build();
        betRepository.save(bet);
        log.warn("Bet processed by: " + Thread.currentThread().getId());
        return new ResponseEntity<>("Your bet is accepted!", HttpStatus.OK);

    }

    @Override
    public List<Bet> findAllActiveBets() {
        return betRepository.findAllByBetStatus(Bet.BetStatus.Accepted);
    }

    @Override
    public boolean isBetCompleted(Bet bet) {
        Match match = findMatchBetByCoeff(bet);
        return LocalDateTime.now().isAfter(match.getTime_end());
    }

    @Override
    public double betResult(Bet bet) {
        Bet.BetEvent betEvent = bet.getBetEvent();
        Match match = findMatchBetByCoeff(bet);
        return Math.round(betEvent.calcIncome(match.getHostsStat(), match.getGuestsStat(), bet, bet.getCoefficient()) * 100.0)/100.0;
    }

    public Match findMatchBetByCoeff(Bet bet){
        Coefficient coefficient = bet.getCoefficient();
        return matchService.findMatchByCoefficient(coefficient);
    }


    public void errorSimulation(){
        throw new RuntimeException("Simulated error");
    }
}
