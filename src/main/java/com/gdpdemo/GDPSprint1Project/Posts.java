package com.gdpdemo.GDPSprint1Project;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.Length;
import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Table(name = "posts")
public class Posts {

	@Id
	@Column(nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Length(min = 1, message = "Content of the Post should be start at least one character")
	@Column
	@Lob
	private String post_content;
	
	@Column
	private String content;

	@Size(min = 1, message = "category required")
	@Column
	private String category;

	@Size(min = 1, message = "title is required")
	@Column
	private String title;

	@OneToMany(mappedBy = "id_post", cascade = { CascadeType.MERGE })
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private List<PostsAuthors> postsAuthors;

	@Lob
	@Column(name = "Image", length = Integer.MAX_VALUE, nullable = true)
	private byte[] attachments;

	@OneToMany(mappedBy = "posts", cascade = { CascadeType.MERGE, CascadeType.PERSIST })
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private List<Comments> comments;

	public Posts(int id,
			@Length(min = 1, message = "Content of the Post should be start at least one character") String post_content,
			@Size(min = 1, message = "category required") String category,
			@Size(min = 1, message = "title is required") String title, List<PostsAuthors> postsAuthors,
			byte[] attachments,String content) {
		super();
		this.id = id;
		this.post_content = post_content;
		this.category = category;
		this.title = title;
		this.postsAuthors = postsAuthors;
		this.attachments = attachments;
		this.content = content;

	}

	/*
	 * public String getContent() { return content; }
	 */

	public Posts() {
		super();
	}

	public int getId() {
		return id;
	}

	public String getPost_content() {
		return post_content;
	}

	public String getCategory() {
		return category;
	}

	public byte[] getAttachments() {
		return attachments;
	}

	public void setAttachments(byte[] attachments) {
		this.attachments = attachments;
	}

	public List<PostsAuthors> getPostsAuthors() {
		return postsAuthors;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setPost_content(String post_content) {
		this.post_content = post_content;
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

	public List<Comments> getComments() {
		return comments;
	}

	public void setComments(List<Comments> comments) {
		this.comments = comments;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	
}
