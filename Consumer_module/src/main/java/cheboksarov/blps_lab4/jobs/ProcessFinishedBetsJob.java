package cheboksarov.blps_lab4.jobs;

import cheboksarov.blps_lab4.service.impl.QuartzServiceImplement;
import lombok.AllArgsConstructor;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ProcessFinishedBetsJob implements Job {
    QuartzServiceImplement quartzService;


    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        quartzService.processBetsAfterMathFinished();
    }
}
