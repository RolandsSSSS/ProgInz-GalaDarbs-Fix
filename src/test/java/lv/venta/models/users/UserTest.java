package lv.venta.models.users;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class UserTest {

	@Test
	void test() {
		long id = 0;
		String parole = "testaParole123";
		String email = "tests@test.lv";
		Person persona = new Person();


		User user = new User(parole, email);

		user.setPerson(persona);

		assertEquals(id, user.getIdu());
		assertEquals(parole, user.getPassword());
		assertEquals(email, user.getEmail());
		assertEquals(persona, user.getPerson());
	}

}
