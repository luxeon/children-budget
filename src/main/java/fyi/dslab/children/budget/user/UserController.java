package fyi.dslab.children.budget.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class UserController {

	private final UserService userService;

	@GetMapping({"/"})
	public String getUsers(Model model) {
		model.addAttribute("users", userService.getUsers());
		return "index";
	}
}
