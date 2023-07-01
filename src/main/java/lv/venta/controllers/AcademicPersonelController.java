package lv.venta.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import lv.venta.services.AcademicPersonelCRUDService;

@Controller
@RequestMapping("/academicPersonel")
public class AcademicPersonelController {

	@Autowired
	private AcademicPersonelCRUDService academicPersonelService;
	
	@GetMapping("/showAll")
	public String selectAllAcademicPersonels(org.springframework.ui.Model academicPersonel) {
		academicPersonel.addAttribute("MyAcademiPersonels", academicPersonelService.selectAllAcademicPersonels());
		return "academicpersonel-all-page";
	}
	
	@GetMapping("/showAll/{id}")
	public String selectAcademicPersonelById(@PathVariable long id, org.springframework.ui.Model academicPersonel) {
		academicPersonel.addAttribute("MyAcademiPersonels", academicPersonelService.selectAcademicPersonelById(id));
		return "academicpersonel-one-page";
	}
	
}
