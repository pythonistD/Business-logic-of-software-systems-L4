package cheboksarov.blps_lab4.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
public class Coefficient{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long coeff_id;
    private Float hosts_wins;
    private Float guests_wins;
    private Float total_one;
    private Float total_two;
    private Float total_three;

}
