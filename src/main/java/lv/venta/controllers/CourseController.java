package lv.venta.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lv.venta.services.CourseCRUDService;

@Controller
@RequestMapping("/Course")
public class CourseController {

	@Autowired
	private CourseCRUDService courseService;
	
	@GetMapping("/showAll")
	public String selectAllCourses(org.springframework.ui.Model course) {
		course.addAttribute("MyCourses", courseService.selectAllCourses());
		return "course-all-page";
	}
}
