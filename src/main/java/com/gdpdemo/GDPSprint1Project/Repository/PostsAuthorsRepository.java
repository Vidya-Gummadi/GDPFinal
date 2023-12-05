package com.gdpdemo.GDPSprint1Project.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.gdpdemo.GDPSprint1Project.PostsAuthors;

@Repository
public interface PostsAuthorsRepository extends JpaRepository<PostsAuthors, Integer> {
	@Modifying
	@Query("delete from PostsAuthors p where p.id_post.id=:id")
	void deletePostsAuthors(@Param("id") int id);
}
