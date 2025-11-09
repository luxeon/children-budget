package fyi.dslab.children.budget.scheduler;

import fyi.dslab.children.budget.transaction.TransactionService;
import fyi.dslab.children.budget.transaction.TransactionType;
import fyi.dslab.children.budget.user.User;
import fyi.dslab.children.budget.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class AgeBonusService {

    private final UserService userService;
    private final TransactionService transactionService;

    @Transactional
    public void creditWeeklyAgeBonus(LocalDate runDate) {
        List<User> users = userService.getUsers();
        for (User user : users) {
            LocalDate birthday = user.birthday();
            if (birthday == null) {
                continue;
            }
            int age = Period.between(birthday, runDate)
                    .getYears();
            if (age <= 0) {
                continue;
            }
            BigDecimal amount = BigDecimal.valueOf(age);
            transactionService.addTransaction(user.id(), amount, "Weekly age bonus", runDate,
                    TransactionType.CREDIT);
            log.info("Executed age bonus job executed for user '{}'.", user.name());
        }
    }
}
