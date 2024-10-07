package cheboksarov.blps_lab4.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DoBetRequest {
    Long matchId;
    String event;
    Double bet;
    Long credentialId;
}
