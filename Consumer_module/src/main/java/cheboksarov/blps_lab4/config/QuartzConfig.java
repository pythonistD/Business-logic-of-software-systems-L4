package cheboksarov.blps_lab4.config;

import org.springframework.context.annotation.Configuration;

@Configuration
public class QuartzConfig {
/*
    @Bean
    public JobDetailFactoryBean printMessageJobDetail(){
        JobDetailFactoryBean factoryBean = new JobDetailFactoryBean();
        factoryBean.setJobClass(PrintMessage.class);
        factoryBean.setDurability(true);
        return factoryBean;
    }

    @Bean
    public JobDetailFactoryBean processFinishedBetsJobDetail(){
        JobDetailFactoryBean factoryBean = new JobDetailFactoryBean();
        factoryBean.setJobClass(ProcessFinishedBetsJob.class);
        factoryBean.setDurability(true);
        return factoryBean;
    }

    @Bean
    public CronTriggerFactoryBean printMessageTrigger(JobDetail printMessageJobDetail){
        CronTriggerFactoryBean triggerFactoryBean = new CronTriggerFactoryBean();
        triggerFactoryBean.setJobDetail(printMessageJobDetail);
        triggerFactoryBean.setStartDelay(0);
        triggerFactoryBean.setCronExpression("0 0/2 * * * ?");
        return triggerFactoryBean;
    }

    @Bean
    public CronTriggerFactoryBean processFinishedBetsTrigger(JobDetail processFinishedBetsJobDetail){
        CronTriggerFactoryBean triggerFactoryBean = new CronTriggerFactoryBean();
        triggerFactoryBean.setJobDetail(processFinishedBetsJobDetail);
        triggerFactoryBean.setStartDelay(0);
        triggerFactoryBean.setCronExpression("0 0/1 * * * ?");
        return triggerFactoryBean;
    }*/
}
