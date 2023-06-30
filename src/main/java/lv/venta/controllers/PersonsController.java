package lv.venta.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


import lv.venta.services.IPersonCRUDService;

@Controller
@RequestMapping("/Person")
public class PersonsController {
	
	@Autowired
	private IPersonCRUDService PersonService;

	@GetMapping("/ShowAll")
	public String selectAllCourses(org.springframework.ui.Model person) {
		person.addAttribute("AllPerson", PersonService.retrieveAllPersons());
		return "course-all-page";
	}

	@GetMapping("/showAll/{id}")
	public String selectIDCourses(org.springframework.ui.Model person) {
		person.addAttribute("AllPerson", PersonService.retrieveAllPersons());
		return "course-one-page";
	}
		
		
	}


