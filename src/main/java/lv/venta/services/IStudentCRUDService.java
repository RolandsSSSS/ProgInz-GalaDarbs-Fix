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
	private final SystemLogger systemLogger;
		
	 @Autowired
	    private IStudentRepo studentRepo;
	 
	   public IStudentCRUDService(IStudentRepo studentRepo, SystemLogger systemLogger) {
	        this.studentRepo = studentRepo;
	        this.systemLogger = systemLogger;
	    }

	    @Override
	    public List<Student> selectAllStudent() {
	    	systemLogger.logInfo("Atlasīti visi studenti.");
	        return (List<Student>) studentRepo.findAll();
	    }
	@Override
	public Student retrieveOneStudentById(Long id) throws Exception {
		  if (studentRepo.existsById(id)) {
			  systemLogger.logInfo("Atlasīts students ar ID: " + id);
	            return studentRepo.findById(id).get();
	        } else {
	        	systemLogger.logError("Metode retrieveOneStudentById izraisīja izņēmuma situāciju: Wrong id");
	            throw new Exception("Wrong id");
	        }
	    }
	

	
	@Override
	public Student insertStudenttByParams(Student Student) throws Exception {
		systemLogger.logInfo("Pievienots jauns students ar ID: " + Student.getIdp());
		studentRepo.save(Student);
        return Student;
	}

	@Override
	public void deleteStudentById(Long id) throws Exception {
		if (studentRepo.existsById(id)) {
			systemLogger.logInfo("Izdzēsts students ar ID: " + id);
			studentRepo.deleteById(id);
        } else {
        	systemLogger.logError("Metode deleteStudentById izraisīja izņēmuma situāciju: Wrong id");
            throw new Exception("Wrong id");
        }
    }
	@Override
	public List<Student> selectAllStudents() {
		systemLogger.logInfo("Atlasīti visi studenti.");
		  return (List<Student>) studentRepo.findAll();
	}

	@Override
	public Student updateStudentByParams(Student Student) throws Exception {
		
		studentRepo.save(Student);
		systemLogger.logInfo("Students atjaunināts ar ID: " + Student.getIdp());
         return Student;
    }

	

}
