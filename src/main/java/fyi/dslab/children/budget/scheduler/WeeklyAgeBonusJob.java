package fyi.dslab.children.budget.scheduler;

import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@RequiredArgsConstructor
@Slf4j
public class WeeklyAgeBonusJob extends QuartzJobBean {

    private final WeeklyBonusService weeklyBonusService;

    @Override
    protected void executeInternal(@NotNull JobExecutionContext context) {
        LocalDate executionDate = LocalDate.now();
        log.info("Starting weekly age bonus job at {}", executionDate);
        weeklyBonusService.creditWeeklyBonus(executionDate);
        log.info("Finished weekly age bonus job at {}", executionDate);
    }
}
