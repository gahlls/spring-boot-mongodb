package com.gahlls.example.mvcmongodb.response;

import java.util.List;

import com.gahlls.example.mvcmongodb.model.User;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserEncapsulation {

	List<User> users;
	User user;
	
	public UserEncapsulation(List<User> users){
		this.users = users;
	}
	
	public UserEncapsulation(User user){
		this.user = user;
	}
}
