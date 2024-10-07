package cheboksarov.blps_lab4.controller;

import cheboksarov.blps_lab4.model.Statistics;
import cheboksarov.blps_lab4.service.StatisticsService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/statistics")
@AllArgsConstructor
public class StatisticsController {
    private StatisticsService statisticsService;
    @GetMapping("/{stat_id}")
    public Statistics findById(@PathVariable Long stat_id){
        return statisticsService.findById(stat_id);
    }

    @PostMapping("save_statistics")
    public Statistics saveStatistics(@RequestBody Statistics statistics){
        return statisticsService.save(statistics);
    }
}
