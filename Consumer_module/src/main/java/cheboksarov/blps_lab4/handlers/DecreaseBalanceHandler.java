package cheboksarov.blps_lab4.handlers;

import cheboksarov.blps_lab4.model.Credential;
import cheboksarov.blps_lab4.model.SiteUser;
import cheboksarov.blps_lab4.service.CredentialService;
import cheboksarov.blps_lab4.service.SiteUserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.client.spring.annotation.ExternalTaskSubscription;
import org.camunda.bpm.client.task.ExternalTask;
import org.camunda.bpm.client.task.ExternalTaskHandler;
import org.camunda.bpm.client.task.ExternalTaskService;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@Slf4j
@ExternalTaskSubscription("decrease_balance")
public class DecreaseBalanceHandler implements ExternalTaskHandler {
    private CredentialService credentialService;
    private SiteUserService userService;

    @Override
    public void execute(ExternalTask externalTask, ExternalTaskService externalTaskService) {
        try {
            String username = externalTask.getVariable("username");
            Double betAmount = Double.valueOf(externalTask.getVariable("Bet_Amount"));
            Credential credential = credentialService.findByUserName(username);
            SiteUser user = userService.findByCredentialId(credential);
            user.setBalance(user.getBalance() - betAmount);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        externalTaskService.complete(externalTask);

    }
}
