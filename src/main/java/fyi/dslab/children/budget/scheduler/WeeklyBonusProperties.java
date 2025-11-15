package fyi.dslab.children.budget.scheduler;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import java.math.BigDecimal;

@Getter
@Setter
@Validated
@Component
@ConfigurationProperties(prefix = "app.weekly-bonus")
public class WeeklyBonusProperties {

    @NotNull
    @DecimalMin(value = "0.00", inclusive = false)
    private BigDecimal amount = BigDecimal.valueOf(5);

    @NotBlank
    private String cron = "0 0 0 ? * MON";
}
