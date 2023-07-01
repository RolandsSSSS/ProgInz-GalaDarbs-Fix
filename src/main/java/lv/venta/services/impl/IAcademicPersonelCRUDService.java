package lv.venta.services.impl;

import java.util.List;

import lv.venta.models.users.AcademicPersonel;

public interface IAcademicPersonelCRUDService {
	
	List<AcademicPersonel> selectAllAcademicPersonels();
	
	AcademicPersonel selectAcademicPersonelById(long idp);
	
	void deleteAcademicPersonelById(long idp);
	
	void updateAcademicPersonelById(long idp, AcademicPersonel updatedAcademicPersonel);
}
