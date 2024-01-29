package lv.venta.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lv.venta.models.users.Student;
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

	public class NotFoundException extends RuntimeException {

		private static final long serialVersionUID = 6336182189635099569L;

		public NotFoundException(String message) {
			super(message);
		}
	}

	public class WrongIdException extends Exception {

		private static final long serialVersionUID = -3267013411432593271L;

		public WrongIdException(String message) {
			super(message);
		}
	}

	@Override
	public Student retrieveOneStudentById(Long id) throws Exception {
		if (studentRepo.existsById(id)) {
			systemLogger.logInfo("Atlasīts students ar ID: " + id);

			Optional<Student> optionalStudent = studentRepo.findById(id);

			if (optionalStudent.isPresent()) {
				return optionalStudent.get();
			} else {
				systemLogger.logError(
						"Metode retrieveOneStudentById izraisīja izņēmuma situāciju: Empty result for ID: " + id);
				throw new NotFoundException("Student not found for ID: " + id);

			}
		} else {
			systemLogger.logError("Metode retrieveOneStudentById izraisīja izņēmuma situāciju: Wrong id");
			throw new WrongIdException("Wrong id");
		}
	}

	@Override
	public Student insertStudenttByParams(Student student) throws Exception {
		systemLogger.logInfo("Pievienots jauns students ar ID: " + student.getIdp());
		studentRepo.save(student);
		return student;
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
	public Student updateStudentByParams(Student student) throws Exception {

		studentRepo.save(student);
		systemLogger.logInfo("Students atjaunināts ar ID: " + student.getIdp());
		return student;
	}

}
