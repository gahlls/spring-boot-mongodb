package com.gahlls.example.mvcmongodb.api;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import com.gahlls.example.mvcmongodb.request.InsertUserRequest;
import com.gahlls.example.mvcmongodb.request.UpdateUserRequest;
import com.gahlls.example.mvcmongodb.response.root.ResponseUserRoot;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api("student")
public interface UserResource {

	@ApiOperation(value = "Insert user")
	@ApiResponses(value = {
			@ApiResponse(code = 201, message= "Created"),
			@ApiResponse(code = 403, message= "Forbidden"),
			@ApiResponse(code = 401, message = "Unauthorized"),
			@ApiResponse(code = 404, message = "Not Found") })
	@PostMapping
	public ResponseEntity<URI> save(final InsertUserRequest saveStudentRequest);
	
	@ApiOperation(value = "Find user by id")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message= "Ok", response = ResponseUserRoot.class),
			@ApiResponse(code = 403, message= "Forbidden"),
			@ApiResponse(code = 401, message = "Unauthorized "),
			@ApiResponse(code = 404, message = "Not Found") })
	@GetMapping("/{id}")
	public ResponseEntity<ResponseUserRoot> findById(@ApiParam(value = "ID", required = true) final String id);

	@ApiOperation(value = "Update one user")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message= "Ok", response = ResponseUserRoot.class),
			@ApiResponse(code = 403, message= "Forbidden"),
			@ApiResponse(code = 401, message = "Unauthorized "),
			@ApiResponse(code = 404, message = "Not Found") })
	@PutMapping("/{id}")
	public ResponseEntity<ResponseUserRoot> update(@ApiParam(value = "ID", required = true) final String id, final UpdateUserRequest atualizaPersonalTrainerRequest);
}
