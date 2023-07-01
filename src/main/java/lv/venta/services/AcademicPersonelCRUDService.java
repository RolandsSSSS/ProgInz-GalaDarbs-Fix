package lv.venta.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lv.venta.models.users.AcademicPersonel;
import lv.venta.repos.users.IAcademicPersonelRepo;
import lv.venta.services.impl.IAcademicPersonelCRUDService;

@Service
public class AcademicPersonelCRUDService implements IAcademicPersonelCRUDService{

	@Autowired
	private IAcademicPersonelRepo academicPersonelRepo;
	
	@Autowired
	public AcademicPersonelCRUDService(IAcademicPersonelRepo academicPersonelRepo) {
		this.academicPersonelRepo = academicPersonelRepo;
	}

	@Override
	public List<AcademicPersonel> selectAllAcademicPersonels() {
		return (List<AcademicPersonel>) academicPersonelRepo.findAll();
	}

	@Override
	public AcademicPersonel selectAcademicPersonelById(long idp) {
		for (AcademicPersonel academicPersonel : selectAllAcademicPersonels()) {
			if (academicPersonel.getIdp() == idp) {
				return academicPersonel;
			}
		}
		return null;
	}
}
