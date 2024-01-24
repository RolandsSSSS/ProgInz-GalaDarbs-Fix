package lv.venta.controllers;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.validation.Valid;
import lv.venta.services.IStudentCRUD1;
import lv.venta.models.users.Student;
import lv.venta.repos.users.IStudentRepo;

@Controller
@RequestMapping("/Student")
public class StudentController {
	public static final String STUDENT_ATTRIBUTE = "AllStudents";
	private static final String REDIRECT_TO_SHOW_ALL = "redirect:/Student/All";

	private IStudentRepo studentRepo;
	private IStudentCRUD1 studentCrud;

	public StudentController(IStudentRepo studentRepo, IStudentCRUD1 studentCrud) {
		this.studentRepo = studentRepo;
		this.studentCrud = studentCrud;
	}

	@GetMapping("/All")
	public String selectAllStudents(Model model) {
		List<Student> allStudents = (List<Student>) studentRepo.findAll();
		model.addAttribute(STUDENT_ATTRIBUTE, allStudents);
		return "Student-All";
	}

	@GetMapping("/All/{id}")
	public String selectCourseById(@PathVariable long id, Model model) {
		try {

			model.addAttribute(STUDENT_ATTRIBUTE, studentCrud.retrieveOneStudentById(id));
			return "Student-One";
		} catch (Exception e) {

			return "error-page";
		}
	}

	@GetMapping("/update/{id}")
	public String updateStudent(@PathVariable long id, Model model) throws Exception {

		Student student = studentCrud.retrieveOneStudentById(id);

		model.addAttribute("Student", student);

		return "Student-Update";
	}

	@PostMapping("/update/By/{id}")
	public String updateStudentById(@PathVariable long id, @ModelAttribute("Student") @Valid Student updatedStudent,
			Model model) throws Exception {

		Student temp = studentCrud.retrieveOneStudentById(id);
		temp.setName(updatedStudent.getName());
		temp.setSurname(updatedStudent.getSurname());
		temp.setMatriculaNo(updatedStudent.getMatriculaNo());
		temp.setDebtCourses(updatedStudent.getDebtCourses());

		studentCrud.updateStudentByParams(temp);

		return REDIRECT_TO_SHOW_ALL;

	}

	@PostMapping("remove/{id}")
	public String deleteStudentById(@PathVariable("id") long id, Model model) {
		try {
			studentRepo.deleteById(id);
			model.addAttribute(STUDENT_ATTRIBUTE, studentCrud.selectAllStudents());
			return "redirect:/course/showAll";
		} catch (EmptyResultDataAccessException e) {
			model.addAttribute("errorMessage", "Student with ID " + id + " not found");
			return "redirect:/course/showAll";
		} catch (DataAccessException e) {
			model.addAttribute("errorMessage", "An error occurred while deleting the student");
			return "redirect:/course/showAll";
		} catch (Exception e) {
			model.addAttribute("errorMessage", "An unexpected error occurred");
			return "redirect:/course/showAll";
		}
	}

	@GetMapping("/AddPage")
	public String addNewStudent(Model model) {

		return "Student-Add";
	}

	@PostMapping("/Add")
	public String addNewStudent2(@ModelAttribute("StudentForm") @Valid Student student) {

		Student temp = new Student();
		temp.setName(student.getName());
		temp.setSurname(student.getSurname());
		temp.setMatriculaNo(student.getMatriculaNo());
		temp.setPersoncode(student.getPersoncode());

		studentRepo.save(temp);

		return REDIRECT_TO_SHOW_ALL;
	}

}
