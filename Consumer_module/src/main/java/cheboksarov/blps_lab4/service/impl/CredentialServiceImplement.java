package cheboksarov.blps_lab4.service.impl;

import cheboksarov.blps_lab4.model.Credential;
import cheboksarov.blps_lab4.model.SiteUser;
import cheboksarov.blps_lab4.repository.CredentialRepository;
import cheboksarov.blps_lab4.repository.SiteUserRepository;
import cheboksarov.blps_lab4.service.CredentialService;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@AllArgsConstructor
public class CredentialServiceImplement implements CredentialService {
    private CredentialRepository credentialRepository;
    private SiteUserRepository siteUserRepository;
    private PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public Credential registerNewUser(Credential credential) {
        SiteUser siteUser = SiteUser.builder().credential(credential)
                        .balance(0.0).firstName("").lastName("")
                        .build();
        // Пароль должен храниться в хэшированном виде, поэтому перед сохранением осущуствляем кодирование пароля
        credential.setPassword(passwordEncoder.encode(credential.getPassword()));
        siteUserRepository.save(siteUser);
        return credentialRepository.save(credential);
    }

    @Override
    public Credential findByUserName(String username) throws Exception {
        Optional<Credential> credentialOp = credentialRepository.findByUserName(username);
        if (credentialOp.isPresent()){
            return credentialOp.get();
        } else {
            throw new Exception("No such Credential!");
        }
    }
}
