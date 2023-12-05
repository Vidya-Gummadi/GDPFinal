package com.gdpdemo.GDPSprint1Project;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "admin")
public class Admin {
	@Id
	@Column(nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column
	private Date date;

	@Size(min = 1, message = "category required")
	@Column
	private String category;
	
	
	@Size(min = 1, message = "title is required")
	@Column
	private String title;

	@OneToMany(mappedBy = "id_post", cascade = { CascadeType.MERGE })
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private List<PostsAuthors> postsAuthors;

	
	  @OneToMany(mappedBy = "posts", cascade = { CascadeType.MERGE,
	  CascadeType.PERSIST })
	  
	  @JsonProperty(access = JsonProperty.Access.WRITE_ONLY) private
	  List<Attachments> attachments;
	 
	
	
	public void Posts(int id,
			@Length(min = 1, message = "userName required") String userName,
			@Size(min = 1, message = "category required") String category,
			@Size(min = 1, message = "title is required") String title, List<PostsAuthors> postsAuthors, Date date, List<Attachments> attachments
			) {
		
		this.id = id;
		this.date = date;
		this.category = category;
		this.title = title;
		this.postsAuthors = postsAuthors;
		this.attachments = attachments;
	}

	
	public long getId() {
		return id;
	}

	public Date getDate() {
		return date;
	}

	public String getCategory() {
		return category;
	}

	public List<PostsAuthors> getPostsAuthors() {
		return postsAuthors;
	}

	

	public void setId(int id) {
		this.id = id;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public void setPostsAuthors(List<PostsAuthors> postsAuthors) {
		this.postsAuthors = postsAuthors;
	}

	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
}
