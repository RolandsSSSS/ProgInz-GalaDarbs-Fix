package lv.venta.services;

import java.util.ArrayList;


import org.springframework.beans.factory.annotation.Autowired;

import lv.venta.models.users.Person;
import lv.venta.models.users.User;
import lv.venta.repos.IPersonRepo;

public class IPersonCRUDService implements IPersonCRUD {


	@Autowired
	private  IPersonRepo personRepo;
	
	ArrayList<Person> personList = (ArrayList<Person>) personRepo.findAll();

	

	@Override
	public ArrayList<Person> retrieveAllPersons() {
		return (ArrayList<Person>)personRepo.findAll();
	}

	@Override
	public Person retrieveOnePersonById(Long id) throws Exception {
		if(personRepo.existsById(id))
		{
			return personRepo.findById(id).get();
		}
		else
		{
			throw new Exception("Wrong id");
		}
	}

	@Override
	public ArrayList<Person> retrieveAllPersonsByTitle(String title) throws Exception {
		if(title!=null) {
			ArrayList<Person> allProductsWithTitle = personRepo.findByName(title);
			return allProductsWithTitle;
		}
		else
		{
			throw new Exception("Wrong title");
		}
	}

	@Override
	public Person insertPersontByParams(String name, String surname, String personcode, User user) throws Exception {
		for (Person temp : personList) {
			if (temp.getName().equals(name) && temp.getSurname().equals(name)
					&& temp.getPersoncode().equals(personcode)) {
				
				Person newProduct = new Person(name, surname, personcode, user);
			//Sketchy
				personList.add(newProduct);
				return newProduct;
			}
		}
		
		throw new Exception("Tada persona jau existe title");
	}
		
	

	@Override
	public Person updatePersonByParams(Long id, String name, String surname, String personcode, User user) throws Exception {
		for (Person temp : personList) {
			if (temp.getIdp() == id) {
				temp.setName(name);
				temp.setSurname(surname);
				temp.setPersoncode(personcode);
				temp.setUser(user);
				return temp;
			}
		}
		
		throw new Exception("Tada persona neexiste");
	}
	@Override
	public void deletePersonById(Long id) throws Exception {
		boolean isFound = false;
		for (Person temp : personList) {
			if (temp.getIdp() == id) {
				personList.remove(temp);
				isFound = true;
				break;
			}
		}
		if(!isFound)
		{
			throw new Exception("Wrong id");
		}
	}}



