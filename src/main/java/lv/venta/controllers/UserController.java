package lv.venta.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.validation.Valid;
import lv.venta.models.users.User;
import lv.venta.services.IUserCRUD;

@Controller
@RequestMapping("/User")
public class UserController {
	public static final String USER_ATTRIBUTE = "MyUsers";
	private static final String REDIRECT_TO_SHOW_ALL = "redirect:/User/showAll";

	private IUserCRUD userCRUD;

	public UserController(IUserCRUD userCRUD) {
		this.userCRUD = userCRUD;
	}

	@GetMapping("/showAll")
	public String selectAllUsers(org.springframework.ui.Model user) {
		user.addAttribute(USER_ATTRIBUTE, userCRUD.selectAllUsers());
		return "User-all-page";
	}

	@GetMapping("/showAll/{id}")
	public String selectUserById(@PathVariable long id, org.springframework.ui.Model user) {
		user.addAttribute(USER_ATTRIBUTE, userCRUD.selectUserById(id));
		return "User-one-page";
	}

	@GetMapping("/remove/{id}")
	public String deleteUserById(@PathVariable long id, org.springframework.ui.Model user) {
		userCRUD.deleteUserById(id);
		user.addAttribute(USER_ATTRIBUTE, userCRUD.selectAllUsers());
		return REDIRECT_TO_SHOW_ALL;
	}

	@GetMapping("/update/{id}")
	public String showUpdateForm(@PathVariable long id, org.springframework.ui.Model user) {
		User temp = userCRUD.selectUserById(id);
		if (temp != null) {
			user.addAttribute("updatedUser", temp);
			return "User-UpdatePage";
		} else {
			user.addAttribute(USER_ATTRIBUTE, userCRUD.selectAllUsers());
			return REDIRECT_TO_SHOW_ALL;
		}
	}

	@PostMapping("/update/{id}")
	public String updateUserById(@PathVariable long id, @Valid User updatedUser, BindingResult bindingResult,
			org.springframework.ui.Model user) {
		if (bindingResult.hasErrors()) {
			user.addAttribute("updatedUser", updatedUser);
			return "User-UpdatePage";
		}

		userCRUD.updateUserById(id, updatedUser);
		user.addAttribute(USER_ATTRIBUTE, userCRUD.selectAllUsers());
		return REDIRECT_TO_SHOW_ALL;
	}

	@GetMapping("/addNew")
	public String showAddUserForm(User user) {
		return "User-add-page";
	}

	@PostMapping("/addNew")
	public String addNewUser(@Valid User user, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return "User-add-page";
		}
		User newUser = new User(user.getPassword(), user.getEmail());
		userCRUD.insertNewUser(newUser);

		return REDIRECT_TO_SHOW_ALL;
	}
}
