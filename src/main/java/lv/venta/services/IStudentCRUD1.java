package lv.venta.services;

import java.util.List;

import lv.venta.models.users.Student;

public interface IStudentCRUD1 {
	// retrieve one by id
	Student retrieveOneStudentById(Long id) throws Exception;

	void deleteStudentById(Long id) throws Exception;

	List<Student> selectAllStudents();

	Student updateStudentByParams(Student student) throws Exception;

	Student insertStudenttByParams(Student student) throws Exception;
}
