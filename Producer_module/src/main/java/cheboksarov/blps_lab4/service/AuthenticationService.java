package cheboksarov.blps_lab4.service;

import cheboksarov.blps_lab4.dto.AuthenticationRequestDto;
import cheboksarov.blps_lab4.dto.AuthenticationResponse;
import cheboksarov.blps_lab4.dto.RegisterRequestDto;

public interface AuthenticationService {
    AuthenticationResponse logIn(AuthenticationRequestDto requestDto);
    AuthenticationResponse register(RegisterRequestDto requestDto);
}
