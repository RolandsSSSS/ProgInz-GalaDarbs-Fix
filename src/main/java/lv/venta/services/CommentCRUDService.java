package lv.venta.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lv.venta.models.Comment;
import lv.venta.repos.ICommentRepo;
import lv.venta.services.impl.ICommentCRUDService;

@Service
public class CommentCRUDService implements ICommentCRUDService {
	
	@Autowired
	private final SystemLogger systemLogger;

	@Autowired
	private ICommentRepo commentRepo;

	@Autowired
	public CommentCRUDService(ICommentRepo commentRepo, SystemLogger systemLogger) {
		this.commentRepo = commentRepo;
		this.systemLogger = systemLogger;
	}

	@Override
	public List<Comment> selectAllComments() {
		systemLogger.logInfo("Atlasīti visi komentāri.");
		return (List<Comment>) commentRepo.findAll();
	}

	@Override
	public Comment selectCommentById(long idco) {
		for (Comment comment : selectAllComments()) {
			if (comment.getIdco() == idco) {
				systemLogger.logInfo("Atlasīts komentārs ar ID: " + idco);
				return comment;
			}
		}
		systemLogger.logWarning("Komentārs ar ID " + idco + " netika atrasts.");
		return null;
	}

	@Override
	public void deleteCommentById(long idco) {
		Comment comment = selectCommentById(idco);
		if (comment != null) {
			systemLogger.logInfo("Izdzēsts komentārs ar ID: " + idco);
			commentRepo.delete(comment);
		} else {
	        systemLogger.logWarning("Mēģināts izdzēst neesošu komentāru ar ID " + idco);
		}
	}

	@Override
	public void updateCommentById(long idco, Comment updatedComment) {
		Comment comment = selectCommentById(idco);
		if (comment != null) {
			comment.setDescription(updatedComment.getDescription());
			commentRepo.save(comment);
			
			systemLogger.logInfo("Komentārs atjaunināts ar ID: " + idco);
		} else {
	        systemLogger.logWarning("Mēģināts atjaunināt neesošu komentāru ar ID " + idco);
		}
	}

	@Override
	public void insertNewComment(Comment comment) {
		for (Comment comment1 : selectAllComments()) {
			if (comment1.getDescription().equals(comment.getDescription())) {
				return;
			}
		}
		selectAllComments().add(comment);
		commentRepo.save(comment);
		
		systemLogger.logInfo("Ievietots jauns komentars: " + comment.getDescription());
	}
}
