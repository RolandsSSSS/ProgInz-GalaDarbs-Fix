package lv.venta.controllers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.validation.Valid;
import lv.venta.models.users.User;
import lv.venta.repos.users.IUserRepo;
import lv.venta.services.IUserCRUD;

@Controller
@RequestMapping("/User")
public class UserController {
	

	
	@Autowired
	private IUserCRUD userCRUD;
	@Autowired
	private IUserRepo userRepo;

		@GetMapping("/showAll")
		public String selectAllUsers(org.springframework.ui.Model User) {
			User.addAttribute("MyUsers", userCRUD.selectAllUsers());
			return "User-all-page";
		}

		@GetMapping("/showAll/{id}")
		public String selectUserById(@PathVariable long id, org.springframework.ui.Model User) {
			User.addAttribute("MyUsers", userCRUD.selectUserById(id));
			return "User-one-page";
		}

		@GetMapping("/remove/{id}")
		public String deleteUserById(@PathVariable long id, org.springframework.ui.Model User) {
			userCRUD.deleteUserById(id);
			User.addAttribute("MyUsers", userCRUD.selectAllUsers());
			return "redirect:/User/showAll";
		}

		@GetMapping("/update/{id}")
		public String showUpdateForm(@PathVariable long id, org.springframework.ui.Model User) {
			User temp = userCRUD.selectUserById(id);
			if (temp != null) {
				User.addAttribute("updatedUser", temp);
				return "User-UpdatePage";
			} else {
				User.addAttribute("MyUsers", userCRUD.selectAllUsers());
				return "redirect:/User/showAll";
			}
		}

		@PostMapping("/update/{id}")
		public String updateUserById(@PathVariable long id, @Valid User updatedUser, BindingResult bindingResult, org.springframework.ui.Model User) {
			 if (bindingResult.hasErrors()) {
				 User.addAttribute("updatedUser", updatedUser);
				 return "User-UpdatePage";
			 }
			
			userCRUD.updateUserById(id, updatedUser);
			User.addAttribute("MyUsers", userCRUD.selectAllUsers());
			return "redirect:/User/showAll";
		}
		
		@GetMapping("/addNew")
		public String showAddUserForm(User User) {
			return "User-add-page";
		}

		@PostMapping("/addNew")
		public String addNewUser(@Valid User User, BindingResult bindingResult) {
			if (bindingResult.hasErrors()) {
				return "User-add-page";
			}
			User newUser = new User( User.getPassword(), User.getEmail());
			userCRUD.insertNewUser(newUser);

			return "redirect:/User/showAll";
		}

	}


