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
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class WeeklyBonusService {

    private final UserService userService;
    private final TransactionService transactionService;
    private final WeeklyBonusProperties weeklyBonusProperties;

    @Transactional
    public void creditWeeklyBonus(LocalDate runDate) {
        BigDecimal amount = weeklyBonusProperties.getAmount();
        List<User> users = userService.getUsers();
        for (User user : users) {
            transactionService.addTransaction(user.id(), amount, "Weekly allowance bonus", runDate,
                    TransactionType.CREDIT);
            log.info("Credited weekly bonus for user '{}'.", user.name());
        }
    }
}
