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
	private ICommentRepo commentRepo;

	@Autowired
	public CommentCRUDService(ICommentRepo commentRepo) {
		this.commentRepo = commentRepo;
	}

	@Override
	public List<Comment> selectAllComments() {
		return (List<Comment>) commentRepo.findAll();
	}

	@Override
	public Comment selectCommentById(long idco) {
		for (Comment comment : selectAllComments()) {
			if (comment.getIdco() == idco) {
				return comment;
			}
		}
		return null;
	}

	@Override
	public void deleteCommentById(long idco) {
		Comment comment = selectCommentById(idco);
		if (comment != null) {
			commentRepo.delete(comment);
		}
	}

	@Override
	public void updateCommentById(long idco, Comment updatedComment) {
		Comment comment = selectCommentById(idco);
		if (comment != null) {
			comment.setDescription(updatedComment.getDescription());
			commentRepo.save(comment);
		}
	}
}
