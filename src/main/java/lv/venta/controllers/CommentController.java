package lv.venta.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import lv.venta.services.CommentCRUDService;

@Controller
@RequestMapping("/comment")
public class CommentController {

	@Autowired
	private CommentCRUDService commentService;
	
	@GetMapping("/showAll")
	public String selectAllComments(org.springframework.ui.Model comment) {
		comment.addAttribute("MyComments", commentService.selectAllComments());
		return "comment-all-page";
	}
	
	@GetMapping("/showAll/{id}")
	public String selectCommentById(@PathVariable long id, org.springframework.ui.Model comment) {
		comment.addAttribute("MyComments", commentService.selectCommentById(id));
		return "comment-one-page";
	}
}
