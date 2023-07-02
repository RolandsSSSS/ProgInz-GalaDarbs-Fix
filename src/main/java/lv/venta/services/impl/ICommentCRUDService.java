package lv.venta.services.impl;

import java.util.List;

import lv.venta.models.Comment;

public interface ICommentCRUDService {

	List<Comment> selectAllComments();
	
	Comment selectCommentById(long idco);
	
	void deleteCommentById(long idco);
	
	void updateCommentById(long idco, Comment updatedComment);
	
	void insertNewComment(Comment comment);
}
