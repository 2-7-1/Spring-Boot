package com.rest.webservices.restwebservices.post;

import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Fetch;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.rest.webservices.restwebservices.user.User;

@Entity
public class Post {
	
	public Post() {
		this.uuid = UUID.randomUUID().toString();
		this.likes = 0;
	}
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Size(min = 2, message = "Post should be at least 2 characters.")
	private String description;
	
	private Integer likes;
	private String uuid;
	
	@ManyToOne(fetch = FetchType.LAZY) 	// This creates the DB relationship many(posts)-to-one(user). fetch = LAZY means the user won't be retrieved from the DB unless this field is explicitly accessed in the code.
	@JsonIgnore							// This is so that the JSON return won't contain a circular reference
	private User user;					
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}	
	public Integer getLikes() {
		return likes;
	}
	public void setLikes(Integer likes) {
		this.likes = likes;
	}
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	@Override
	public String toString() {
		return "Post [id=" + id + ", description=" + description + "]";
	}

	
}
