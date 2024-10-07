package cheboksarov.blps_lab4.repository;

import cheboksarov.blps_lab4.model.Credential;
import cheboksarov.blps_lab4.model.SiteUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SiteUserRepository extends JpaRepository<SiteUser, Long> {
    Optional<SiteUser> findByCredential(Credential credential);
}
