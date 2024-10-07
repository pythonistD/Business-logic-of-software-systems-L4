package cheboksarov.blps_lab4.service;

import cheboksarov.blps_lab4.model.Credential;

public interface CredentialService {
    Credential registerNewUser(Credential credential);

    Credential findByUserName(String username) throws Exception;

    // Credential findByCredentialId()
}
