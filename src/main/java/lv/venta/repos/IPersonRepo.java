package lv.venta.repos;

import java.util.ArrayList;

import org.springframework.data.repository.CrudRepository;

import lv.venta.models.users.Person;

public interface IPersonRepo  extends CrudRepository<Person, Long>{

	ArrayList<Person> findByName(String var);
}
