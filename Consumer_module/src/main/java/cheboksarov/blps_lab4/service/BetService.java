package cheboksarov.blps_lab4.service;

import cheboksarov.blps_lab4.dto.DoBetRequest;
import cheboksarov.blps_lab4.model.Bet;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface BetService {
    List<Bet> findAllMyBets() throws Exception;

    ResponseEntity<?> doBet(DoBetRequest doBetDto) throws Exception;

    List<Bet> findAllActiveBets();

    boolean isBetCompleted(Bet bet);

    double betResult(Bet bet);
}
