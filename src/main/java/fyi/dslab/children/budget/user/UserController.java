package fyi.dslab.children.budget.user;

import fyi.dslab.children.budget.transaction.Transaction;
import fyi.dslab.children.budget.transaction.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class UserController {

	private final UserService userService;
	private final TransactionService transactionService;

	@GetMapping({"/", "/users"})
	public String getUsers(Model model) {
		model.addAttribute("users", userService.getUsers());
		return "index";
	}

	@GetMapping("/users/{id}")
	public String getUser(@PathVariable Long id,
						  @RequestParam(name = "page", defaultValue = "0") int page,
						  @RequestParam(name = "size", defaultValue = "10") int size,
						  Model model) {
		User user = userService.getUser(id);
		Page<Transaction> transactionsPage = transactionService.getTransactionsForUser(id, page, size);

		model.addAttribute("user", user);
		model.addAttribute("transactions", transactionsPage.getContent());
		model.addAttribute("transactionsPage", transactionsPage);
		return "user-details";
	}
}
