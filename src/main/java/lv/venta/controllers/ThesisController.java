package lv.venta.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.validation.Valid;
import lv.venta.models.Thesis;
import lv.venta.models.users.AcademicPersonel;
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

	@GetMapping("/remove/{id}")
	public String deleteThesisById(@PathVariable long id, org.springframework.ui.Model thesis) {
		thesisService.deleteThesisById(id);
		thesis.addAttribute("MyThesis", thesisService.selectAllThesis());
		return "redirect:/thesis/showAll";
	}

	@GetMapping("/update/{id}")
	public String showUpdateForm(@PathVariable long id, org.springframework.ui.Model thesis) {
		Thesis temp = thesisService.selectThesisById(id);
		if (temp != null) {
			thesis.addAttribute("updatedThesis", temp);
			return "thesis-update-page";
		} else {
			thesis.addAttribute("MyThesis", thesisService.selectAllThesis());
			return "redirect:/thesis/showAll";
		}
	}

	@PostMapping("/update/{id}")
	public String updateThesisById(@PathVariable long id, @Valid Thesis updatedThesis, BindingResult bindingResult,
			org.springframework.ui.Model thesis) {
		if (bindingResult.hasErrors()) {
			thesis.addAttribute("updatedThesis", updatedThesis);
			return "thesis-update-page";
		}

		thesisService.updateThesisById(id, updatedThesis);
		thesis.addAttribute("MyThesis", thesisService.selectAllThesis());
		return "redirect:/thesis/showAll";
	}

	@GetMapping("/addNew")
	public String showAddThesisForm(Thesis thesis) {
		return "thesis-add-page";
	}

	@PostMapping("/addNew")
	public String addNewThesis(@Valid Thesis thesis, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return "thesis-add-page";
		}
		Thesis newThesis = new Thesis(thesis.getTitleLv(), thesis.getTitleEn(), thesis.getAim(), thesis.getTasks(),
				thesis.getStudent(), thesis.getSupervisor());
		thesisService.insertNewThesis(newThesis);

		return "redirect:/thesis/showAll";
	}
}
