package lv.venta.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import lv.venta.models.Course;
import lv.venta.models.users.Person;
import lv.venta.models.users.User;
import lv.venta.repos.ICourseRepo;
import lv.venta.repos.IPersonRepo;

@Service
public class IPersonCRUDService implements IPersonCRUD {

    User us1 = new User("123", "karina.krinkele@venta.lv");

    private ArrayList<Person> allProducts = new ArrayList<>(Arrays.asList(new Person("John", "Doe", "123456789012", us1)));

    @Autowired
    private IPersonRepo personRepo;

    public IPersonCRUDService(IPersonRepo personRepo) {
        this.personRepo = personRepo;
    }

    @Override
    public List<Person> selectAllPersons() {
        return (List<Person>) personRepo.findAll();
    }

    @Override
    public ArrayList<Person> retrieveAllPersons() {
        return allProducts;
    }

    @Override
    public ArrayList<Person> retrieveAllPersonsByTitle(String title) throws Exception {
        if (title != null) {
            ArrayList<Person> allProductsWithTitle = personRepo.findByName(title);
            return allProductsWithTitle;
        } else {
            throw new Exception("Wrong title");
        }
    }

    @Override
    public Person insertPersontByParams(Person person) throws Exception {
        for (Person temp : allProducts) {
            if (temp.getName().equals(person.getName()) && temp.getSurname().equals(person.getSurname()) && temp.getPersoncode().equals(person.getPersoncode())) {
                throw new Exception("Tada persona jau existe");
            }
        }

        personRepo.save(person);
        return person;
    }


    @Override
    public Person updatePersonByParams(Person person) throws Exception {
    	 for (Person temp : allProducts) {
             if (temp.getName().equals(person.getName()) && temp.getSurname().equals(person.getSurname()) || temp.getPersoncode().equals(person.getPersoncode())) {
                 throw new Exception("Tada persona jau existe");
             }
             
         }
    	 personRepo.save(person);
         return person;
    }

    @Override
    public void deletePersonById(Long id) throws Exception {
        if (personRepo.existsById(id)) {
            personRepo.deleteById(id);
        } else {
            throw new Exception("Wrong id");
        }
    }


    @Override
    public Person retrieveOnePersonById(Long id) throws Exception {
        if (personRepo.existsById(id)) {
            return personRepo.findById(id).get();
        } else {
            throw new Exception("Wrong id");
        }
    }

    public Model selectPersonById(long id, Model model) throws Exception {
      
        
            Person selectedPerson = retrieveOnePersonById(id);
            model.addAttribute("AllPersons", selectedPerson);
            return model;
       
        }}
