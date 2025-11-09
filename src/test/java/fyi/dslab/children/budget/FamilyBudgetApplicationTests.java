package fyi.dslab.children.budget;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("it")
@SpringBootTest
@Import(TestcontainersConfiguration.class)
class FamilyBudgetApplicationTests {

	@Test
	void contextLoads() {
	}

}
