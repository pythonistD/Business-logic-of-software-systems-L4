package cheboksarov.blps_lab4;

import cheboksarov.blps_lab4.controller.AuthenticationResponse;
import cheboksarov.blps_lab4.dto.RegisterRequestDto;
import cheboksarov.blps_lab4.model.Credential;
import cheboksarov.blps_lab4.model.SiteUser;
import cheboksarov.blps_lab4.repository.CredentialRepository;
import cheboksarov.blps_lab4.service.AuthenticationService;
import cheboksarov.blps_lab4.service.JwtService;
import cheboksarov.blps_lab4.service.SiteUserService;
import cheboksarov.blps_lab4.service.impl.AuthenticationServiceImplement;
import cheboksarov.blps_lab4.service.impl.JwtServiceImplement;
import cheboksarov.blps_lab4.service.impl.SiteUserServiceImplement;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AuthenticationServiceTest {

    private final JwtService jwtService = new JwtServiceImplement();
    private final Credential credential  = Credential.builder()
            .userName("admin").password("admin").build();
    @MockBean(AuthenticationManager.class)
    AuthenticationManager authenticationManager;
    @MockBean
    SiteUserService siteUserService;
    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    String token = jwtService.generateToken(credential);

    @Test
    public void registerTest(){
        var siteUser = SiteUser.builder()
                .firstName("Yaroslav")
                .lastName("Cheboksarov")
                .credential(credential).build();
        CredentialRepository credentialRepository= Mockito.mock(CredentialRepository.class);
        Mockito.when(credentialRepository.save(credential)).thenReturn(credential);
        SiteUserService siteUserService= Mockito.mock(SiteUserServiceImplement.class);
        Mockito.when(siteUserService.createNewUser(Mockito.any(SiteUser.class))).thenReturn(siteUser);
        AuthenticationService authenticationService= new AuthenticationServiceImplement(
                credentialRepository,
                siteUserService,
                passwordEncoder,
                jwtService,
                authenticationManager
        );
        RegisterRequestDto requestDto= RegisterRequestDto.builder()
                .username("admin")
                .password("admin")
                .firstName("Yaroslav")
                .lastName("Cheboksarov")
                .build();
        AuthenticationResponse resp = authenticationService.register(requestDto);
        System.out.println(resp.getToken());
        String username = jwtService.extractUsername(resp.getToken());
        assertEquals("admin", username);

    }

}
