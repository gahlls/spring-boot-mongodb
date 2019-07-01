package com.gahlls.example.mvcmongodb.builders;

import java.time.LocalDate;

import com.gahlls.example.mvcmongodb.model.enums.Genre;
import com.gahlls.example.mvcmongodb.model.enums.Role;
import com.gahlls.example.mvcmongodb.request.InsertUserRequest;

public class SaveUserRequestBuilder {

	private InsertUserRequest insertUserRequest;
	private SaveUserRequestBuilder() {}
	
	public static SaveUserRequestBuilder usuario() {
		SaveUserRequestBuilder builder = new SaveUserRequestBuilder();
		initializeDefault(builder);
		return builder;
	}
	
	public static void initializeDefault(SaveUserRequestBuilder builder) {
		builder.insertUserRequest = new InsertUserRequest();
		InsertUserRequest insertUserRequest = builder.insertUserRequest;
		insertUserRequest.setEmail("gabriel@teste.com");
		insertUserRequest.setPassword("123");
		insertUserRequest.setFirstName("Gabriel");
		insertUserRequest.setLastName("Silva");
		insertUserRequest.setDateBorn(LocalDate.now());
		insertUserRequest.setCellphone(11955555555l);
		insertUserRequest.setGenre(Genre.MASCULINO.getId());
		insertUserRequest.setRole(Role.STUDENT);
	}
	
	public SaveUserRequestBuilder email(String email) {
		insertUserRequest.setEmail(email);
		return this;
	}
	
	public SaveUserRequestBuilder firstName(String nome) {
		insertUserRequest.setFirstName(nome);
		return this; 
	}
	
	public InsertUserRequest builder() {
		return insertUserRequest;
	}
}
