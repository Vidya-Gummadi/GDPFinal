package com.gdpdemo.GDPSprint1Project;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.gdpdemo.GDPSprint1Project.service.AttachmentsManager;
import com.gdpdemo.GDPSprint1Project.service.PostsAuthorsManager;
import com.gdpdemo.GDPSprint1Project.service.PostsManager;
import com.gdpdemo.GDPSprint1Project.storage.StorageService;
import com.gdpdemo.GDPSprint1Project.service.CommentsManager;

@Controller
public class PostController {
	private final CommentsManager commentsManager;
	private final PostsManager postsManager;
	private final PostsAuthorsManager postsAuthorsManager;
	private final AttachmentsManager attachmentsManager;
	private final StorageService storageService;

	@Autowired
	public PostController(PostsManager postsManager, PostsAuthorsManager postsAuthorsManager,
			CommentsManager commentsManager, AttachmentsManager attachmentsManager, StorageService storageService) {
		this.postsManager = postsManager;
		this.postsAuthorsManager = postsAuthorsManager;
		this.commentsManager = commentsManager;
		this.attachmentsManager = attachmentsManager;
		this.storageService = storageService;
	}

	@GetMapping("/post/{id}")
	public String post(@PathVariable("id") int id, Model model, HttpSession session) {
		Posts post = postsManager.findById(id);
		System.out.println(post.getPost_content());
		model.addAttribute("post", post);
		model.addAttribute("comments", new Comments());
		return "post";
	}

	// edit post
	@GetMapping("/post/edit/{id}")
	public String editPost(Model model, @PathVariable int id) {
		Posts post = postsManager.findById(id);
		model.addAttribute("post", post);
		model.addAttribute("attachments", post.getAttachments());
		model.addAttribute("PostId", id);
		return "edit-post";
	}

	@PostMapping("/post/update/{id}")
	public String updatePost(@PathVariable int id, @RequestBody @Valid @ModelAttribute("posts") Posts postss,
			BindingResult br, BindingResult bindingResultForId, @RequestParam("files") MultipartFile[] files,
			HttpSession session) {
		if (br.hasErrors() || bindingResultForId.hasErrors()) {
			return "edit-post";
		}
		postss.setContent(splitString(postss.getPost_content()));
		postsManager.save(postss);
		// postsAuthorsManager.save(postss.getId(), home.getId());
		storageService.store(files);
		attachmentsManager.save(files, postss.getId());

		return "redirect:/post/" + id;
	}

	public String splitString(String post_content) {
		String s = "";
		if (post_content.length() > 150) {
			s = post_content.substring(0, 150) + "....";
		}
		return s;
	}

	// delete post
	@GetMapping("/post/delete/{id}/{category}")
	public String deletePost(@PathVariable("id") int id, @PathVariable("category") String category, Model model) {
		attachmentsManager.removeByPostId(id);
		commentsManager.removeByPostId(id);
		postsAuthorsManager.removeByPost(id);
		postsManager.remove(id);
		model.addAttribute("title", "My Post");
		model.addAttribute("posts", postsManager.getAllPostsOrdered(-1, null, null, category));
		return "myposts";
	}

}
