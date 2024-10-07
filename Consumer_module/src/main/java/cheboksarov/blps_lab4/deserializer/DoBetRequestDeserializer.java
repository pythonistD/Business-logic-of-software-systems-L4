package cheboksarov.blps_lab4.deserializer;

import cheboksarov.blps_lab4.dto.DoBetRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@AllArgsConstructor
@Slf4j
public class DoBetRequestDeserializer {

    String request;

    public DoBetRequest deserialize() throws JsonProcessingException, JsonMappingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(request, DoBetRequest.class);
    }



}
