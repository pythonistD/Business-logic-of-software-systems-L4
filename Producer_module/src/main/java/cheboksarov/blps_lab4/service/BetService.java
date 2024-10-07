package cheboksarov.blps_lab4.service;

import cheboksarov.blps_lab4.dto.DoBetDto;
import cheboksarov.blps_lab4.dto.HumanReadableBetDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface BetService {
    List<HumanReadableBetDto> findAllMyBets() throws Exception;

    ResponseEntity<?> doBet(DoBetDto doBetDto) throws Exception;
}
