package lv.venta.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import lv.venta.services.IPersonCRUD;
import lv.venta.services.IPersonCRUDService;

import lv.venta.models.users.Person;
import lv.venta.repos.IPersonRepo;

@Controller
@RequestMapping("/Person")
public class PersonsController {
	
	@Autowired
	private IPersonRepo personRepo;
	@Autowired
	private IPersonCRUD personCrud;
	
	

	@GetMapping("/All")
	public String selectAllPersons(Model model) {
		List<Person> allPersons = (List<Person>) personRepo.findAll();
		model.addAttribute("AllPersons", allPersons);
		return "Persons-All";
	}
	@GetMapping("/All/{id}")
	public String selectCourseById(@PathVariable long id, Model model) {
	    try {
	  
	        model.addAttribute("AllPersons", personCrud.retrieveOnePersonById(id));
	        return "Persons-One"; 
	    } catch (Exception e) {
	        // Handle the exception appropriately, e.g., show an error message
	        return "error-page";
	    }
	}


		
}
