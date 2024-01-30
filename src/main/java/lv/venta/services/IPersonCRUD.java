package lv.venta.services;

import java.util.ArrayList;
import java.util.List;

import lv.venta.models.users.Person;

public interface IPersonCRUD {

	Person retrieveOnePersonById(Long id) throws Exception;

	ArrayList<Person> retrieveAllPersonsByTitle(String title) throws Exception;

	Person insertPersontByParams(Person person) throws Exception;

	void deletePersonById(Long id) throws Exception;

	List<Person> selectAllPersons();

	Person updatePersonByParams(Person person) throws Exception;

}