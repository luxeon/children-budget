package fyi.dslab.children.budget.user;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("users")
public record User(
		@Id
		Long id,
		String name
) {
}
