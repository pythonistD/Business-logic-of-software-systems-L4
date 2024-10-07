package cheboksarov.blps_lab4.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DoBetRequest {
    Long matchId;
    String event;
    Double bet;
    Long credentialId;
}
