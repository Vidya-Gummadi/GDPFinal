package com.gdpdemo.GDPSprint1Project.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gdpdemo.GDPSprint1Project.Attachments;

@Repository
public interface AttachmentsRepository extends JpaRepository<Attachments, Integer> {
	void deleteAllByPostsId(int id);
}
