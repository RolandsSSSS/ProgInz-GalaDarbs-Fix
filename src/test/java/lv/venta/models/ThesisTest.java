package lv.venta.models;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ThesisTest {
	
	@Test
	void test() {
		long id = 0;
		String nosaukumsLv = "TestTitleLv";
		String nosaukumsEn = "TestTitleEn";
		String merkis = "TestAim";
		String uzd = "TestTasks";
		LocalDateTime datums = LocalDateTime.now();
		boolean status = true;
		AcceptanceStatus accStatus = AcceptanceStatus.SUBMITTED;
		LocalDateTime accDatums = LocalDateTime.now();

		Thesis thesis = new Thesis(nosaukumsLv, nosaukumsEn, merkis, uzd, null, null);

		thesis.setSubmitDateTime(datums);
		thesis.setStatusFromSupervisor(status);
		thesis.setAccStatus(accStatus);
		thesis.setAccDateTime(accDatums);


		assertEquals(id, thesis.getIdt());
		assertEquals(nosaukumsLv, thesis.getTitleLv());
		assertEquals(nosaukumsEn, thesis.getTitleEn());
		assertEquals(merkis, thesis.getAim());
		assertEquals(uzd, thesis.getTasks());
		assertEquals(datums, thesis.getSubmitDateTime());
		assertEquals(status, thesis.isStatusFromSupervisor());
		assertEquals(accStatus, thesis.getAccStatus());
		assertEquals(accDatums, thesis.getAccDateTime());

	}

}
