package com.rest.webservices.restwebservices.user;

import java.net.URI;
import java.util.List;
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

import io.swagger.annotations.ApiOperation;

@RestController
public class UserResource {
	
	@Autowired
	private UserDALService userDALService;
	
	@GetMapping(path = "/users")
	@ApiOperation(value = "Retrieves all users", 
		response = User.class,
		responseContainer = "List")
	public List<User> retrieveAllUsers() {		
		return userDALService.findAll();
	}
	
	@GetMapping(path = "/users/{id}")
	@ApiOperation(value = "Finds User by Id", response = User.class)
	public Resource<User> retrieveUser(@PathVariable int id) {
		User user = userDALService.findOne(id);
		if (user == null) {
			throw new UserNotFoundException("Id: " + id);
		}
		
		// HATEOS
		ControllerLinkBuilder hateosLink =  ControllerLinkBuilder.linkTo(ControllerLinkBuilder.methodOn(this.getClass()).retrieveAllUsers());
		Resource<User> resource = new Resource<User>(user);
		resource.add(hateosLink.withRel("all-users"));
		return resource;
	}
	
	@PostMapping(path = "/users")
	@ApiOperation(value = "Adds a User", response = User.class)
	public ResponseEntity createUser(@Valid @RequestBody User user) {
		User savedUser = userDALService.save(user);
		URI location = ServletUriComponentsBuilder
			.fromCurrentRequest()
			.path("/{id}")
			.buildAndExpand(Integer.toString(savedUser.getId()))
			.toUri();
		return ResponseEntity.created(location).body(savedUser);
	}
	
	@DeleteMapping(path = "/users/{id}")
	@ApiOperation(value = "Deletes a User")
	public void deleteUser(@PathVariable int id) {
		User deletedUser = userDALService.deleteOne(id);
		if (deletedUser == null) {
			throw new UserNotFoundException("Id: " + id);
		}
	}
}
