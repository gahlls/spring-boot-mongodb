package com.gahlls.example.mvcmongodb.util;

import org.springframework.stereotype.Component;

import com.gahlls.example.mvcmongodb.security.UserSpringSecurity;
import com.gahlls.example.mvcmongodb.service.impl.UserService;

@Component
public class AuthenticateUser {

	public UserSpringSecurity authenticanted() {
		return UserService.authenticated();
	}
}
