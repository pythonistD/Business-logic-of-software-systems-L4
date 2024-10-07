package cheboksarov.blps_lab4.controller;

import cheboksarov.blps_lab4.dto.AuthenticationRequestDto;
import cheboksarov.blps_lab4.dto.RegisterRequestDto;
import cheboksarov.blps_lab4.service.AuthenticationService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RestController
@RequestMapping("api/v1/authenticate")
@AllArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;
    @PostMapping("register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequestDto registrationRequest, HttpServletRequest request, HttpServletResponse response){
        try {
            return ResponseEntity.ok(authenticationService.register(registrationRequest));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new AuthenticationResponse(
                            "",
                            e.getMessage()
                    )
            );
        }
    }

    @PostMapping("logIn")
    public ResponseEntity<?> logIn(@RequestBody AuthenticationRequestDto authRequest, HttpServletRequest request, HttpServletResponse response){
        try {
            return ResponseEntity.ok(authenticationService.logIn(authRequest));
        }catch  (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new AuthenticationResponse(
                            "",
                            e.getMessage()
                    )
            );
        }
    }
}
