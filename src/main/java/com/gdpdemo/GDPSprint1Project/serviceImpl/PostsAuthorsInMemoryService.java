package com.gdpdemo.GDPSprint1Project.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gdpdemo.GDPSprint1Project.Posts;
import com.gdpdemo.GDPSprint1Project.PostsAuthors;
import com.gdpdemo.GDPSprint1Project.PostsAuthorsId;
import com.gdpdemo.GDPSprint1Project.Repository.HomeRepository;
import com.gdpdemo.GDPSprint1Project.Repository.PostsAuthorsRepository;
import com.gdpdemo.GDPSprint1Project.Repository.PostsRepository;
import com.gdpdemo.GDPSprint1Project.service.PostsAuthorsManager;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class PostsAuthorsInMemoryService implements PostsAuthorsManager {

	@Autowired
	private PostsAuthorsRepository postsAuthorsRepository;

	@Autowired
	private PostsRepository postsRepository;

	@Autowired
	private HomeRepository homeRepository;

	@Override
	public void save(int id_post, int id_author) {
		PostsAuthorsId postsAuthorsId = new PostsAuthorsId(id_post, id_author);
		PostsAuthors postsAuthors = new PostsAuthors(postsAuthorsId, postsRepository.findById(id_post).get(),
				homeRepository.findById(id_author).get());
		postsAuthorsRepository.save(postsAuthors);
	}

	@Override
	public void removeByPost(int id_post) {
		postsAuthorsRepository.deletePostsAuthors(id_post);
	}

	@Override
	public List<PostsAuthors> getAllPostsAuthors() {
		return postsAuthorsRepository.findAll();
	}

	@Override
	public List<Posts> getAllPostsByAuthors(int id_author) {
		List<Posts> postsList = new ArrayList<>();
		for (PostsAuthors pa : postsAuthorsRepository.findAll()) {
			if (pa.getId_author().getId() == id_author)
				postsList.add(postsRepository.findById(pa.getId_post().getId()).get());
		}
		return postsList;
	}

}
