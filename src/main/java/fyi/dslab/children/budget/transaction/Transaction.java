package fyi.dslab.children.budget.transaction;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.time.LocalDate;

@Table("transactions")
public record Transaction(
        @Id
        Long id,
        @Column("user_id")
        Long userId,
        BigDecimal amount,
        String description,
        @Column("occurred_on")
        LocalDate occurredOn
) {
}
