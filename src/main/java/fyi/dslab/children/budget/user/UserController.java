package fyi.dslab.children.budget.user;

import fyi.dslab.children.budget.transaction.Transaction;
import fyi.dslab.children.budget.transaction.TransactionService;
import fyi.dslab.children.budget.transaction.TransactionType;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.time.LocalDate;

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
							  @RequestParam(name = "deleted", required = false) Boolean deleted,
							  Model model) {
			User user = userService.getUser(id);
			Page<Transaction> transactionsPage = transactionService.getTransactionsForUser(id, page, size);

			model.addAttribute("user", user);
			model.addAttribute("transactions", transactionsPage.getContent());
			model.addAttribute("transactionsPage", transactionsPage);
			model.addAttribute("transactionDeleted", deleted != null && deleted);
			return "user-details";
		}

	@PostMapping("/users/{id}/transactions")
	public String addTransaction(@PathVariable Long id,
								 @RequestParam BigDecimal amount,
								 @RequestParam String description,
								 @RequestParam(name = "type", defaultValue = "DEBIT") TransactionType type,
								 @RequestParam(name = "occurredOn", required = false)
	                             @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate occurredOn) {
		transactionService.addTransaction(id, amount, description, occurredOn, type);
		return "redirect:/users/" + id;
	}

	@PostMapping("/users/{id}/transactions/{transactionId}/delete")
		public String deleteTransaction(@PathVariable Long id, @PathVariable Long transactionId) {
			transactionService.removeTransaction(id, transactionId);
			return "redirect:/users/" + id + "?deleted=true";
		}
	}
