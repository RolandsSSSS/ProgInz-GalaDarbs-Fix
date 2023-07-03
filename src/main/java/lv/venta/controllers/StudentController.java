package lv.venta.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.validation.Valid;
import lv.venta.services.IStudentCRUDService;
import lv.venta.services.IStudentCRUD1;
import lv.venta.models.Course;
import lv.venta.models.users.Student;
import lv.venta.repos.users.IStudentRepo;

@Controller
@RequestMapping("/Student")
public class StudentController {


	
	@Autowired
	private IStudentRepo studentRepo;
	@Autowired
	private IStudentCRUD1 StudentCrud;
	
	

	@GetMapping("/All")
	public String selectAllStudents(Model model) {
		List<Student> allStudents = (List<Student>) studentRepo.findAll();
		model.addAttribute("AllStudents", allStudents);
		return "Student-All";
	}
	@GetMapping("/All/{id}")
	public String selectCourseById(@PathVariable long id, Model model) {
	    try {
	  
	        model.addAttribute("AllStudents", StudentCrud.retrieveOneStudentById(id));
	        return "Student-One"; 
	    } catch (Exception e) {
	        
	        return "error-page";
	    }
	}
	
	@GetMapping("/update/{id}")
	public String UdateStudent(@PathVariable long id, Model model) throws Exception {
	  
	    Student Student = StudentCrud.retrieveOneStudentById(id);


	    model.addAttribute("Student", Student);

	    return "Student-Update";
	}

	@PostMapping("/update/By/{id}")
	public String updateStudentById(@PathVariable long id, @ModelAttribute("Student") @Valid Student updatedStudent,
	        Model model) throws Exception {
	    

	   
	    Student temp = StudentCrud.retrieveOneStudentById(id);
	    temp.setName(updatedStudent.getName());
	    temp.setSurname(updatedStudent.getSurname());
	    temp.setMatriculaNo(updatedStudent.getMatriculaNo());
	    temp.setDebtCourses(updatedStudent.getDebtCourses());
	
	    StudentCrud.updateStudentByParams(temp);

	    return "redirect:/Student/All";

	}


	
	

	@PostMapping("remove/{id}")
	public String deleteStudentById(@PathVariable("id") long id, Model model) throws Exception {
		studentRepo.deleteById(id);
		model.addAttribute("AllStudents", StudentCrud.selectAllStudents());
		return "redirect:/course/showAll";
	           
	        
	  
	}



			@GetMapping("/AddPage")
					public String AddNewStudent(Model model) {
				
					return "Student-Add";
}
			@PostMapping("/Add")
			public String addNewStudent(@ModelAttribute("StudentForm") @Valid Student Student) {
			  
			    
			    Student temp = new Student();
			    temp.setName(Student.getName());
			    temp.setSurname(Student.getSurname());
			    temp.setMatriculaNo(Student.getMatriculaNo());
			  temp.setPersoncode(Student.getPersoncode());
			    
			    studentRepo.save(temp);
			   

			    return "redirect:/Student/All";
			}
		

}
