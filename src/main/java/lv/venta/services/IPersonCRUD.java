package lv.venta.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lv.venta.models.Course;
import lv.venta.models.users.Person;
import lv.venta.models.users.User;

public interface IPersonCRUD {

	
		
		//retrieve all
		ArrayList<Person> retrieveAllPersons();

		//retrieve one by id
		Person retrieveOnePersonById(Long id) throws Exception;
		
		//retrieve one by title
		ArrayList<Person> retrieveAllPersonsByTitle(String title) throws Exception;
		
		//create (insert)
		Person insertPersontByParams(Person person) throws Exception;

		

		void deletePersonById(Long id) throws Exception;

		List<Person> selectAllPersons();

		Person updatePersonByParams(Long id, Person person) throws Exception;
		
		
	

}
