package fyi.dslab.children.budget.scheduler;

import org.quartz.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QuartzConfig {

    @Bean
    public JobDetail weeklyAgeBonusJobDetail() {
        return JobBuilder.newJob(WeeklyAgeBonusJob.class)
                .withIdentity("weeklyAgeBonusJob")
                .storeDurably()
                .build();
    }

    @Bean
    public Trigger weeklyAgeBonusTrigger(JobDetail weeklyAgeBonusJobDetail) {
        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule("0 0 0 ? * MON")
                .withMisfireHandlingInstructionFireAndProceed();

        return TriggerBuilder.newTrigger()
                .forJob(weeklyAgeBonusJobDetail)
                .withIdentity("weeklyAgeBonusTrigger")
                .withSchedule(scheduleBuilder)
                .build();
    }
}
