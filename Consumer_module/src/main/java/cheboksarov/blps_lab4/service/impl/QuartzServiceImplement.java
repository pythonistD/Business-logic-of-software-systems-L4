package cheboksarov.blps_lab4.service.impl;

import cheboksarov.blps_lab4.model.Bet;
import cheboksarov.blps_lab4.repository.BetRepository;
import cheboksarov.blps_lab4.service.BetService;
import cheboksarov.blps_lab4.service.SiteUserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class QuartzServiceImplement {

    private BetService betService;
    private BetRepository betRepository;
    private SiteUserService siteUserService;

    public void printMessage(String msg){
        System.out.println(LocalTime.now() + msg);
    }
    public void processBetsAfterMathFinished(){
        List<Bet> activeBets = betService.findAllActiveBets();
        for(Bet bet: activeBets){
            log.warn("Starting processing bet: " + bet.getBetId());
            if(betService.isBetCompleted(bet)){
                double betRes = betService.betResult(bet);
                log.warn("Bet Result: " + betRes);
                if(betRes > -1){
                    try {
                        siteUserService.makePayment(betRes, bet.getSiteUser().getCredential().getUsername());
                        bet.setBetStatus(Bet.BetStatus.Win);
                        log.warn("You Win!");
                    }catch (Exception e){
                        log.error("Error while processing finished bets" + e.getMessage());
                    }
                }else{
                    bet.setBetStatus(Bet.BetStatus.Lose);
                    log.warn("You Lose ((");
                }
                betRepository.save(bet);
                log.warn("Bet updated");
            }
        }

    }
}
