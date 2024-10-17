package cheboksarov.blps_lab4.handlers;

import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.client.spring.annotation.ExternalTaskSubscription;
import org.camunda.bpm.client.task.ExternalTask;
import org.camunda.bpm.client.task.ExternalTaskHandler;
import org.camunda.bpm.client.task.ExternalTaskService;
import org.camunda.bpm.engine.variable.VariableMap;
import org.camunda.bpm.engine.variable.Variables;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Component
@ExternalTaskSubscription("auth")
@Slf4j
public class AuthHandler implements ExternalTaskHandler {

    @Override
    public void execute(ExternalTask externalTask, ExternalTaskService externalTaskService) {
        VariableMap variableMap = Variables.createVariables();
        String username = externalTask.getVariable("username");
        String password = externalTask.getVariable("pass");
        System.out.println(username + " " +password + "Auth");
        variableMap.putValue("auth", "true");
        variableMap.putValue("usernam", "true");
        externalTaskService.complete(externalTask);
    }
}
