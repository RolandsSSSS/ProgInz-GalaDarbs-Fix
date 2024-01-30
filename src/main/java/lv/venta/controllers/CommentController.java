package lv.venta.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.validation.Valid;
import lv.venta.models.Comment;
import lv.venta.models.Thesis;
import lv.venta.services.CommentCRUDService;
import lv.venta.services.ThesisCRUDService;

@Controller
@RequestMapping("/comment")
public class CommentController {
	public static final String COMMENT_ATTRIBUTE = "comments";
	private static final String REDIRECT_TO_SHOW_ALL = "redirect:/comment/showAll";

	private CommentCRUDService commentService;
	private ThesisCRUDService thesisService;

	public CommentController(CommentCRUDService commentService, ThesisCRUDService thesisService) {
		this.commentService = commentService;
		this.thesisService = thesisService;
	}

	@GetMapping("/showAll")
	public String selectAllComments(Model model) {
		model.addAttribute(COMMENT_ATTRIBUTE, commentService.selectAllComments());
		return "comment-all-page";
	}

	@GetMapping("/showAll/{id}")
	public String selectCommentById(@PathVariable long id, Model model) {
		model.addAttribute(COMMENT_ATTRIBUTE, commentService.selectCommentById(id));
		return "comment-one-page";
	}

	@GetMapping("/remove/{id}")
	public String deleteCommentById(@PathVariable long id, Model model) {
		commentService.deleteCommentById(id);
		model.addAttribute(COMMENT_ATTRIBUTE, commentService.selectAllComments());
		return REDIRECT_TO_SHOW_ALL;
	}

	@GetMapping("/update/{id}")
	public String showUpdateForm(@PathVariable long id, Model model) {
		Comment temp = commentService.selectCommentById(id);
		if (temp != null) {
			model.addAttribute("updatedComment", temp);
			return "comment-update-page";
		} else {
			model.addAttribute(COMMENT_ATTRIBUTE, commentService.selectAllComments());
			return REDIRECT_TO_SHOW_ALL;
		}
	}

	@PostMapping("/update/{id}")
	public String updateCommentById(@PathVariable long id, @Valid Comment updatedComment, BindingResult bindingResult,
			Model model) {
		if (bindingResult.hasErrors()) {
			model.addAttribute("updatedComment", updatedComment);
			return "comment-update-page";
		}

		commentService.updateCommentById(id, updatedComment);
		model.addAttribute(COMMENT_ATTRIBUTE, commentService.selectAllComments());
		return REDIRECT_TO_SHOW_ALL;
	}

	@GetMapping("/addNew")
	public String showAddCommentForm(Model model) {
		model.addAttribute("comment", new Comment());
		model.addAttribute("allThesis", thesisService.selectAllThesis());
		return "comment-add-page";
	}

	@PostMapping("/addNew")
	public String addNewComment(@Valid Comment comment, BindingResult bindingResult, Model model) {
		if (bindingResult.hasErrors()) {
			model.addAttribute("allThesis", thesisService.selectAllThesis());
			return "comment-add-page";
		}
		// Save the thesis object first
		Thesis thesis = comment.getThesis();
		thesisService.insertNewThesis(thesis);

		// Set the saved thesis reference in the comment object
		comment.setThesis(thesis);

		commentService.insertNewComment(comment);

		return REDIRECT_TO_SHOW_ALL;
	}
}