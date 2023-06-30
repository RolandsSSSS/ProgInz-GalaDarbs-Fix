package lv.venta.services;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
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
		Person insertPersontByParams( String name,String surname, String personcode,User user) throws Exception;

		Person updatePersonByParams(Long id, String name, String surname, String personcode, User user)
				throws Exception;

		void deletePersonById(Long id) throws Exception;
		
		
	

}
