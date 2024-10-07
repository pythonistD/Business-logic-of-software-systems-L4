package cheboksarov.blps_lab4.controller;


import cheboksarov.blps_lab4.dto.DoBetDto;
import cheboksarov.blps_lab4.service.BetService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/bet")
@AllArgsConstructor
@Slf4j
public class BetController {
    private BetService betService;

    @GetMapping("getMyAllBets")
    public ResponseEntity<?> findAllMyBets(){
        try{
            return new ResponseEntity<>(betService.findAllMyBets(), HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("doBet")
    public ResponseEntity<?> doBet(@RequestBody DoBetDto doBetDto){
        try {
            return betService.doBet(doBetDto);
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }

}
