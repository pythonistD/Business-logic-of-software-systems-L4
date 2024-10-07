package cheboksarov.blps_lab4.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Bet {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long betId;
    @ManyToOne(fetch = FetchType.LAZY)
    private SiteUser siteUser;
    @ManyToOne
    @JoinColumn(name = "fk_coeff_id", referencedColumnName = "coeff_id")
    private Coefficient coefficient;
    private BetEvent betEvent;
    private BetStatus betStatus;
    private Double amount;

    public enum BetEvent{
        HostsWins{
            @Override
            public Float getCoeff(Coefficient coefficient) {
                return coefficient.getHosts_wins();
            }
        },
        GuestsWins {
            @Override
            public Float getCoeff(Coefficient coefficient) {
                return coefficient.getGuests_wins();
            }
        },
        TotalOne {
            @Override
            public Float getCoeff(Coefficient coefficient) {
                return coefficient.getTotal_one();
            }
        },
        TotalTwo {
            @Override
            public Float getCoeff(Coefficient coefficient) {
                return coefficient.getTotal_two();
            }
        },
        TotalThree{
            @Override
            public Float getCoeff(Coefficient coefficient) {
                return coefficient.getTotal_three();
            }
        };
        public abstract Float getCoeff(Coefficient coefficient);
    }
    public enum BetStatus{
        Processing,
        Accepted,
        Win,
        Lose
    }
}
