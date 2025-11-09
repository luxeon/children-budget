package fyi.dslab.children.budget.user;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
public class UserService {

    private final UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public List<User> getUsers() {
        return repository.findAllByOrderById();
    }

    @Transactional(readOnly = true)
    public User getUser(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
    }

    @Transactional
    public User createUser(String name, LocalDate birthday, BigDecimal initialBalance) {
        if (name == null || name.isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Name is required");
        }
        if (birthday == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Birthday is required");
        }

        BigDecimal safeBalance = initialBalance;
        if (safeBalance == null) {
            safeBalance = BigDecimal.ZERO;
        }

        User user = new User(
                null,
                name.strip(),
                safeBalance,
                birthday
        );
        return repository.save(user);
    }

    @Transactional
    public void deleteUser(Long id) {
        boolean exists = repository.existsById(id);
        if (!exists) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }
        repository.deleteById(id);
    }

    @Transactional
    public User updateUser(Long id, String name, LocalDate birthday) {
        User existing = getUser(id);
        if (name == null || name.isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Name is required");
        }
        if (birthday == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Birthday is required");
        }
        User updated = new User(
                existing.id(),
                name.strip(),
                existing.balance(),
                birthday
        );
        return repository.save(updated);
    }
}
