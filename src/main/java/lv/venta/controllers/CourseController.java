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
import lv.venta.models.Course;
import lv.venta.models.users.Course;
import lv.venta.services.CourseCRUDService;
import lv.venta.services.Excel.ExcelExportService;

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
	public String updateCourseById(@PathVariable long id, @Valid Course updatedCourse, BindingResult bindingResult,
			org.springframework.ui.Model course) {
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

	@GetMapping("/Export")
	protected void ExportCourses(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		List<Course> allCourses = (List<Course>) courseService.selectAllCourses();

		String filePath = "Courses.xlsx";
		ExcelExportService<Course> excelExportService = new ExcelExportService<>();
		excelExportService.exportToExcel(allCourses, filePath);

		response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
		response.setHeader("Content-Disposition", "attachment; filename=Courses.xlsx");

		InputStream fileStream = new FileInputStream(filePath);

		OutputStream out = response.getOutputStream();
		byte[] buffer = new byte[4096];
		int length;
		while ((length = fileStream.read(buffer)) > 0) {
			out.write(buffer, 0, length);
		}

		out.flush();
		out.close();
		fileStream.close();
	}

}
