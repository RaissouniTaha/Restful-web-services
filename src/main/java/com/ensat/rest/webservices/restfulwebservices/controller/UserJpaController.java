package com.ensat.rest.webservices.restfulwebservices.controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;

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
import com.ensat.rest.webservices.restfulwebservices.entity.Post;
import com.ensat.rest.webservices.restfulwebservices.entity.User;
import com.ensat.rest.webservices.restfulwebservices.exception.InvalidUserDataException;
import com.ensat.rest.webservices.restfulwebservices.exception.NoUsersException;
import com.ensat.rest.webservices.restfulwebservices.exception.UserNotFoundException;
import com.ensat.rest.webservices.restfulwebservices.repository.PostRepository;
import com.ensat.rest.webservices.restfulwebservices.repository.UserRepository;

@RestController
public class UserJpaController {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private PostRepository postRepository;
	//GET /users
	//retrieveAllUsers
	@GetMapping(path = "/jpa/users")
	public List<User> retrieveAllUsers(){
		 List<User> users = userRepository.findAll();
		 if(users.size()==0) {
			 throw new NoUsersException("you don't have users");
		 }
		 return users;
	}
	
	//GET /users/{id}
	//retrieveUser(int id)
	@GetMapping(path = "/jpa/users/{id}")
	public EntityModel<User> retrieveUser(
			@PathVariable int id) {
		
		Optional<User> user = userRepository.findById(id);
		if(!user.isPresent()) {
			throw new UserNotFoundException("id-"+id);
		}
		//"all-users", SERVER_PATH + "/users"
		//retrieveAllUsers
		
		EntityModel<User> resource = EntityModel.of(user.get());
		
		WebMvcLinkBuilder linkTo = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).retrieveAllUsers());
		
		resource.add(linkTo.withRel("all-users"));
		
		return resource;
	}
	@PostMapping(path = "/jpa/users")
	public ResponseEntity<Object> saveUser(@Valid @RequestBody User user) {
		
		if(user.getBirthDath()==null || user.getName()==null) {
			throw new InvalidUserDataException("Invalid data");
		}
		
		User savedUser = userRepository.save(user);
		 URI location = ServletUriComponentsBuilder.
		fromCurrentRequest().
		path("/{id}").
		buildAndExpand(savedUser.getId()).toUri();
		
		return ResponseEntity.created(location).build();
	}
	
	@DeleteMapping(path = "/jpa/users/{id}")
	public void deleteUser(
			@PathVariable(name = "id") int id) {

		userRepository.deleteById(id);
	}
	
	@GetMapping(path = "/jpa/users/{id}/posts")
	public List<Post> retrieveAllPostForUser(@PathVariable(name ="id") int id){
		 Optional<User> user = userRepository.findById(id);
		 if(!user.isPresent()) {
			 throw new UserNotFoundException("id-"+id);
		 }
		 return user.get().getPosts();
	}
	@PostMapping("/jpa/users/{id}/posts")
	public ResponseEntity<Object> createPost(@PathVariable int id, @RequestBody Post post) {
		Optional<User> userOptional = userRepository.findById(id);
		 if(!userOptional.isPresent()) {
			 throw new UserNotFoundException("id-"+id);
		 }
		User  user =   userOptional.get();
		post.setUser(user);
		 postRepository.save(post);
		 URI location = ServletUriComponentsBuilder.
		fromCurrentRequest().                                                                              
		path("/{id}").
		buildAndExpand(post.getId()).toUri();
		
		return ResponseEntity.created(location).build();
	}
}
