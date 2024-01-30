package lv.venta.models;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class TranslateTest {
	
	@Test
	void test() {
		assertThrows(IllegalStateException.class, () -> new Translate());
	}

}
