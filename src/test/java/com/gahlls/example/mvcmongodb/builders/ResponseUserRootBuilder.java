package com.gahlls.example.mvcmongodb.builders;

import com.gahlls.example.mvcmongodb.model.enums.Genre;
import com.gahlls.example.mvcmongodb.response.DataUserResponse;
import com.gahlls.example.mvcmongodb.response.UserResponse;
import com.gahlls.example.mvcmongodb.response.root.ResponseUserRoot;

public class ResponseUserRootBuilder {

	private ResponseUserRoot responseUserRoot;
	private ResponseUserRootBuilder() {}
	
	public static ResponseUserRootBuilder user() {
		ResponseUserRootBuilder builder = new ResponseUserRootBuilder();
		initializeDefault(builder);
		return builder;
	}
	
	public static void initializeDefault(ResponseUserRootBuilder builder) {
		builder.responseUserRoot = new ResponseUserRoot();
		ResponseUserRoot responseUserRoot = builder.responseUserRoot;
		responseUserRoot.setDataUserResponse(new DataUserResponse());
		responseUserRoot.getDataUserResponse().setUserResponse(new UserResponse());
		responseUserRoot.getDataUserResponse().getUserResponse().setEmail("gabriel@teste.com");
		responseUserRoot.getDataUserResponse().getUserResponse().setFirstName("Gabriel");
		responseUserRoot.getDataUserResponse().getUserResponse().setLastName("Silva");
		responseUserRoot.getDataUserResponse().getUserResponse().setCellphone(119555555l);
		responseUserRoot.getDataUserResponse().getUserResponse().setGenre(Genre.MASCULINO.getDescribe());
	}
	
	public ResponseUserRootBuilder role(String role) {
		responseUserRoot.getDataUserResponse().getUserResponse().setRole(role);
		return this;
	}
	
	public ResponseUserRoot builder() {
		return responseUserRoot;
	} 
}
