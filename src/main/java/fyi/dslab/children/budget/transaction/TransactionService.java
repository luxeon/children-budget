package fyi.dslab.children.budget.transaction;

import fyi.dslab.children.budget.user.User;
import fyi.dslab.children.budget.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.LocalDate;

@Service
@RequiredArgsConstructor
@Slf4j
public class TransactionService {

    private static final int MAX_PAGE_SIZE = 50;

    private final TransactionRepository repository;
    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public Page<Transaction> getTransactionsForUser(Long userId, int page, int size) {
        int safePage = Math.max(page, 0);
        int safeSize = Math.min(Math.max(size, 1), MAX_PAGE_SIZE);

        Pageable pageable = PageRequest.of(
                safePage,
                safeSize,
                Sort.by(Sort.Direction.DESC, "occurredOn")
        );
        return repository.findByUserId(userId, pageable);
    }

    @Transactional
    public void addTransaction(Long userId,
                               BigDecimal amount,
                               String description,
                               LocalDate occurredOn,
                               TransactionType type) {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) == 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Amount must be non-zero");
        }
        if (description == null || description.isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Description is required");
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        TransactionType effectiveType = type == null ? TransactionType.DEBIT : type;
        BigDecimal normalizedAmount = amount.abs();
        BigDecimal signedAmount = effectiveType.applySign(normalizedAmount);

        LocalDate effectiveDate = occurredOn != null ? occurredOn : LocalDate.now();
        String sanitizedDescription = description.strip();

        repository.save(new Transaction(
                null,
                userId,
                signedAmount,
                sanitizedDescription,
                effectiveDate
        ));

        BigDecimal currentBalance = user.balance() == null ? BigDecimal.ZERO : user.balance();
        BigDecimal updatedBalance = currentBalance.add(signedAmount);
        userRepository.updateBalance(user.id(), updatedBalance);

        log.info("Recorded {} transaction of {} for user {} (new balance: {})",
                effectiveType, signedAmount, user.name(), updatedBalance);
    }

    @Transactional
    public void removeTransaction(Long userId, Long transactionId) {
        Transaction transaction = repository.findById(transactionId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Transaction not found"));

        if (!transaction.userId().equals(userId)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Transaction does not belong to user");
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        BigDecimal currentBalance = user.balance() == null ? BigDecimal.ZERO : user.balance();
        BigDecimal updatedBalance = currentBalance.subtract(transaction.amount());

        repository.deleteById(transactionId);
        userRepository.updateBalance(user.id(), updatedBalance);

        log.info("Removed transaction {} for user {}. Amount {} deducted from balance. New balance: {}",
                transactionId, user.name(), transaction.amount(), updatedBalance);
    }
}
