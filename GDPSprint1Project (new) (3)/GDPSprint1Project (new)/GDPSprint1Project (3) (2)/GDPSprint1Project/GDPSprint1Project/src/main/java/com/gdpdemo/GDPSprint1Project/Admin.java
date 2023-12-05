package com.gdpdemo.GDPSprint1Project;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "admin")
public class Admin {
	@Id
	@Column(nullable = false)
	@GeneratedValue(generator="adm", strategy = GenerationType.IDENTITY)
   @SequenceGenerator(name="adm")
	private int id;


	/*
	 * @Size(min = 1, message = "category required")
	 * 
	 * @Column private String category;
	 * 
	 * 
	 * @Size(min = 1, message = "title is required")
	 * 
	 * @Column private String title;
	 * 
	 * 
	 * 
	 * @OneToMany(mappedBy = "id_post", cascade = { CascadeType.MERGE,
	 * CascadeType.PERSIST })
	 * 
	 * @JsonProperty(access = JsonProperty.Access.WRITE_ONLY) private Date date;
	 */
	 @OneToOne(cascade=CascadeType.ALL)
	 @JoinColumn(name="id_post")
	Posts posts;
	
	public Admin() {
		super();
		// TODO Auto-generated constructor stub
	}


	public Admin(int id,Posts posts
			
			) {
		
		this.id = id;
		this.posts=posts;
		
		
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public Posts getPosts() {
		return posts;
	}


	public void setPosts(Posts posts) {
		this.posts = posts;
	}


}
