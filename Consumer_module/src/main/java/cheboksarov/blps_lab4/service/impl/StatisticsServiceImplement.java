package cheboksarov.blps_lab4.service.impl;

import cheboksarov.blps_lab4.exceptions.StatisticsNotFoundException;
import cheboksarov.blps_lab4.model.Statistics;
import cheboksarov.blps_lab4.repository.StatisticsRepository;
import cheboksarov.blps_lab4.service.StatisticsService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@AllArgsConstructor
public class StatisticsServiceImplement implements StatisticsService {
    private StatisticsRepository statisticsRepository;
    @Override
    public Statistics findById(Long stat_id) throws  StatisticsNotFoundException{
        Optional<Statistics> statOp = statisticsRepository.findById(stat_id);
        if (statOp.isPresent()){
            return statOp.get();
        }
        throw new StatisticsNotFoundException(String.format("No Statistic with this stat_id: %d", stat_id));
    }

    @Override
    public Statistics save(Statistics statistics) {
        return statisticsRepository.save(statistics);
    }

    @Override
    public Statistics createDefaultStatistics() {
        Statistics statistics = Statistics.builder()
                .cornerKicks(0).score(0).penalties(0)
                .yCards(0).rCards(0).shotsOnTarget(0)
                .build();
        return statisticsRepository.save(statistics);
    }

    @Override
    public Statistics updateStatistics(Statistics statistics) {
        return statisticsRepository.save(statistics);
    }

    @Override
    public void deleteStatistics(Long statId) {
        Optional<Statistics> statisticsOp = statisticsRepository.findById(statId);
        if(statisticsOp.isPresent()){
            statisticsRepository.delete(statisticsOp.get());
        }

    }
}
