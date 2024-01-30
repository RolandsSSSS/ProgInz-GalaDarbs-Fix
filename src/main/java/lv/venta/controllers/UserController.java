package lv.venta.controllers;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lv.venta.models.users.User;
import lv.venta.models.users.User;
import lv.venta.repos.users.IUserRepo;
import lv.venta.services.IUserCRUD;
import lv.venta.services.Excel.ExcelExportService;

@Controller
@RequestMapping("/User")
public class UserController {
	public static final String USER_ATTRIBUTE = "MyUsers";
	private static final String REDIRECT_TO_SHOW_ALL = "redirect:/User/showAll";

	private IUserCRUD userCRUD;
	private IUserRepo userRepo;

	public UserController(IUserCRUD userCRUD, IUserRepo userRepo) {
		this.userCRUD = userCRUD;
		this.userRepo = userRepo;
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

	@GetMapping("/Export")
	protected void ExportUsers(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		List<User> allUsers = (List<User>) userRepo.findAll();

		String filePath = "Users.xlsx";
		ExcelExportService<User> excelExportService = new ExcelExportService<>();
		excelExportService.exportToExcel(allUsers, filePath);

		response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
		response.setHeader("Content-Disposition", "attachment; filename=Users.xlsx");

		InputStream fileStream = new FileInputStream(filePath);

		OutputStream out = response.getOutputStream();
		byte[] buffer = new byte[4096];
		int length;
		while ((length = fileStream.read(buffer)) > 0) {
			out.write(buffer, 0, length);
		}

		out.flush();
		out.close();
		fileStream.close();
	}
}
