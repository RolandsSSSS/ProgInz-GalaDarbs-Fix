package lv.venta.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.validation.Valid;
import lv.venta.models.Comment;
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
	
	@GetMapping("/remove/{id}")
	public String deleteCommentById(@PathVariable long id, org.springframework.ui.Model comment) {
		commentService.deleteCommentById(id);
		comment.addAttribute("MyComments", commentService.selectAllComments());
		return "redirect:/comment/showAll";
	}
	
	@GetMapping("/update/{id}")
	public String showUpdateForm(@PathVariable long id, org.springframework.ui.Model comment) {
		Comment temp = commentService.selectCommentById(id);
		if (temp != null) {
			comment.addAttribute("updatedComment", temp);
			return "comment-update-page";
		} else {
			comment.addAttribute("MyComments", commentService.selectAllComments());
			return "redirect:/comment/showAll";
		}
	}

	@PostMapping("/update/{id}")
	public String updateCommentById(@PathVariable long id, @Valid Comment updatedComment, BindingResult bindingResult, org.springframework.ui.Model comment) {
		 if (bindingResult.hasErrors()) {
			 comment.addAttribute("updatedComment", updatedComment);
			 return "comment-update-page";
		 }
		
		commentService.updateCommentById(id, updatedComment);
		comment.addAttribute("MyComments", commentService.selectAllComments());
		return "redirect:/comment/showAll";
	}
}
