package cheboksarov.blps_lab4.handlers;

import cheboksarov.blps_lab4.model.Credential;
import cheboksarov.blps_lab4.model.Match;
import cheboksarov.blps_lab4.model.SiteUser;
import cheboksarov.blps_lab4.service.CredentialService;
import cheboksarov.blps_lab4.service.MatchService;
import cheboksarov.blps_lab4.service.SiteUserService;
import cheboksarov.blps_lab4.service.impl.MyUserDetailService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.client.spring.annotation.ExternalTaskSubscription;
import org.camunda.bpm.client.task.ExternalTask;
import org.camunda.bpm.client.task.ExternalTaskHandler;
import org.camunda.bpm.client.task.ExternalTaskService;
import org.camunda.bpm.engine.variable.VariableMap;
import org.camunda.bpm.engine.variable.Variables;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@ExternalTaskSubscription("check_balance")
@Slf4j
@AllArgsConstructor
public class CheckBalanceHandler implements ExternalTaskHandler {
    private CredentialService credentialService;
    private SiteUserService userService;

    @Override
    public void execute(ExternalTask externalTask, ExternalTaskService externalTaskService){
        String username = externalTask.getVariable("username");
        Double betAmount = Double.valueOf(externalTask.getVariable("Bet_Amount"));
        VariableMap variableMap = Variables.createVariables();
        try {
            Credential credential = credentialService.findByUserName(username);
            SiteUser user = userService.findByCredentialId(credential);
            log.info(user.getBalance().toString());
            if (user.getBalance() < betAmount){
                variableMap.putValue("isEnough", false);
            }else{
                variableMap.putValue("isEnough", true);
            }
            // TO-DO: Check if Match is finished in consumer side
            /*
            Match match = matchService.findById(matchId);
            if(LocalDateTime.now().isAfter(match.getTime_end())){
                throw new Exception("You cant do bet for finished match!");
            }*/
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        externalTaskService.complete(externalTask, variableMap);
    }
}
