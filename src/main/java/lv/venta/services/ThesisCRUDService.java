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
public class ThesisCRUDService implements IThesisCRUDService{

	@Autowired
	private IThesisRepo thesisRepo;
	
	@Autowired
	private ICommentRepo commentRepo;
	
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

	@Override
	public void deleteThesisById(long idt) {
		Thesis thesis = selectThesisById(idt);
	    if (thesis != null) {
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
	    }
	}
}
