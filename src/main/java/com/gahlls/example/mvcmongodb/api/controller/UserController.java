package com.gahlls.example.mvcmongodb.api.controller;

import java.net.URI;

import javax.inject.Inject;
import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.gahlls.example.mvcmongodb.api.UserResource;
import com.gahlls.example.mvcmongodb.constants.UserConstants;
import com.gahlls.example.mvcmongodb.model.User;
import com.gahlls.example.mvcmongodb.request.InsertUserRequest;
import com.gahlls.example.mvcmongodb.request.UpdateUserRequest;
import com.gahlls.example.mvcmongodb.request.mapper.UserRequestMapper;
import com.gahlls.example.mvcmongodb.response.UserEncapsulation;
import com.gahlls.example.mvcmongodb.response.mapper.UserResponseMapper;
import com.gahlls.example.mvcmongodb.response.root.ResponseUserRoot;
import com.gahlls.example.mvcmongodb.service.UserService;

@RestController 
@RequestMapping(UserConstants.STUDENT_END_POINT)
public class UserController implements UserResource { 
	
	private UserService userService;
	private UserRequestMapper userRequestMapper;
	private UserResponseMapper userResponseMapper;
	
	@Inject
	public UserController(UserService userService, UserRequestMapper userRequestMapper,
							 UserResponseMapper userResponseMapper) {
		this.userService = userService;
		this.userRequestMapper = userRequestMapper;
		this.userResponseMapper = userResponseMapper;
	}

	@Override
	public ResponseEntity<URI> save(@RequestBody @Valid InsertUserRequest insertUserRequest) {
		User user = userRequestMapper.mapInsertUserRequest(insertUserRequest);
		User userSaved = userService.insert(user);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(userSaved.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}

	@Override
	public ResponseEntity<ResponseUserRoot> findById(@PathVariable("id") String id) {
		User user = userService.findById(id);
		ResponseUserRoot response = userResponseMapper.mapUser(new UserEncapsulation(user));
		return ResponseEntity.ok(response);
	}
	
	@Override
	public ResponseEntity<ResponseUserRoot> update(@PathVariable("id") String id, @RequestBody @Valid UpdateUserRequest updateStudentRequest) {
		User user = userRequestMapper.mapUpdateUserRequest(updateStudentRequest);
		User userUpdated = userService.update(id, user);
		ResponseUserRoot response = userResponseMapper.mapUser(new UserEncapsulation(userUpdated));
		return ResponseEntity.ok(response);
	}
}
