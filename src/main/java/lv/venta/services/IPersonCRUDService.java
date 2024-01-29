package lv.venta.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import lv.venta.models.users.Person;
import lv.venta.repos.IPersonRepo;
import lv.venta.services.exceptions.PersonNotFoundException;
import lv.venta.services.exceptions.SelectPersonException;
import lv.venta.services.exceptions.WrongIdException;

@Service
public class IPersonCRUDService implements IPersonCRUD {

	@Autowired
	private final SystemLogger systemLogger;

	@Autowired
	private IPersonRepo personRepo;

	public IPersonCRUDService(IPersonRepo personRepo, SystemLogger systemLogger) {
		this.personRepo = personRepo;
		this.systemLogger = systemLogger;
	}

	@Override
	public List<Person> selectAllPersons() {
		systemLogger.logInfo("Atlasītas visas personas.");
		return (List<Person>) personRepo.findAll();
	}

	@Override
	public ArrayList<Person> retrieveAllPersonsByTitle(String title) {
		if (title != null) {
			return personRepo.findByName(title);
		} else {
			systemLogger.logError("Metode retrieveAllPersonsByTitle izraisīja izņēmuma situāciju: Wrong title");
			throw new IllegalArgumentException("Wrong title");
		}
	}

	@Override
	public Person insertPersontByParams(Person person) {

		personRepo.save(person);
		systemLogger.logInfo("Ievietots jauna persona: " + person);
		return person;
	}

	@Override
	public Person updatePersonByParams(Person person) {

		personRepo.save(person);
		systemLogger.logInfo("Atjaunināta persona: " + person.getName() + " " + person.getSurname());
		return person;
	}

	@Override
	public void deletePersonById(Long id) throws WrongIdException {
		if (personRepo.existsById(id)) {
			systemLogger.logInfo("Izdzēsta persona ar ID: " + id);
			personRepo.deleteById(id);
		} else {
			systemLogger.logError("Metode deletePersonById izraisīja izņēmuma situāciju: Wrong id");
			throw new WrongIdException("Wrong id");
		}
	}

	@Override
	public Person retrieveOnePersonById(Long id) throws WrongIdException {
		if (personRepo.existsById(id)) {
			systemLogger.logInfo("Atlasīta persona ar ID: " + id);

			Optional<Person> optionalPerson = personRepo.findById(id);

			if (optionalPerson.isPresent()) {
				return optionalPerson.get();
			} else {
				systemLogger.logError(
						"Metode retrieveOnePersonById izraisīja izņēmuma situāciju: Empty result for ID: " + id);
				throw new PersonNotFoundException("Empty result for ID: " + id);
			}
		} else {
			systemLogger.logError("Metode retrieveOnePersonById izraisīja izņēmuma situāciju: Wrong id");
			throw new WrongIdException("Wrong id");
		}
	}

	public Model selectPersonById(long id, Model model) throws SelectPersonException {
		try {
	        Person selectedPerson = retrieveOnePersonById(id);
	        model.addAttribute("AllPersons", selectedPerson);
	        return model;
	    } catch (PersonNotFoundException e) {
	        throw new SelectPersonException("Error selecting person. Person not found.", e);
	    } catch (WrongIdException e) {
	        throw new SelectPersonException("Error selecting person. Wrong ID.", e);
	    }
	}
}
