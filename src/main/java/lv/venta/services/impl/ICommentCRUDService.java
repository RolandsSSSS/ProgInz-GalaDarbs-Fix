package lv.venta.services.impl;

import java.util.List;

import lv.venta.models.Comment;

public interface ICommentCRUDService {

	List<Comment> selectAllComments();
	
	Comment selectCommentById(long idco);
}
