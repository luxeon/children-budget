package fyi.dslab.children.budget.user;

import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;

public interface UserRepository extends ListCrudRepository<User, Long> {
	List<User> findAllByOrderById();

	@Modifying
	@Query("UPDATE users SET balance = :balance WHERE id = :id")
	void updateBalance(@Param("id") Long id, @Param("balance") BigDecimal balance);
}
