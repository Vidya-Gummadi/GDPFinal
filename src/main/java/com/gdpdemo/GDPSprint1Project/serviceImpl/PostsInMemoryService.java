package com.gdpdemo.GDPSprint1Project.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gdpdemo.GDPSprint1Project.Posts;
import com.gdpdemo.GDPSprint1Project.Repository.PostsRepository;
import com.gdpdemo.GDPSprint1Project.service.PostsManager;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional
public class PostsInMemoryService implements PostsManager {

	@Autowired
	private PostsRepository postsRepository;

	@Override
	public void save(Posts post) {
		postsRepository.save(post);
	}

	@Override
	public List<Posts> getAllPostsOrdered(int order, Map<Integer, Integer> comments,
			Map<Integer, Integer> attachments,String category) {
		List<Posts> posts = postsRepository.findPostsByCategory(category);;
		List<Posts> list = new ArrayList<>();
		List<Integer> sorted;
		switch (order) {
		case 1:
			return posts;
		case 2:
			sorted = comments.entrySet().stream().sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
					.map(Map.Entry::getKey).collect(Collectors.toList());
			for (Integer id : sorted) {
				for (Posts post : posts) {
					if (id == post.getId())
						list.add(post);
				}
			}
			return list;
		case 3:
			sorted = comments.entrySet().stream().sorted(Map.Entry.comparingByValue()).map(Map.Entry::getKey)
					.collect(Collectors.toList());
			for (Integer id : sorted) {
				for (Posts post : posts) {
					if (id == post.getId())
						list.add(post);
				}
			}
			return list;
		case 4:
			sorted = attachments.entrySet().stream().sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
					.map(Map.Entry::getKey).collect(Collectors.toList());
			for (Integer id : sorted) {
				for (Posts post : posts) {
					if (id == post.getId())
						list.add(post);
				}
			}
			return list;
		case 5:
			sorted = attachments.entrySet().stream().sorted(Map.Entry.comparingByValue()).map(Map.Entry::getKey)
					.collect(Collectors.toList());
			for (Integer id : sorted) {
				for (Posts post : posts) {
					if (id == post.getId())
						list.add(post);
				}
			}
			return list;
		default:
			posts.sort(Comparator.comparing(Posts::getId).reversed());
			return posts;
		}
	}

	@Override
	public Posts findById(int id) {
		return postsRepository.findById(id).get();
	}

	@Override
	public void remove(int id) {
		postsRepository.deleteById(id);
	}

	public String split(String post_content) {
		String s = " ";
		if (post_content.length() > 150) {
			s = post_content.substring(0, 250);
		}
		return s;
	}

	@Override
	public List<Posts> getPostByCategory(String category) {

		List<Posts> posts = this.postsRepository.findPostsByCategory(category);

		return posts;

	}

}
