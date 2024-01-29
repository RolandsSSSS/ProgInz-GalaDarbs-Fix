package lv.venta.services;

import java.util.ArrayList;
import java.util.List;

import lv.venta.models.users.Person;
import lv.venta.services.exceptions.PersonDeletionException;
import lv.venta.services.exceptions.PersonInsertionException;
import lv.venta.services.exceptions.PersonNotFoundException;
import lv.venta.services.exceptions.PersonUpdateException;
import lv.venta.services.exceptions.PersonsNotFoundException;
import lv.venta.services.exceptions.WrongIdException;

public interface IPersonCRUD {

	Person retrieveOnePersonById(Long id) throws PersonNotFoundException, WrongIdException;

	ArrayList<Person> retrieveAllPersonsByTitle(String title) throws PersonsNotFoundException;

	Person insertPersontByParams(Person person) throws PersonInsertionException;

	void deletePersonById(Long id) throws PersonDeletionException, WrongIdException;

	List<Person> selectAllPersons();

	Person updatePersonByParams(Person person) throws PersonUpdateException;

}