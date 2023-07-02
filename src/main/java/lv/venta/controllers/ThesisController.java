package lv.venta.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import lv.venta.services.ThesisCRUDService;

@Controller
@RequestMapping("/thesis")
public class ThesisController {

	@Autowired
	private ThesisCRUDService thesisService;
	
	@GetMapping("/showAll")
	public String selectAllThesis(org.springframework.ui.Model thesis) {
		thesis.addAttribute("MyThesis", thesisService.selectAllThesis());
		return "thesis-all-page";
	}
	
	@GetMapping("/showAll/{id}")
	public String selectThesisById(@PathVariable long id, org.springframework.ui.Model thesis) {
		thesis.addAttribute("MyThesis", thesisService.selectThesisById(id));
		return "thesis-one-page";
	}
}
