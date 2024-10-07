package cheboksarov.blps_lab4;

import cheboksarov.blps_lab4.model.Credential;
import cheboksarov.blps_lab4.service.JwtService;
import cheboksarov.blps_lab4.service.impl.JwtServiceImplement;
import io.jsonwebtoken.Jwts;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JwtServiceTest {
    private final JwtService jwtService = new JwtServiceImplement();
    private final Credential credential  = Credential.builder()
            .userName("admin").password("admin").build();
    String token = jwtService.generateToken(credential);

    @Test
    public void generateTokenTest() {
        System.out.println(token);

        assertEquals("admin" ,
                Jwts.parser().verifyWith(jwtService.getSecret())
                        .build().parseSignedClaims(token)
                        .getPayload().getSubject()
        );
    }
    @Test
    public void extractNameTest(){
        assertEquals("admin", jwtService.extractUsername(token));
    }
}
