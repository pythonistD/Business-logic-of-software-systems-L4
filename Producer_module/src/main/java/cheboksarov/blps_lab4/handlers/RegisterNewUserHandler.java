package cheboksarov.blps_lab4.handlers;

import cheboksarov.blps_lab4.dto.RegisterRequestDto;
import cheboksarov.blps_lab4.service.AuthenticationService;
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
@ExternalTaskSubscription("registerNewUser")
@Slf4j
@AllArgsConstructor
public class RegisterNewUserHandler implements ExternalTaskHandler {
    private AuthenticationService authenticationService;

    @Override
    public void execute(ExternalTask externalTask, ExternalTaskService externalTaskService) {
        String firstName = externalTask.getVariable("firstName");
        String lastName = externalTask.getVariable("lastName");
        String username = externalTask.getVariable("username");
        String password = externalTask.getVariable("pass");
        VariableMap variableMap = Variables.createVariables();

        RegisterRequestDto registrationRequest = RegisterRequestDto.builder()
                .firstName(firstName)
                .lastName(lastName)
                .username(username)
                .password(password).build();
        try {
            authenticationService.register(registrationRequest);
            variableMap.putValue("isRegSucess", true);
            variableMap.putValue("username", username);
        }catch (Exception e){
            log.error(e.getMessage());
            variableMap.putValue("isRegSucess", false);
        }
        externalTaskService.complete(externalTask, variableMap);
    }
}
