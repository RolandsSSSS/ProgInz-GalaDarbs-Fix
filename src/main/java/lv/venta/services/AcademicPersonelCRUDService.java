package lv.venta.services;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lv.venta.models.Comment;
import lv.venta.models.Course;
import lv.venta.models.Thesis;
import lv.venta.models.users.AcademicPersonel;
import lv.venta.repos.ICommentRepo;
import lv.venta.repos.IThesisRepo;
import lv.venta.repos.users.IAcademicPersonelRepo;
import lv.venta.services.impl.IAcademicPersonelCRUDService;

@Service
public class AcademicPersonelCRUDService implements IAcademicPersonelCRUDService {

	@Autowired
	private IAcademicPersonelRepo academicPersonelRepo;

	@Autowired
	private IThesisRepo thesisRepo;

	@Autowired
	private ICommentRepo commentRepo;

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

	@Override
	public void deleteAcademicPersonelById(long idp) {
		AcademicPersonel academicPersonel = selectAcademicPersonelById(idp);
		if (academicPersonel != null) {
			/*
			for (Thesis thesis : academicPersonel.getThesis()) {
				thesisRepo.delete(thesis);
			}
			for (Thesis thesis : academicPersonel.getThesisForReviews()) {
				thesisRepo.delete(thesis);
			}
			for (Comment comment : academicPersonel.getComments()) {
				commentRepo.delete(comment);
			}
			*/
			academicPersonelRepo.delete(academicPersonel);
		}
	}

	@Override
	public void updateAcademicPersonelById(long idp, AcademicPersonel updatedAcademicPersonel) {
		 AcademicPersonel academicPersonel = selectAcademicPersonelById(idp);
		    if (academicPersonel != null) {
		        academicPersonel.setName(updatedAcademicPersonel.getName());
		        academicPersonel.setSurname(updatedAcademicPersonel.getSurname());
		        academicPersonel.setPersoncode(updatedAcademicPersonel.getPersoncode());
		        academicPersonel.setDegree(updatedAcademicPersonel.getDegree());
		        academicPersonelRepo.save(academicPersonel);
		    }
	}
}
