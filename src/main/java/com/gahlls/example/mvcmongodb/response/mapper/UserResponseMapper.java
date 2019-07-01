package com.gahlls.example.mvcmongodb.response.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import com.gahlls.example.mvcmongodb.response.UserEncapsulation;
import com.gahlls.example.mvcmongodb.response.mapper.uses.GenreResponseMap;
import com.gahlls.example.mvcmongodb.response.mapper.uses.RoleResponseMap;
import com.gahlls.example.mvcmongodb.response.mapper.uses.UsersResponseMap;
import com.gahlls.example.mvcmongodb.response.root.ResponseUserRoot;
import com.gahlls.example.mvcmongodb.response.root.ResponseUsersRoot;

@Mapper(componentModel = "spring", uses = {GenreResponseMap.class, RoleResponseMap.class, UsersResponseMap.class})
public interface UserResponseMapper {

	@Mappings({
		@Mapping(target="dataUserResponse.userResponse.id", source="user.id"),
		@Mapping(target="dataUserResponse.userResponse.email", source="user.email"),
		@Mapping(target="dataUserResponse.userResponse.firstName", source="user.firstName"),
		@Mapping(target="dataUserResponse.userResponse.lastName", source="user.lastName"),
		@Mapping(target="dataUserResponse.userResponse.cellphone", source="user.cellphone"),
		@Mapping(target="dataUserResponse.userResponse.dateBorn", source="user.dateBorn"),
		@Mapping(target="dataUserResponse.userResponse.genre", source="user.genre", qualifiedByName= "GenreResponseMap"),
		@Mapping(target="dataUserResponse.userResponse.role", source="user.roles", qualifiedByName= "RoleResponseMap")
    })
	ResponseUserRoot mapUser(final UserEncapsulation user);
	
	@Mappings({
		@Mapping(target="dataUsersResponse.usersResponse", source="users")
    })
	ResponseUsersRoot mapUsers(final UserEncapsulation user);
}
