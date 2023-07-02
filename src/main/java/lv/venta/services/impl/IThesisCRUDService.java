package lv.venta.services.impl;

import java.util.List;

import lv.venta.models.Thesis;

public interface IThesisCRUDService {

	List<Thesis> selectAllThesis();

	Thesis selectThesisById(long idt);
	
	void deleteThesisById(long idt);
	
	void updateThesisById(long idt, Thesis updatedThesis);
	
	void insertNewThesis(Thesis thesis);
}
