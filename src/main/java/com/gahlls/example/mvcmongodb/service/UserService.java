package com.gahlls.example.mvcmongodb.service;

import org.springframework.security.access.prepost.PreAuthorize;

import com.gahlls.example.mvcmongodb.model.User;

public interface UserService {

	User insert(final User user);
	
	@PreAuthorize("hasRole('ROLE_STUDENT') or hasRole('ROLE_NO_STUDENT')")
	User findById(final String id);
	
	User findByEmail(final String email);

	@PreAuthorize("hasRole('ROLE_STUDENT')")
	User update(final String id, final User user);
}
