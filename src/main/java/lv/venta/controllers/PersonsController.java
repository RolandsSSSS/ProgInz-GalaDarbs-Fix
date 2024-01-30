package lv.venta.controllers;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lv.venta.services.IPersonCRUD;
import lv.venta.services.IUserCRUD;
import lv.venta.services.Excel.ExcelExportService;
import lv.venta.services.Excel.ExcelUploadService;
import lv.venta.models.users.AcademicPersonel;
import lv.venta.models.users.Person;
import lv.venta.models.users.User;
import lv.venta.repos.IPersonRepo;
import lv.venta.repos.users.IUserRepo;

@Controller
@RequestMapping("/Person")
public class PersonsController {
	public static final String PERSON_ATTRIBUTE = "AllPersons";
	private static final String REDIRECT_TO_SHOW_ALL = "redirect:/Person/All";

	private IPersonRepo personRepo;
	private IPersonCRUD personCrud;
	private IUserRepo userRepo;
	private IUserCRUD userService;

	public PersonsController(IPersonRepo personRepo, IPersonCRUD personCrud, IUserRepo userRepo,
			IUserCRUD userService) {
		this.personRepo = personRepo;
		this.personCrud = personCrud;
		this.userRepo = userRepo;
		this.userService = userService;
	}

	@GetMapping("/All")
	public String selectAllPersons(Model model) {
		List<Person> allPersons = personCrud.selectAllPersons();
		model.addAttribute(PERSON_ATTRIBUTE, allPersons);
		return "Persons-All";
	}

	@GetMapping("/All/{id}")
	public String selectCourseById(@PathVariable long id, Model model) {
		try {

			model.addAttribute(PERSON_ATTRIBUTE, personCrud.retrieveOnePersonById(id));
			return "Persons-One";
		} catch (Exception e) {

			return "error-page";
		}
	}

	@GetMapping("/update/{id}")
	public String updatePerson(@PathVariable long id, Model model) throws Exception {

		Person person = personCrud.retrieveOnePersonById(id);

		model.addAttribute("person", person);

		return "Person-Update";
	}

	@PostMapping("/update/By/{id}")
	public String updatePersonById(@PathVariable long id, @ModelAttribute("person") @Valid Person updatedPerson,
			BindingResult bindingResult, Model model) throws Exception {
		if (bindingResult.hasErrors()) {
			return "Person-Update";
		}

		Person existingPerson = personCrud.retrieveOnePersonById(id);
		existingPerson.setName(updatedPerson.getName());
		existingPerson.setSurname(updatedPerson.getSurname());
		existingPerson.setPersoncode(updatedPerson.getPersoncode());
		personCrud.updatePersonByParams(existingPerson);

		return REDIRECT_TO_SHOW_ALL;
	}

	@PostMapping("remove/{id}")
	public String deletePersonById(@PathVariable("id") long id, Model model) throws Exception {
		try {
			if (personCrud.retrieveOnePersonById(id) instanceof AcademicPersonel) {
				return "redirect:/academicPersonel/remove/{id}";
			} else {
				personCrud.deletePersonById(id);
				model.addAttribute(PERSON_ATTRIBUTE, personCrud.selectAllPersons());
				return REDIRECT_TO_SHOW_ALL;
			}

		} catch (Exception e) {
			return "error-page";
		}
	}

	@GetMapping("/AddPage")
	public String addNewPerson(Model model) {
		List<User> users = userService.selectAllUsers();
		model.addAttribute("users", users);
		return "Person-Add";
	}

	@PostMapping("/AddPage")
	public String addNewPerson2(@ModelAttribute("personForm") @Valid Person person, BindingResult bindingResult,
			HttpServletRequest request) {
		if (bindingResult.hasErrors()) {
			return "Person-Add";
		}

		long userId = Long.parseLong(request.getParameter("user"));
		User user = userService.selectUserById(userId);

		Person temp = new Person();
		temp.setName(person.getName());
		temp.setSurname(person.getSurname());
		temp.setPersoncode(person.getPersoncode());
		temp.setUser(user);

		try {
			personCrud.insertPersontByParams(temp);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return REDIRECT_TO_SHOW_ALL;
	}

	@PostMapping("/Import")
	public String importPersons(@ModelAttribute("file") MultipartFile file) throws Exception {
		if (!ExcelUploadService.isValidExcelFile(file)) {
			return REDIRECT_TO_SHOW_ALL;

		}

		try {
			List<Person> persons = ExcelUploadService.getPersonDataFromExcel(file.getInputStream());
			for (Person person : persons) {
				personCrud.insertPersontByParams(person);
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

		return REDIRECT_TO_SHOW_ALL;
	}

	@GetMapping("/Export")
	protected void ExportPersons(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		List<Person> allPersons = (List<Person>) personRepo.findAll();

		String filePath = "persons.xlsx";
		ExcelExportService<Person> excelExportService = new ExcelExportService<>();
		excelExportService.exportToExcel(allPersons, filePath);

		response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
		response.setHeader("Content-Disposition", "attachment; filename=persons.xlsx");

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
