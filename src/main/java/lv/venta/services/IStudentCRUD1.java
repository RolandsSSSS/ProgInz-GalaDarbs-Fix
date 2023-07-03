package lv.venta.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import lv.venta.models.users.Student;



public interface IStudentCRUD1 {
	//retrieve one by id
	Student retrieveOneStudentById(Long id) throws Exception;
	
	

	void deleteStudentById(Long id) throws Exception;

	List<Student> selectAllStudents();

	Student updateStudentByParams(Student Student) throws Exception;

	List<Student> selectAllStudent();

	Student insertStudenttByParams(Student Student) throws Exception;
}
