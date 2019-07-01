package com.gahlls.example.mvcmongodb.response.mapper.uses;

import java.util.Set;

import org.mapstruct.Named;
import org.springframework.stereotype.Component;

import com.gahlls.example.mvcmongodb.model.enums.Role;

@Named("RoleResponseMap")
@Component
public class RoleResponseMap {

	public String map(Set<Role> roles) {
		
		String roleReturn = "";
		for (Role role : roles) {
			if(role.getDescribe().equals(Role.STUDENT.getDescribe()))
				roleReturn = role.getDescribe();
		}
		return roleReturn;
	}
}
