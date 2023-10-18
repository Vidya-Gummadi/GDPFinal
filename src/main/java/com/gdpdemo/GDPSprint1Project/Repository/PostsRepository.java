package com.gdpdemo.GDPSprint1Project.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.gdpdemo.GDPSprint1Project.Home;
import com.gdpdemo.GDPSprint1Project.Posts;

@Repository
public interface PostsRepository extends JpaRepository<Posts, Integer> {

	@Query("select u from Posts u where u.category = :category")
	public List<Posts> findPostsByCategory(@Param("category") String category);

}
