package com.gahlls.example.mvcmongodb.model;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.gahlls.example.mvcmongodb.model.enums.Genre;
import com.gahlls.example.mvcmongodb.model.enums.Role;

import lombok.Data;

@Document(collection = "user")
@Data
public class User {
	
	@Id
	private String id;  
	private String email;
	private String password;
	private String firstName;
	private String lastName;
	private LocalDate dateBorn;
	private Genre genre;
	private Long cellphone;
	private Address address; 
	private Set<Integer> roles = new HashSet<>();

	public Set<Role> getRoles() {
		return roles.stream().map(x -> Role.toEnum(x)).collect(Collectors.toSet());
	}
	
	public void setRole(Role role) {
		roles.add(role.getId());
	}
}
