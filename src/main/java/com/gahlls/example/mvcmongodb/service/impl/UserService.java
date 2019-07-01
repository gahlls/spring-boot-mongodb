package com.gahlls.example.mvcmongodb.service.impl;

import org.springframework.security.core.context.SecurityContextHolder;

import com.gahlls.example.mvcmongodb.security.UserSpringSecurity;

public class UserService {

	public static UserSpringSecurity authenticated() {
		
		try {
			return (UserSpringSecurity) SecurityContextHolder.getContext().getAuthentication().getPrincipal(); 
		} catch (Exception e) {
			return null;
		}
	}
}
