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
	private final SystemLogger systemLogger;
	
	@Autowired
	private IAcademicPersonelRepo academicPersonelRepo;

	@Autowired
	private IThesisRepo thesisRepo;

	@Autowired
	private ICommentRepo commentRepo;

	@Autowired
	public AcademicPersonelCRUDService(IAcademicPersonelRepo academicPersonelRepo, SystemLogger systemLogger) {
		this.academicPersonelRepo = academicPersonelRepo;
		this.systemLogger = systemLogger;
	}

	@Override
	public List<AcademicPersonel> selectAllAcademicPersonels() {
		systemLogger.logInfo("Atlasīts viss Akadēmiskais personāls.");
		return (List<AcademicPersonel>) academicPersonelRepo.findAll();
	}

	@Override
	public AcademicPersonel selectAcademicPersonelById(long idp) {
		for (AcademicPersonel academicPersonel : selectAllAcademicPersonels()) {
			if (academicPersonel.getIdp() == idp) {
				systemLogger.logInfo("Atlasīts akadēmiskais personāls ar ID: " + idp);
				return academicPersonel;
			}
		}
		systemLogger.logWarning("Akadēmiskais personāls ar ID " + idp + " netika atrasts.");
		return null;
	}

	@Override
	public void deleteAcademicPersonelById(long idp) {
		AcademicPersonel academicPersonel = selectAcademicPersonelById(idp);
		if (academicPersonel != null) {
			systemLogger.logInfo("Izdzēsts akadēmiskais personāls ar ID: " + idp);
			for(Thesis thesis : academicPersonel.getThesis()) {
				thesis.getSupervisor().remove(academicPersonel);
			}
			academicPersonel.getThesis().clear();
			academicPersonelRepo.delete(academicPersonel);
		} else {
	        systemLogger.logWarning("Mēģināts izdzēst neesošu akadēmisko personālu ar ID " + idp);
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
		        
		        systemLogger.logInfo("Akadēmiskais personāls atjaunināts ar ID: " + idp);
		    } else {
		        systemLogger.logWarning("Mēģināts atjaunināt neesošu akadēmisko personālu ar ID " + idp);
			}
	}

	@Override
	public void insertNewAcademicPersonel(AcademicPersonel academicPersonel) {
		 for (AcademicPersonel academicPersonel1 : selectAllAcademicPersonels()) {
		        if (academicPersonel1.getName().equals(academicPersonel.getName()) && 
		        	academicPersonel1.getSurname().equals(academicPersonel.getSurname()) &&
		        	academicPersonel1.getPersoncode().equals(academicPersonel.getPersoncode())) {
		            return;
		        }
		    }
		 	selectAllAcademicPersonels().add(academicPersonel);
		    academicPersonelRepo.save(academicPersonel);
		    systemLogger.logInfo("Ievietots jauns akadēmiskais personāls: " + academicPersonel.getName() + " " + academicPersonel.getSurname());
	}
}
