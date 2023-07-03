package lv.venta.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lv.venta.models.users.Person;
import lv.venta.models.users.Student;
import lv.venta.repos.IPersonRepo;
import lv.venta.repos.users.IStudentRepo;

@Service
public class IStudentCRUDService implements IStudentCRUD1 {
		
	 @Autowired
	    private IStudentRepo studentRepo;
	 
	   public IStudentCRUDService(IStudentRepo studentRepo) {
	        this.studentRepo = studentRepo;
	    }

	    @Override
	    public List<Student> selectAllStudent() {
	        return (List<Student>) studentRepo.findAll();
	    }
	@Override
	public Student retrieveOneStudentById(Long id) throws Exception {
		  if (studentRepo.existsById(id)) {
	            return studentRepo.findById(id).get();
	        } else {
	            throw new Exception("Wrong id");
	        }
	    }
	

	
	@Override
	public Student insertStudenttByParams(Student Student) throws Exception {
		studentRepo.save(Student);
        return Student;
	}

	@Override
	public void deleteStudentById(Long id) throws Exception {
		if (studentRepo.existsById(id)) {
			studentRepo.deleteById(id);
        } else {
            throw new Exception("Wrong id");
        }
    }
	@Override
	public List<Student> selectAllStudents() {
		  return (List<Student>) studentRepo.findAll();
	}

	@Override
	public Student updateStudentByParams(Student Student) throws Exception {
		
		studentRepo.save(Student);
         return Student;
    }

	

}
