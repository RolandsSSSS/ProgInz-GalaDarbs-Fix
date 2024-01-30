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
import lv.venta.models.users.AcademicPersonel;
import lv.venta.models.users.Person;
import lv.venta.services.AcademicPersonelCRUDService;
import lv.venta.services.Excel.ExcelExportService;

@Controller
@RequestMapping("/academicPersonel")
public class AcademicPersonelController {
	public static final String ACADEMIC_PERSONELS_ATTRIBUTE = "MyAcademiPersonels";
	private static final String REDIRECT_TO_SHOW_ALL = "redirect:/academicPersonel/showAll";

	private AcademicPersonelCRUDService academicPersonelService;

	public AcademicPersonelController(AcademicPersonelCRUDService academicPersonelService) {
		this.academicPersonelService = academicPersonelService;
	}

	@GetMapping("/showAll")
	public String selectAllAcademicPersonels(org.springframework.ui.Model academicPersonel) {
		academicPersonel.addAttribute(ACADEMIC_PERSONELS_ATTRIBUTE,
				academicPersonelService.selectAllAcademicPersonels());
		return "academicpersonel-all-page";
	}

	@GetMapping("/showAll/{id}")
	public String selectAcademicPersonelById(@PathVariable long id, org.springframework.ui.Model academicPersonel) {
		academicPersonel.addAttribute(ACADEMIC_PERSONELS_ATTRIBUTE,
				academicPersonelService.selectAcademicPersonelById(id));
		return "academicpersonel-one-page";
	}

	@GetMapping("/remove/{id}")
	public String deleteAcademicPersonelById(@PathVariable long id, org.springframework.ui.Model academicPersonel) {
		academicPersonelService.deleteAcademicPersonelById(id);
		academicPersonel.addAttribute(ACADEMIC_PERSONELS_ATTRIBUTE,
				academicPersonelService.selectAllAcademicPersonels());
		return REDIRECT_TO_SHOW_ALL;
	}

	@GetMapping("/update/{id}")
	public String showUpdateForm(@PathVariable long id, org.springframework.ui.Model academicPersonel) {
		AcademicPersonel temp = academicPersonelService.selectAcademicPersonelById(id);
		if (temp != null) {
			academicPersonel.addAttribute("updatedAcademicPersonel", temp);
			return "academicpersonel-update-page";
		} else {
			academicPersonel.addAttribute(ACADEMIC_PERSONELS_ATTRIBUTE,
					academicPersonelService.selectAllAcademicPersonels());
			return REDIRECT_TO_SHOW_ALL;
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
		academicPersonel.addAttribute(ACADEMIC_PERSONELS_ATTRIBUTE,
				academicPersonelService.selectAllAcademicPersonels());
		return REDIRECT_TO_SHOW_ALL;
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
		AcademicPersonel newAcademicPersonel = new AcademicPersonel(academicPersonel.getName(),
				academicPersonel.getSurname(), academicPersonel.getPersoncode(), academicPersonel.getUser(),
				academicPersonel.getDegree());
		academicPersonelService.insertNewAcademicPersonel(newAcademicPersonel);

		return REDIRECT_TO_SHOW_ALL;
	}
}
