package com.rest.webservices.restwebservices.user;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import com.rest.webservices.restwebservices.post.Post;
import com.rest.webservices.restwebservices.post.PostRepository;

import io.swagger.annotations.ApiOperation;

@RestController
public class UserJPAResource {
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PostRepository postRepository;
	
	@GetMapping(path = "/jpa/users")
	@ApiOperation(value = "Retrieves all users", 
		response = User.class,
		responseContainer = "List")
	public List<User> retrieveAllUsers() {		
		return userRepository.findAll();
	}
	
	@GetMapping(path = "/jpa/users/{id}")
	@ApiOperation(value = "Finds User by Id", response = User.class)
	public Resource<User> retrieveUser(@PathVariable int id) {
		Optional<User> user = userRepository.findById(id);
		if (!user.isPresent()) {
			throw new UserNotFoundException("Id: " + id);
		}
		
		// HATEOS
		ControllerLinkBuilder hateosLink =  ControllerLinkBuilder.linkTo(ControllerLinkBuilder.methodOn(this.getClass()).retrieveAllUsers());
		Resource<User> resource = new Resource<User>(user.get());
		resource.add(hateosLink.withRel("all-users"));
		return resource;
	}
	
	@PostMapping(path = "/jpa/users")
	@ApiOperation(value = "Adds a User", response = User.class)
	public ResponseEntity createUser(@Valid @RequestBody User user) {
		User savedUser = userRepository.save(user);
		URI location = ServletUriComponentsBuilder
			.fromCurrentRequest()
			.path("/{id}")
			.buildAndExpand(Integer.toString(savedUser.getId()))
			.toUri();
		return ResponseEntity.created(location).body(savedUser);
	}
	
	@DeleteMapping(path = "/jpa/users/{id}")
	@ApiOperation(value = "Deletes a User")
	public void deleteUser(@PathVariable int id) {
		userRepository.deleteById(id);
	}
	
	@GetMapping(path = "/jpa/users/{id}/posts")
	@ApiOperation(value = "Retrieves all users")
	public List<Post> retrieveAllUsers(@PathVariable int id) {		
		Optional<User> userOptional = userRepository.findById(id);
		if (!userOptional.isPresent()) {
			throw new UserNotFoundException("Id: " + id);
		}
		return userOptional.get().getPosts();
	}
	
	@PostMapping(path = "/jpa/users/{id}/posts")
	@ApiOperation(value = "Adds a blog Post to a User", response = Post.class)
	public ResponseEntity createPost(@Valid @RequestBody Post post, @PathVariable int id) {
		Optional<User> userOptional = userRepository.findById(id);
		if (!userOptional.isPresent()) {
			throw new UserNotFoundException("Id: " + id);
		}
		User user = userOptional.get();
		post.setUser(user);
		this.postRepository.save(post);
		URI location = ServletUriComponentsBuilder
			.fromCurrentRequest()
			.path("/{id}")
			.buildAndExpand(Integer.toString(post.getId()))
			.toUri();
		return ResponseEntity.created(location).build();
	}
}
