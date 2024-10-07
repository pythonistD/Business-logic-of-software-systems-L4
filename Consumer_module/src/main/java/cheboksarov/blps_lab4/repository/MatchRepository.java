package cheboksarov.blps_lab4.repository;

import cheboksarov.blps_lab4.model.Coefficient;
import cheboksarov.blps_lab4.model.Match;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MatchRepository extends JpaRepository<Match, Long>{
    Match findMatchByCoefficient(Coefficient coefficient);

}
