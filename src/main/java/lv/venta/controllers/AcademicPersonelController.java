package lv.venta.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.validation.Valid;
import lv.venta.models.users.AcademicPersonel;
import lv.venta.services.AcademicPersonelCRUDService;

@Controller
@RequestMapping("/academicPersonel")
public class AcademicPersonelController {
	public static final String ACADEMIC_PERSONELS_ATTRIBUTE = "MyAcademiPersonels";

	private AcademicPersonelCRUDService academicPersonelService;
	
	public AcademicPersonelController(AcademicPersonelCRUDService academicPersonelService) {
        this.academicPersonelService = academicPersonelService;
    }

	@GetMapping("/showAll")
	public String selectAllAcademicPersonels(org.springframework.ui.Model academicPersonel) {
		academicPersonel.addAttribute(ACADEMIC_PERSONELS_ATTRIBUTE, academicPersonelService.selectAllAcademicPersonels());
		return "academicpersonel-all-page";
	}

	@GetMapping("/showAll/{id}")
	public String selectAcademicPersonelById(@PathVariable long id, org.springframework.ui.Model academicPersonel) {
		academicPersonel.addAttribute(ACADEMIC_PERSONELS_ATTRIBUTE, academicPersonelService.selectAcademicPersonelById(id));
		return "academicpersonel-one-page";
	}

	@GetMapping("/remove/{id}")
	public String deleteAcademicPersonelById(@PathVariable long id, org.springframework.ui.Model academicPersonel) {
		academicPersonelService.deleteAcademicPersonelById(id);
		academicPersonel.addAttribute(ACADEMIC_PERSONELS_ATTRIBUTE, academicPersonelService.selectAllAcademicPersonels());
		return "redirect:/academicPersonel/showAll";
	}

	@GetMapping("/update/{id}")
	public String showUpdateForm(@PathVariable long id, org.springframework.ui.Model academicPersonel) {
		AcademicPersonel temp = academicPersonelService.selectAcademicPersonelById(id);
		if (temp != null) {
			academicPersonel.addAttribute("updatedAcademicPersonel", temp);
			return "academicpersonel-update-page";
		} else {
			academicPersonel.addAttribute(ACADEMIC_PERSONELS_ATTRIBUTE, academicPersonelService.selectAllAcademicPersonels());
			return "redirect:/academicPersonel/showAll";
		}
	}

	@PostMapping("/update/{id}")
	public String updateAcademicPersonelById(@PathVariable long id, @Valid AcademicPersonel updatedAcademicPersonel,
			BindingResult bindingResult, org.springframework.ui.Model academicPersonel) {
		if (bindingResult.hasErrors()) {
			academicPersonel.addAttribute("updatedAcademicPersonel", updatedAcademicPersonel);
			return "academicpersonel-update-page";
		}

		academicPersonelService.updateAcademicPersonelById(id, updatedAcademicPersonel);
		academicPersonel.addAttribute("MyAcademiPersonels", academicPersonelService.selectAllAcademicPersonels());
		return "redirect:/academicPersonel/showAll";
	}

	@GetMapping("/addNew")
	public String showAddAcademicPersonelForm(AcademicPersonel academicPersonel) {
		return "academicpersonel-add-page";
	}

	@PostMapping("/addNew")
	public String addNewAcademicPersonel(@Valid AcademicPersonel academicPersonel, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return "academicpersonel-add-page";
		}
		AcademicPersonel newAcademicPersonel = new AcademicPersonel(academicPersonel.getName(), academicPersonel.getSurname(), academicPersonel.getPersoncode(),academicPersonel.getUser(), academicPersonel.getDegree());
		academicPersonelService.insertNewAcademicPersonel(newAcademicPersonel);

		return "redirect:/academicPersonel/showAll";
	}

}
