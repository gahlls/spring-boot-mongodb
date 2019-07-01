package com.gahlls.example.mvcmongodb.response;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class UserResponse {

	@JsonProperty("id")
	private String id;
	
	@JsonProperty("email")
	private String email;
	
	@JsonProperty("first-name")
	private String firstName;
	
	@JsonProperty("last-name")
	private String lastName;
	
	@JsonProperty("data-born")
	private LocalDate dateBorn;
	
	@JsonProperty("genre")
	private String genre;
	
	@JsonProperty("cellphone")
	private Long cellphone;
	
	@JsonProperty("role")
	private String role;
}
