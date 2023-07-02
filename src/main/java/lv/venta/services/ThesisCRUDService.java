package lv.venta.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lv.venta.models.Thesis;
import lv.venta.models.users.AcademicPersonel;
import lv.venta.repos.IThesisRepo;
import lv.venta.services.impl.IThesisCRUDService;

@Service
public class ThesisCRUDService implements IThesisCRUDService{

	@Autowired
	private IThesisRepo thesisRepo;
	
	@Autowired
	public ThesisCRUDService(IThesisRepo thesisRepo) {
		this.thesisRepo = thesisRepo;
	}
	
	@Override
	public List<Thesis> selectAllThesis() {
		return (List<Thesis>) thesisRepo.findAll();
	}

	@Override
	public Thesis selectThesisById(long idt) {
		for (Thesis thesis : selectAllThesis()) {
			if (thesis.getIdt() == idt) {
				return thesis;
			}
		}
		return null;
	}
}
