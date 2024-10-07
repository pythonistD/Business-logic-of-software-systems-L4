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
            public double calcIncome(Statistics hostsStat, Statistics guestStat, Bet bet, Coefficient coefficient){
                if((hostsStat.getScore()- guestStat.getScore()) <= 0){
                    return -1;
                }else{
                    return coefficient.getHosts_wins() * bet.amount;
                }
            }
        },
        GuestsWins{
            @Override
            public double calcIncome(Statistics hostsStat, Statistics guestStat, Bet bet, Coefficient coefficient ){
                if((hostsStat.getScore()- guestStat.getScore()) >= 0){
                    return -1;
                }else{
                    return coefficient.getHosts_wins() * bet.amount;
                }

            }
        },
        TotalOne{
            @Override
            public double calcIncome(Statistics hostsStat, Statistics guestStat, Bet bet, Coefficient coefficient){
                if((hostsStat.getScore() + guestStat.getScore()) == 1){
                    return coefficient.getTotal_one() * bet.amount;
                }else{
                    return -1;
                }
            }
        },
        TotalTwo{
            @Override
            public double calcIncome(Statistics hostsStat, Statistics guestStat, Bet bet, Coefficient coefficient){
                if((hostsStat.getScore() + guestStat.getScore()) == 2){
                    return coefficient.getTotal_one() * bet.amount;
                }else{
                    return -1;
                }

            }
        },
        TotalThree{
            @Override
            public double calcIncome(Statistics hostsStat, Statistics guestStat, Bet bet, Coefficient coefficient){
                if((hostsStat.getScore() + guestStat.getScore()) == 3){
                    return coefficient.getTotal_one() * bet.amount;
                }else{
                    return -1;
                }

            }
        };

        public abstract double calcIncome(Statistics hostsStat, Statistics guestStat, Bet bet, Coefficient coefficient );
    }
    public enum BetStatus{
        Processing,
        Accepted,
        Win,
        Lose;
    }
}
