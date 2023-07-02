package lv.venta.services.impl;

import java.util.List;

import lv.venta.models.Thesis;
import lv.venta.models.users.AcademicPersonel;

public interface IThesisCRUDService {

	List<Thesis> selectAllThesis();

	Thesis selectThesisById(long idt);
	
	void deleteThesisById(long idt);
	
	void updateThesisById(long idt, Thesis updatedThesis);
}
