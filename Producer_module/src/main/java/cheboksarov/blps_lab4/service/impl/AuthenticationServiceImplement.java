package cheboksarov.blps_lab4.service.impl;

import cheboksarov.blps_lab4.dto.AuthenticationRequestDto;
import cheboksarov.blps_lab4.dto.AuthenticationResponse;
import cheboksarov.blps_lab4.dto.RegisterRequestDto;
import cheboksarov.blps_lab4.model.Credential;
import cheboksarov.blps_lab4.model.Role;
import cheboksarov.blps_lab4.model.SiteUser;
import cheboksarov.blps_lab4.repository.CredentialRepository;
import cheboksarov.blps_lab4.service.AuthenticationService;
import cheboksarov.blps_lab4.service.JwtService;
import cheboksarov.blps_lab4.service.SiteUserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImplement implements AuthenticationService {
    private final CredentialRepository credentialRepository;
    private final SiteUserService siteUserService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    @Override
    public AuthenticationResponse logIn(AuthenticationRequestDto requestDto) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        requestDto.getUsername(),
                        requestDto.getPassword()
                )
        );
        var user = credentialRepository.findByUserName(requestDto.getUsername()).orElseThrow();
        var token = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(token).build();
    }

    @Transactional
    @Override
    public AuthenticationResponse register(RegisterRequestDto requestDto) {
        var credential = Credential.builder()
                .userName(requestDto.getUsername())
                .password(passwordEncoder.encode(requestDto.getPassword()))
                .role(Role.USER)
                .build();
        credentialRepository.save(credential);
        var siteUser = SiteUser.builder()
                .firstName(requestDto.getFirstName())
                .lastName(requestDto.getLastName())
                .balance(0.0)
                .credential(credential).build();
        siteUserService.createNewUser(siteUser);
        var token = jwtService.generateToken(credential);
        return AuthenticationResponse.builder()
                .token(token).build();
    }
}
