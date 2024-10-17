package cheboksarov.blps_lab4.handlers;

import cheboksarov.blps_lab4.dto.RegisterRequestDto;
import cheboksarov.blps_lab4.model.Match;
import cheboksarov.blps_lab4.service.AuthenticationService;
import cheboksarov.blps_lab4.service.MatchService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.JacksonYAMLParseException;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.client.spring.annotation.ExternalTaskSubscription;
import org.camunda.bpm.client.task.ExternalTask;
import org.camunda.bpm.client.task.ExternalTaskHandler;
import org.camunda.bpm.client.task.ExternalTaskService;
import org.camunda.bpm.engine.variable.VariableMap;
import org.camunda.bpm.engine.variable.Variables;
import org.camunda.spin.Spin;
import org.camunda.spin.SpinList;
import org.camunda.spin.json.SpinJsonNode;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@ExternalTaskSubscription("sendMatches")
@Slf4j
@AllArgsConstructor
public class SendMatchesHandler implements ExternalTaskHandler {
    private MatchService matchService;

    @Override
    public void execute(ExternalTask externalTask, ExternalTaskService externalTaskService) {
        VariableMap variableMap = Variables.createVariables();

        ObjectMapper mapper = new ObjectMapper() .registerModule(new JavaTimeModule());
        List<Match> matches = matchService.findAllMatch();
        Map<String, String> matchesMap = new HashMap<String, String>();
        for(int i = 0; i < matches.size(); i++) {
            String t = matches.get(i).getHosts() + " " + matches.get(i).getGuests();
            matchesMap.put(String.valueOf(i), t);
        }
        /*
        String jsonMatches;
        try {
            jsonMatches = mapper.writeValueAsString(matches);
            variableMap.put("matches", jsonMatches);
        } catch (JsonProcessingException e) {
            log.error(e.getMessage());
            variableMap.put("matches", "");
        }*/
        try {
            Map<String, String> productTypes = new HashMap<>();
            productTypes.put("001", "Notebook");
            productTypes.put("002", "Server");
            productTypes.put("003", "Workstation");

            // Сериализация Map в JSON с использованием Spin
            SpinJsonNode jsonProductTypes = Spin.JSON(productTypes);

            // Преобразование в строку для передачи в процесс
            String jsonString = jsonProductTypes.toString();

            // Установка переменной в процессе
            Map<String, Object> variables = new HashMap<>();
            variables.put("AVAILABLE_PRODUCT_TYPES", Spin.JSON(matchesMap));

            // Завершение задания и передача переменных
            externalTaskService.complete(externalTask, variables);
        } catch (Exception e){
            log.error(e.getMessage());
        }
    }
}
