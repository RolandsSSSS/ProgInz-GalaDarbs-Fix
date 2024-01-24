package lv.venta.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.validation.Valid;
import lv.venta.models.Course;
import lv.venta.services.CourseCRUDService;

@Controller
@RequestMapping("/course")
public class CourseController {
	public static final String COURSE_ATTRIBUTE = "MyCourses";
	private static final String REDIRECT_TO_SHOW_ALL = "redirect:/course/showAll";

	private CourseCRUDService courseService;
	
	public CourseController(CourseCRUDService courseService) {
        this.courseService = courseService;
    }

	@GetMapping("/showAll")
	public String selectAllCourses(org.springframework.ui.Model course) {
		course.addAttribute(COURSE_ATTRIBUTE, courseService.selectAllCourses());
		return "course-all-page";
	}

	@GetMapping("/showAll/{id}")
	public String selectCourseById(@PathVariable long id, org.springframework.ui.Model course) {
		course.addAttribute(COURSE_ATTRIBUTE, courseService.selectCourseById(id));
		return "course-one-page";
	}

	@GetMapping("/remove/{id}")
	public String deleteCourseById(@PathVariable long id, org.springframework.ui.Model course) {
		courseService.deleteCourseById(id);
		course.addAttribute(COURSE_ATTRIBUTE, courseService.selectAllCourses());
		return REDIRECT_TO_SHOW_ALL;
	}

	@GetMapping("/update/{id}")
	public String showUpdateForm(@PathVariable long id, org.springframework.ui.Model course) {
		Course temp = courseService.selectCourseById(id);
		if (temp != null) {
			course.addAttribute("updatedCourse", temp);
			return "course-update-page";
		} else {
			course.addAttribute(COURSE_ATTRIBUTE, courseService.selectAllCourses());
			return REDIRECT_TO_SHOW_ALL;
		}
	}

	@PostMapping("/update/{id}")
	public String updateCourseById(@PathVariable long id, @Valid Course updatedCourse, BindingResult bindingResult, org.springframework.ui.Model course) {
		 if (bindingResult.hasErrors()) {
			 course.addAttribute("updatedCourse", updatedCourse);
			 return "course-update-page";
		 }
		
		courseService.updateCourseById(id, updatedCourse);
		course.addAttribute(COURSE_ATTRIBUTE, courseService.selectAllCourses());
		return REDIRECT_TO_SHOW_ALL;
	}
	
	@GetMapping("/addNew")
	public String showAddCourseForm(Course course) {
		return "course-add-page";
	}

	@PostMapping("/addNew")
	public String addNewCourse(@Valid Course course, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return "course-add-page";
		}
		Course newCourse = new Course(course.getTitle(), course.getCreditPoints());
		courseService.insertNewCourse(newCourse);

		return REDIRECT_TO_SHOW_ALL;
	}

}
