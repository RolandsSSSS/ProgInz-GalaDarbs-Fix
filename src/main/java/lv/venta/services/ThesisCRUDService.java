package lv.venta.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lv.venta.models.Comment;
import lv.venta.models.Thesis;
import lv.venta.models.users.AcademicPersonel;
import lv.venta.repos.ICommentRepo;
import lv.venta.repos.IThesisRepo;
import lv.venta.services.impl.IThesisCRUDService;

@Service
public class ThesisCRUDService implements IThesisCRUDService {

	@Autowired
	private final SystemLogger systemLogger;

	@Autowired
	private IThesisRepo thesisRepo;

	@Autowired
	private ICommentRepo commentRepo;

	@Autowired
	public ThesisCRUDService(IThesisRepo thesisRepo, SystemLogger systemLogger) {
		this.thesisRepo = thesisRepo;
		this.systemLogger = systemLogger;
	}

	@Override
	public List<Thesis> selectAllThesis() {
		systemLogger.logInfo("Atlasīti visi diplomdarbi.");
		return (List<Thesis>) thesisRepo.findAll();
	}

	@Override
	public Thesis selectThesisById(long idt) {
		for (Thesis thesis : selectAllThesis()) {
			if (thesis.getIdt() == idt) {
				systemLogger.logInfo("Atlasīts diplomdarbs ar ID: " + idt);
				return thesis;
			}
		}
		systemLogger.logWarning("Diplomdarbs ar ID " + idt + " netika atrasts.");
		return null;
	}

	@Override
	public void deleteThesisById(long idt) {
		Thesis thesis = selectThesisById(idt);
		if (thesis != null) {
			systemLogger.logInfo("Izdzēsts diplomdarbu ar ID: " + idt);

			for (Comment comment : thesis.getComments()) {
				comment.setThesis(null);
				commentRepo.deleteById(comment.getIdco());
			}

			AcademicPersonel supervisor = thesis.getSupervisor();
			if (supervisor != null) {
				supervisor.getThesis().remove(thesis);
			}

			thesis.getComments().clear();
			thesisRepo.delete(thesis);
		} else {
			systemLogger.logWarning("Mēģināts izdzēst neesošu diplomdarbu ar ID " + idt);
		}
	}

	@Override
	public void updateThesisById(long idt, Thesis updatedThesis) {
		Thesis thesis = selectThesisById(idt);
		if (thesis != null) {
			thesis.setTitleLv(updatedThesis.getTitleLv());
			thesis.setTitleEn(updatedThesis.getTitleEn());
			thesis.setAim(updatedThesis.getAim());
			thesis.setTasks(updatedThesis.getTasks());
			thesis.setAccStatus(updatedThesis.getAccStatus());
			thesisRepo.save(thesis);

			systemLogger.logInfo("Diplomdarbs atjaunināts ar ID: " + idt);
		} else {
			systemLogger.logWarning("Mēģināts atjaunināt neesošu diplomdarbu ar ID " + idt);
		}
	}

	@Override
	public void insertNewThesis(Thesis thesis) {
		for (Thesis thesis1 : selectAllThesis()) {
			if (thesis1.getTitleLv().equals(thesis.getTitleLv()) &&
					thesis1.getTitleEn().equals(thesis.getTitleEn()) &&
					thesis1.getAim().equals(thesis.getAim()) &&
					thesis1.getTasks().equals(thesis.getTasks()) &&
					thesis1.getStudent().equals(thesis.getStudent()) &&
					thesis1.getSupervisor().equals(thesis.getSupervisor()) &&
					thesis1.getAccStatus() == thesis.getAccStatus()) {
				return;
			}
		}
		selectAllThesis().add(thesis);
		thesisRepo.save(thesis);

		systemLogger.logInfo(
				"Ievietots jauns diplomdarbs: " + "(LV) " + thesis.getTitleLv() + " (EN) " + thesis.getTitleEn());
	}
}
