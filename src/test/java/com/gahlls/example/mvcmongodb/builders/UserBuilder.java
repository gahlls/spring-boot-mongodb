package com.gahlls.example.mvcmongodb.builders;

import com.gahlls.example.mvcmongodb.model.User;
import com.gahlls.example.mvcmongodb.model.enums.Genre;
import com.gahlls.example.mvcmongodb.model.enums.Role;

public class UserBuilder {

	private User user;
	private UserBuilder() {}
	
	public static UserBuilder user() {
		UserBuilder builder = new UserBuilder();
		initializeDefault(builder);
		return builder;
	}
	
	public static void initializeDefault(UserBuilder builder) {
		builder.user = new User();
		User usuario = builder.user;
		usuario.setId("abc");
		usuario.setEmail("gabriel@teste.com");
		usuario.setPassword("123");
		usuario.setFirstName("Gabriel");
		usuario.setLastName("Silva");
		usuario.setGenre(Genre.toEnum(1));
		usuario.setCellphone(new Long("11955555555"));
	}
	
	public UserBuilder id(String id) {
		user.setId(id);
		return this;
	}
	
	public UserBuilder email(String email) {
		user.setEmail(email);
		return this;
	}
	
	public UserBuilder firstName(String nome) {
		user.setFirstName(nome);
		return this;
	}
	
	public UserBuilder role(Role perfil) {
		user.setRole(perfil);
		return this;
	}
	
	public User builder() {
		return user;
	}
}
