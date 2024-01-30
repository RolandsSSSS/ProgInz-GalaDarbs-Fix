package lv.venta.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import lv.venta.models.users.Person;
import lv.venta.repos.IPersonRepo;

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
    public ArrayList<Person> retrieveAllPersonsByTitle(String title) throws Exception {
        if (title != null) {
            ArrayList<Person> allProductsWithTitle = personRepo.findByName(title);
            return allProductsWithTitle;
        } else {
            systemLogger.logError("Metode retrieveAllPersonsByTitle izraisīja izņēmuma situāciju: Wrong title");
            throw new Exception("Wrong title");
        }
    }

    @Override
    public Person insertPersontByParams(Person person) throws Exception {

        personRepo.save(person);
        systemLogger.logInfo("Ievietots jauna persona: " + person);
        return person;
    }

    @Override
    public Person updatePersonByParams(Person person) throws Exception {

        personRepo.save(person);
        systemLogger.logInfo("Atjaunināta persona: " + person.getName() + " " + person.getSurname());
        return person;
    }

    @Override
    public void deletePersonById(Long id) throws Exception {
        if (personRepo.existsById(id)) {
            systemLogger.logInfo("Izdzēsta persona ar ID: " + id);
            personRepo.deleteById(id);
        } else {
            systemLogger.logError("Metode deletePersonById izraisīja izņēmuma situāciju: Wrong id");
            throw new Exception("Wrong id");
        }
    }

    @Override
    public Person retrieveOnePersonById(Long id) throws Exception {
        if (personRepo.existsById(id)) {
            systemLogger.logInfo("Atlasīta persona ar ID: " + id);
            return personRepo.findById(id).get();
        } else {
            systemLogger.logError("Metode retrieveOnePersonById izraisīja izņēmuma situāciju: Wrong id");
            throw new Exception("Wrong id");
        }
    }

    public Model selectPersonById(long id, Model model) throws Exception {

        Person selectedPerson = retrieveOnePersonById(id);
        model.addAttribute("AllPersons", selectedPerson);
        return model;

    }
}
