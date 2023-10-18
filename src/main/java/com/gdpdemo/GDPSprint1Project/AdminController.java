package com.gdpdemo.GDPSprint1Project;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.gdpdemo.GDPSprint1Project.service.AdminAuthorManager;
import com.gdpdemo.GDPSprint1Project.service.AdminManager;
import com.gdpdemo.GDPSprint1Project.service.AdminService;
import com.gdpdemo.GDPSprint1Project.service.AttachmentsManager;
import com.gdpdemo.GDPSprint1Project.service.PostsAuthorsManager;
import com.gdpdemo.GDPSprint1Project.service.PostsManager;
import com.gdpdemo.GDPSprint1Project.storage.StorageService;

public class AdminController {
public AdminService admser;
private final AdminManager adminManager;
private final AdminAuthorManager adminAuthorManager;

//private final StorageService storageService;
public AdminController(AdminService admser, AdminManager adminManager, AdminAuthorManager adminAuthorManager) {
	super();
	this.admser = admser;
	this.adminManager = adminManager;
	this.adminAuthorManager = adminAuthorManager;
}
@GetMapping("/admin")
public String listUsers(Model model)
{
	model.addAttribute("admin",admser.getAllUsers());
	return "admin";
	}




@GetMapping("/post/{id}")
public String post(@PathVariable("id") int id, Model model) {
    Posts post = adminManager.findById(id);
    model.addAttribute("post", post);
    model.addAttribute("comments", new Comments());
    return "post";
}

//edit post
/*
 * @GetMapping("/post/edit/{id}") public String editPost(Model
 * model, @PathVariable int id) { List<String> tagsWithDuplication = new
 * ArrayList<>(); //postsManager.getAllPosts().forEach(tags ->
 * tagsWithDuplication.addAll(Arrays.asList(tags.getTags().split(" "))));
 * List<String> allTags = new ArrayList<>(new HashSet<>(tagsWithDuplication));
 * Collections.sort(allTags); Posts post = postsManager.findById(id);
 * model.addAttribute("post", post); model.addAttribute("allTags", allTags);
 * model.addAttribute("attachments", post.getAttachments());
 * model.addAttribute("PostId", id);
 * 
 * return "edit-post"; }
 */



}
