package lv.venta.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.validation.Valid;
import lv.venta.services.IPersonCRUD;
import lv.venta.services.IPersonCRUDService;
import lv.venta.models.Course;
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
	        
	        return "error-page";
	    }
	}
	
	@GetMapping("/update/{id}")
	public String UdatePerson(@PathVariable long id, Model model) throws Exception {
	  
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

	    return "redirect:/Person/All";
	}


	
	

	@PostMapping("remove/{id}")
	public String deletePersonById(@PathVariable("id") long id, Model model) throws Exception {
	    try {
	        personCrud.deletePersonById(id);
	        model.addAttribute("AllPersons", personCrud.selectAllPersons());
	        return "redirect:/Person/All";
	    } catch (Exception e) {
	        return "error-page";
	    }
	}



			@GetMapping("/AddPage")
					public String AddNewPerson(Model model) {
				
					return "Person-Add";
}
			@PostMapping("/Add")
			public String addNewPerson(@ModelAttribute("personForm") @Valid Person person, BindingResult bindingResult) {
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
			 
			    }

			    return "redirect:/Person/All";
			}
		

}
