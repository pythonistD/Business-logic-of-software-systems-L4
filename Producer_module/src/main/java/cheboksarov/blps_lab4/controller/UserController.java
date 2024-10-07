package cheboksarov.blps_lab4.controller;

import cheboksarov.blps_lab4.dto.IncBalanceDto;
import cheboksarov.blps_lab4.model.SiteUser;
import cheboksarov.blps_lab4.service.SiteUserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/user")
@AllArgsConstructor
public class UserController {
    private SiteUserService siteUserService;

    @GetMapping("getAll")
    public List<SiteUser> getAllUsers(){
        return siteUserService.getAllUsers();
    }

    @PostMapping("topUpBalance")
    public ResponseEntity<?> topUpBalance(@RequestBody IncBalanceDto incBalanceDto){
        try {
            return siteUserService.makePayment(incBalanceDto.getAmount(), incBalanceDto.getUserName());
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }

    @GetMapping("myBalance")
    public ResponseEntity<?> getMyBalance(){
        return siteUserService.getMyBalance();
    }


}
