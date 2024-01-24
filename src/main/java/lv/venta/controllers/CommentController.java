package lv.venta.controllers;

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
	public static final String COMMENT_ATTRIBUTE = "MyComments";
	private static final String REDIRECT_TO_SHOW_ALL = "redirect:/comment/showAll";

	private CommentCRUDService commentService;
	
	public CommentController(CommentCRUDService commentService) {
        this.commentService = commentService;
    }
	
	@GetMapping("/showAll")
	public String selectAllComments(org.springframework.ui.Model comment) {
		comment.addAttribute(COMMENT_ATTRIBUTE, commentService.selectAllComments());
		return "comment-all-page";
	}
	
	@GetMapping("/showAll/{id}")
	public String selectCommentById(@PathVariable long id, org.springframework.ui.Model comment) {
		comment.addAttribute(COMMENT_ATTRIBUTE, commentService.selectCommentById(id));
		return "comment-one-page";
	}
	
	@GetMapping("/remove/{id}")
	public String deleteCommentById(@PathVariable long id, org.springframework.ui.Model comment) {
		commentService.deleteCommentById(id);
		comment.addAttribute(COMMENT_ATTRIBUTE, commentService.selectAllComments());
		return REDIRECT_TO_SHOW_ALL;
	}
	
	@GetMapping("/update/{id}")
	public String showUpdateForm(@PathVariable long id, org.springframework.ui.Model comment) {
		Comment temp = commentService.selectCommentById(id);
		if (temp != null) {
			comment.addAttribute("updatedComment", temp);
			return "comment-update-page";
		} else {
			comment.addAttribute(COMMENT_ATTRIBUTE, commentService.selectAllComments());
			return REDIRECT_TO_SHOW_ALL;
		}
	}

	@PostMapping("/update/{id}")
	public String updateCommentById(@PathVariable long id, @Valid Comment updatedComment, BindingResult bindingResult, org.springframework.ui.Model comment) {
		 if (bindingResult.hasErrors()) {
			 comment.addAttribute("updatedComment", updatedComment);
			 return "comment-update-page";
		 }
		
		commentService.updateCommentById(id, updatedComment);
		comment.addAttribute(COMMENT_ATTRIBUTE, commentService.selectAllComments());
		return REDIRECT_TO_SHOW_ALL;
	}
	
	@GetMapping("/addNew")
	public String showAddCommentForm(Comment comment) {
		return "comment-add-page";
	}

	@PostMapping("/addNew")
	public String addNewComment(@Valid Comment comment, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return "comment-add-page";
		}
		Comment newComment = new Comment(comment.getDescription(), comment.getPersonel(), comment.getThesis());
		commentService.insertNewComment(newComment);

		return REDIRECT_TO_SHOW_ALL;
	}
}
