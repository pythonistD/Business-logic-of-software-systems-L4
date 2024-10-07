package cheboksarov.blps_lab4.service.impl;

import cheboksarov.blps_lab4.dto.DoBetDto;
import cheboksarov.blps_lab4.dto.DoBetRequest;
import cheboksarov.blps_lab4.dto.HumanReadableBetDto;
import cheboksarov.blps_lab4.model.*;
import cheboksarov.blps_lab4.repository.BetRepository;
import cheboksarov.blps_lab4.service.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;


@Service
@Slf4j
@AllArgsConstructor
public class BetServiceImplement implements BetService {
    private BetRepository betRepository;
    private SiteUserService userService;
    private MatchService matchService;
    private CredentialService credentialService;
    private KafkaTemplate<String, DoBetRequest> kafkaTemplate;
    @Override
    public List<HumanReadableBetDto> findAllMyBets() throws Exception {
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
        List<Bet> bets = betRepository.findAllBySiteUser(siteUser);
        return bets.stream().map(this::humanReadableBet).collect(Collectors.toList());
    }

    private HumanReadableBetDto humanReadableBet(Bet bet){
        Coefficient coefficient = bet.getCoefficient();
        Match match = matchService.findMatchByCoefficient(coefficient);
        HashMap<String, String> teamsOfMatch = new HashMap<>();
        teamsOfMatch.put("hosts", match.getHosts());
        teamsOfMatch.put("guests", match.getGuests());
        HashMap<String, Float> coeff = new HashMap<>();
        coeff.put("coefficient", bet.getBetEvent().getCoeff(coefficient));
        return HumanReadableBetDto.builder().matchId(match.getMatch_id())
                .teams(teamsOfMatch)
                .event(bet.getBetEvent())
                .coefficient(coeff)
                .amount(bet.getAmount()).build();
    }

    @Override
    @Transactional
    public ResponseEntity<?> doBet(DoBetDto doBetDto) throws Exception {
        if((doBetDto.getMatchId() == null) | (doBetDto.getBet() == null)
            | (doBetDto.getEvent() == null)
                )
        {
            throw new Exception("Bad request data");
        }
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String name = auth.getName();
        Credential credential = credentialService.findByUserName(name);
        SiteUser user = userService.findByCredentialId(credential);
        if (user.getBalance() < doBetDto.getBet()){
            throw new Exception("Not enought money in your balance ((");
        }
        Match match = matchService.findById(doBetDto.getMatchId());
        if(LocalDateTime.now().isAfter(match.getTime_end())){
            throw new Exception("You cant do bet for finished match!");
        }
        try {
            kafkaTemplate.send("do_bet_request_topic", DoBetRequest.builder().matchId(doBetDto.getMatchId())
                    .bet(doBetDto.getBet())
                    .event(doBetDto.getEvent())
                    .credentialId(credential.getCredentialId()).build());
            return new ResponseEntity<>("Your bet is accepted!", HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    public void errorSimulation(){
        throw new RuntimeException("Simulated error");
    }

}
