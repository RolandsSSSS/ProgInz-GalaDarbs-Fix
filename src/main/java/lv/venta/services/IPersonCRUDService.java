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
    public Person insertPersontByParams(String name, String surname, String personcode, User user) throws Exception {
        for (Person temp : allProducts) {
            if (temp.getName().equals(name) && temp.getSurname().equals(name) && temp.getPersoncode().equals(personcode)) {

                Person newProduct = new Person(name, surname, personcode, user);
                // Sketchy
                allProducts.add(newProduct);
                return newProduct;
            }
        }

        throw new Exception("Tada persona jau existe title");
    }

    @Override
    public Person updatePersonByParams(Long id, String name, String surname, String personcode, User user) throws Exception {
        for (Person temp : allProducts) {
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
        for (Person temp : allProducts) {
            if (temp.getIdp() == id) {
                allProducts.remove(temp);
                isFound = true;
                break;
            }
        }
        if (!isFound) {
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
      
            List<Person> allPersons = (List<Person>) personRepo.findAll();
            Person selectedPerson = retrieveOnePersonById(id);
            model.addAttribute("AllPersons", selectedPerson);
            return model;
       
        }}
