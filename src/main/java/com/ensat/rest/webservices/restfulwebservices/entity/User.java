package com.ensat.rest.webservices.restfulwebservices.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
@ApiModel(description = "All details about the users.")
@Entity
public class User {

	@Id
	@GeneratedValue
	private Integer id;
	
	@Size(min = 3,message = "Name should have at least 2 characteres ")
	@ApiModelProperty(notes = "Name atleat have 2 characteres")
	private String name;
	@Past
	@ApiModelProperty(notes = "Birth date should be in the past")
	private Date birthDath;
	@OneToMany(mappedBy = "user")
	private List<Post> posts;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Date getBirthDath() {
		return birthDath;
	}
	public void setBirthDath(Date birthDath) {
		this.birthDath = birthDath;
	}
	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", birthDath=" + birthDath + "]";
	}
	public User(Integer id, String name, Date birthDath) {
		super();
		this.id = id;
		this.name = name;
		this.birthDath = birthDath;
	}
	public User() {
		super();
		// TODO Auto-generated constructor stub
	}
	public List<Post> getPosts() {
		return posts;
	}
	public void setPosts(List<Post> posts) {
		this.posts = posts;
	}
	
	
}
