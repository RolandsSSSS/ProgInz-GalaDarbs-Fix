package lv.venta.controllers;

import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.logging.Logger;
import java.util.logging.Level;

import jakarta.validation.Valid;
import lv.venta.services.IPersonCRUD;
import lv.venta.models.users.AcademicPersonel;
import lv.venta.models.users.Person;
import lv.venta.repos.IPersonRepo;

@Controller
@RequestMapping("/Person")
public class PersonsController {
	Logger logger = Logger.getLogger(getClass().getName());
	public static final String PERSON_ATTRIBUTE = "AllPersons";
	private static final String REDIRECT_TO_SHOW_ALL = "redirect:/Person/All";

	private IPersonRepo personRepo;
	private IPersonCRUD personCrud;

	public PersonsController(IPersonRepo personRepo, IPersonCRUD personCrud) {
		this.personRepo = personRepo;
		this.personCrud = personCrud;
	}

	@GetMapping("/All")
	public String selectAllPersons(Model model) {
		List<Person> allPersons = (List<Person>) personRepo.findAll();
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

		return "Person-Add";
	}

	@PostMapping("/Add")
	public String addNewPerson2(@ModelAttribute("personForm") @Valid Person person, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return "Person-Add";
		}

		Person temp = new Person();
		temp.setName(person.getName());
		temp.setSurname(person.getSurname());
		temp.setPersoncode(person.getPersoncode());

		try {
			personCrud.insertPersontByParams(person);
		} catch (Exception e) {
			logger.log(Level.SEVERE, () -> "Kļūda veicot personas ievietošanu, " + e);
		}

		return REDIRECT_TO_SHOW_ALL;
	}

}
