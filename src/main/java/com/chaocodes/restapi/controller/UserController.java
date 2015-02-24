package com.chaocodes.restapi.controller;

import java.util.List;

import javax.validation.Valid;

import org.jsondoc.core.annotation.Api;
import org.jsondoc.core.annotation.ApiBodyObject;
import org.jsondoc.core.annotation.ApiMethod;
import org.jsondoc.core.annotation.ApiPathParam;
import org.jsondoc.core.annotation.ApiResponseObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.chaocodes.restapi.exception.ResourceNotFoundException;
import com.chaocodes.restapi.model.User;
import com.chaocodes.restapi.repository.UserRepository;
import com.chaocodes.restapi.util.TimeUtil;

@RestController
@RequestMapping("/user")
@Api(name = "user services", description = "User API Endpoints")
public class UserController
{
	private final UserRepository userRepository;

	@Autowired
	public UserController(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	private void checkUserExists(Long userId, User user) {
		if (user == null) {
			throw new ResourceNotFoundException("User " + userId + " could not be found.");
		}
	}

	@RequestMapping(method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(value = HttpStatus.CREATED)
	@ApiMethod(description = "Creates a new user")
	public @ApiResponseObject User createOneUser(@ApiBodyObject @RequestBody @Valid User user) {
		return userRepository.save(user);
	}

	@RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiMethod(description = "Reads all users")
	public @ApiResponseObject List<User> readAllUsers() {
		return userRepository.findAll();
	}

	@RequestMapping(value = "/{userId}", method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiMethod(description = "Reads a user")
	public @ApiResponseObject User readOneUser(@ApiPathParam(name = "userId", description="The user ID") @PathVariable Long userId) {
		User user = userRepository.findOne(userId);
		checkUserExists(userId, user);
		return user;
	}

	@RequestMapping(value = "/{userId}", method = RequestMethod.PUT,
			consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiMethod(description = "Updates a user")
	public @ApiResponseObject User updateOneUser(@ApiPathParam(name = "userId", description="The user ID") @PathVariable Long userId,
			@ApiBodyObject @RequestBody @Valid User user) {
		User update = userRepository.findOne(userId);
		checkUserExists(userId, update);
		update.setEmail(user.getEmail());
		update.setUsername(user.getUsername());
		update.setOwner(user.isOwner());
		update.setUpdatedAt(TimeUtil.getTimestampNow());
		return userRepository.save(update);
	}

	@RequestMapping(value = "/{userId}",
			method = RequestMethod.DELETE)
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	@ApiMethod(description = "Deletes a user")
	public void deleteOneUser(@ApiPathParam(name = "userId", description="The user ID") @PathVariable Long userId) {
		User delete = userRepository.findOne(userId);
		checkUserExists(userId, delete);
		userRepository.delete(delete);
	}
}
