package cheboksarov.blps_lab4.repository;

import cheboksarov.blps_lab4.model.Statistics;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StatisticsRepository  extends JpaRepository<Statistics, Long> {
}
