package com.gahlls.example.mvcmongodb.response.mapper.uses;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.gahlls.example.mvcmongodb.model.User;
import com.gahlls.example.mvcmongodb.model.enums.Role;
import com.gahlls.example.mvcmongodb.response.UserResponse;

@Component
public class UsersResponseMap {

	public List<UserResponse> map(List<User> users) {
		
		List<UserResponse> usersResponse = new ArrayList<>();
		users.forEach(user ->{
			UserResponse userResponse = new UserResponse();
			userResponse.setId(user.getId());
			userResponse.setEmail(user.getEmail());
			userResponse.setCellphone(user.getCellphone());
			userResponse.setDateBorn(user.getDateBorn());
			userResponse.setGenre(user.getGenre().getDescribe());
			userResponse.setFirstName(user.getFirstName());
			userResponse.setLastName(user.getLastName());
			user.getRoles().forEach(role -> {
				if(role.getDescribe().equals(Role.NO_STUDENT.getDescribe()))
						userResponse.setRole(role.getDescribe());
			});
			usersResponse.add(userResponse);
		});
		return usersResponse;
	}
}
