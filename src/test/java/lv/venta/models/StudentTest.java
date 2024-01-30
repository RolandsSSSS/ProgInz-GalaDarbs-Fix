package lv.venta.models;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import lv.venta.models.users.Student;

@SpringBootTest
class StudentTest {

	@Test
	void testMatrNr() {
		String matikulasNr = "123456789";

		Student student = new Student();
		student.setMatriculaNo(matikulasNr);

		assertEquals(matikulasNr, student.getMatriculaNo());
	}

	@Test
	void testFinParadu() {
		boolean finParads = true;

		Student student = new Student();
		student.setFinancialDebt(finParads);

		assertEquals(finParads, student.isFinancialDebt());
	}

	@Test
	void testParaduKursusKolekciju() {
		Course course = new Course("Testa kurss", 4);
		Student student = new Student();

		student.addDebtCourse(course);

		assertTrue(student.getDebtCourses().contains(course));
	}

}
