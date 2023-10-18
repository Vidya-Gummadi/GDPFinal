package com.gdpdemo.GDPSprint1Project.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.gdpdemo.GDPSprint1Project.Posts;

@Service
public interface PostsManager {

	void save(Posts post);

	Posts findById(int id);

	List<Posts> getAllPostsOrdered(int order, Map<Integer, Integer> comments, Map<Integer, Integer> attachments,String category);

	void remove(int id);

	String split(String post_content);

	List<Posts> getPostByCategory(String category);
}
