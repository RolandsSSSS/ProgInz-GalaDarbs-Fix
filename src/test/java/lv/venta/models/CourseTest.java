package lv.venta.models;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CourseTest {

	@Test
    void test() {
        long id = 0;
        String nosaukums = "Testa kurss";
        int kPunkti = 4;

        Course course = new Course(nosaukums, kPunkti);

        assertEquals(nosaukums, course.getTitle());
        assertEquals(kPunkti, course.getCreditPoints());
        assertEquals(id, course.getIdc());

        assertNotNull(course.getDebtStudents());
        assertTrue(course.getDebtStudents().isEmpty());
    }
}
