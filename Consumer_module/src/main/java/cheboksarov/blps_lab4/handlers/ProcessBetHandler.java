package cheboksarov.blps_lab4.handlers;

import cheboksarov.blps_lab4.model.Bet;
import cheboksarov.blps_lab4.model.Coefficient;
import cheboksarov.blps_lab4.model.Credential;
import cheboksarov.blps_lab4.model.SiteUser;
import cheboksarov.blps_lab4.repository.BetRepository;
import cheboksarov.blps_lab4.service.CredentialService;
import cheboksarov.blps_lab4.service.MatchService;
import cheboksarov.blps_lab4.service.SiteUserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.client.spring.annotation.ExternalTaskSubscription;
import org.camunda.bpm.client.task.ExternalTask;
import org.camunda.bpm.client.task.ExternalTaskHandler;
import org.camunda.bpm.client.task.ExternalTaskService;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@AllArgsConstructor
@Slf4j
@ExternalTaskSubscription("process_bet")
public class ProcessBetHandler implements ExternalTaskHandler {
    private CredentialService credentialService;
    private SiteUserService userService;
    private MatchService matchService;
    private BetRepository betRepository;

    @Override
    public void execute(ExternalTask externalTask, ExternalTaskService externalTaskService) {
        try {
            String username = externalTask.getVariable("username");
            Double betAmount = Double.valueOf(externalTask.getVariable("Bet_Amount"));
            String betEvent = externalTask.getVariable("EVENT_NAME");
            Long matchId = Long.valueOf(externalTask.getVariable("MATCH_ID"));
            Credential credential = credentialService.findByUserName(username);
            SiteUser user = userService.findByCredentialId(credential);
            Coefficient coefficient = matchService.findById(matchId).getCoefficient();
            user.setBalance(user.getBalance() - betAmount);
            log.info(Bet.BetEvent.GuestsWins.toString());
            Bet bet = Bet.builder()
                    .amount(betAmount)
                    .betEvent(Bet.BetEvent.valueOf(betEvent))
                    .betStatus(Bet.BetStatus.Accepted)
                    .coefficient(coefficient)
                    .siteUser(user).build();
            betRepository.save(bet);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        externalTaskService.complete(externalTask);

    }
}
