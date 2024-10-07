package cheboksarov.blps_lab4.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SiteUser {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long userId;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "fk_credential_id", referencedColumnName = "credentialId")
    private Credential credential;
    private String firstName;
    private String lastName;
    @ColumnDefault("0")
    private Double balance;

}
