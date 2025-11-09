package fyi.dslab.children.budget.scheduler;

import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.quartz.JobExecutionContext;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class WeeklyAgeBonusJob extends QuartzJobBean {

    private final AgeBonusService ageBonusService;

    @Override
    protected void executeInternal(@NotNull JobExecutionContext context) {
        ageBonusService.creditWeeklyAgeBonus(LocalDate.now());
    }
}
