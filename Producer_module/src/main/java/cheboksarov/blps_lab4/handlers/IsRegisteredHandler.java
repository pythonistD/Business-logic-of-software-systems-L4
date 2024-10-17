package cheboksarov.blps_lab4.handlers;

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
@ExternalTaskSubscription("check_reg")
@Slf4j
@AllArgsConstructor
public class IsRegisteredHandler implements ExternalTaskHandler {
    private MyUserDetailService myUserDetailService;

    @Override
    public void execute(ExternalTask externalTask, ExternalTaskService externalTaskService) {
        String username = externalTask.getVariable("username");
        String password = externalTask.getVariable("pass");
        VariableMap variableMap = Variables.createVariables();
        try {
            myUserDetailService.loadUserByUsername(username);
            variableMap.putValue("isReg", true);
            variableMap.putValue("username", username);
        } catch (Exception e) {
            log.error(e.getMessage());
            variableMap.putValue("isReg", false);
        }
            System.out.println(username + " " +password + "Is Registered");
        externalTaskService.complete(externalTask, variableMap);
    }
}
