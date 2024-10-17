package cheboksarov.blps_lab4.handlers;

import cheboksarov.blps_lab4.model.Credential;
import cheboksarov.blps_lab4.model.Role;
import cheboksarov.blps_lab4.model.SiteUser;
import cheboksarov.blps_lab4.service.CredentialService;
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

@Component
@ExternalTaskSubscription("check_role")
@Slf4j
@AllArgsConstructor
public class CheckRoleHandler implements ExternalTaskHandler {
    private CredentialService credentialService;

    @Override
    public void execute(ExternalTask externalTask, ExternalTaskService externalTaskService) {
        String username = externalTask.getVariable("username");
        VariableMap variableMap = Variables.createVariables();
        Role role = Role.valueOf(externalTask.getVariable("role"));
        try {
            Credential credential = credentialService.findByUserName(username);
            if (credential.getRole().equals(role)) {
                variableMap.putValue("allowed", true);
            }else{
                variableMap.putValue("allowed", false);
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        externalTaskService.complete(externalTask, variableMap);
    }
}
