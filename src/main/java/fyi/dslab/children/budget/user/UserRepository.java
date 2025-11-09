package fyi.dslab.children.budget.user;

import java.util.List;

import org.springframework.data.repository.ListCrudRepository;

public interface UserRepository extends ListCrudRepository<User, Long> {
	List<User> findAllByOrderById();
}
