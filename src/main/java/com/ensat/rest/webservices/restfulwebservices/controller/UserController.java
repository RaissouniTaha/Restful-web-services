package com.ensat.rest.webservices.restfulwebservices.controller;

import java.net.URI;
import java.nio.file.attribute.UserPrincipalNotFoundException;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponents;

import com.ensat.rest.webservices.restfulwebservices.dao.UserServiceDao;
import com.ensat.rest.webservices.restfulwebservices.entity.Post;
import com.ensat.rest.webservices.restfulwebservices.entity.User;
import com.ensat.rest.webservices.restfulwebservices.exception.InvalidUserDataException;
import com.ensat.rest.webservices.restfulwebservices.exception.NoUsersException;
import com.ensat.rest.webservices.restfulwebservices.exception.UserNotFoundException;

@RestController
public class UserController {

	@Autowired
	private UserServiceDao userServiceDao;
	//GET /users
	//retrieveAllUsers
	@GetMapping(path = "/users")
	public List<User> retrieveAllUsers(){
		 List<User> users = userServiceDao.findAll();
		 if(users.size()==0) {
			 throw new NoUsersException("you don't have users");
		 }
		 return users;
	}
	
	//GET /users/{id}
	//retrieveUser(int id)
	@GetMapping(path = "/users/{id}")
	public EntityModel<User> retrieveUser(
			@PathVariable int id) {
		
		User user = userServiceDao.findOne(id);
		if(user==null) {
			throw new UserNotFoundException("id-"+id);
		}
		//"all-users", SERVER_PATH + "/users"
		//retrieveAllUsers
		
		EntityModel<User> resource = EntityModel.of(user);
		
		WebMvcLinkBuilder linkTo = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).retrieveAllUsers());
		
		resource.add(linkTo.withRel("all-users"));
		
		return resource;
	}
	@PostMapping(path = "/users")
	public ResponseEntity<Object> saveUser(@Valid @RequestBody User user) {
		
		if(user.getBirthDath()==null || user.getName()==null) {
			throw new InvalidUserDataException("Invalid data");
		}
		
		User savedUser = userServiceDao.save(user);
		 URI location = ServletUriComponentsBuilder.
		fromCurrentRequest().
		path("/{id}").
		buildAndExpand(savedUser.getId()).toUri();
		
		return ResponseEntity.created(location).build();
	}
	
	@DeleteMapping(path = "/users/{id}")
	public void deleteUser(
			@PathVariable(name = "id") int id) {
		
		User user = userServiceDao.deleteById(id);
		if(user==null) {
			throw new UserNotFoundException("id-"+id);
		}
	}
}
