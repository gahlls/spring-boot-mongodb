package com.gahlls.example.mvcmongodb.request.mapper;


import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import com.gahlls.example.mvcmongodb.model.User;
import com.gahlls.example.mvcmongodb.request.InsertUserRequest;
import com.gahlls.example.mvcmongodb.request.UpdateUserRequest;
import com.gahlls.example.mvcmongodb.request.mapper.uses.GenreRequestMap;

@Mapper(componentModel = "spring", uses = {GenreRequestMap.class})
public interface UserRequestMapper {

	@Mappings({
		@Mapping(target="email", source="email"),
		@Mapping(target="password", source="password"),
		@Mapping(target="firstName", source="firstName"),
		@Mapping(target="lastName", source="lastName"),
		@Mapping(target="cellphone", source="cellphone"),
		@Mapping(target="dateBorn", source="dateBorn"),
		@Mapping(target="genre", source="genre", qualifiedByName="GenreRequestMapper"),
		@Mapping(target="role", source="role"),
		@Mapping(target="address.address", source="address"),
		@Mapping(target="address.city", source="city"),
		@Mapping(target="address.code", source="code"),
		@Mapping(target="address.district", source="district"),
		@Mapping(target="address.state", source="state"),
		@Mapping(target="address.status", source="status")
    })
	User mapInsertUserRequest(final InsertUserRequest insertUserRequest);
	
	@Mappings({
		@Mapping(target="password", source="password"),
		@Mapping(target="firstName", source="firstName"),
		@Mapping(target="lastName", source="lastName"),
		@Mapping(target="cellphone", source="cellphone"),
		@Mapping(target="dateBorn", source="dateBorn"),
		@Mapping(target="genre", source="genre", qualifiedByName="GenreRequestMapper"),
		@Mapping(target="address.address", source="address"),
		@Mapping(target="address.city", source="city"),
		@Mapping(target="address.code", source="code"),
		@Mapping(target="address.district", source="district"),
		@Mapping(target="address.state", source="state"),
		@Mapping(target="address.status", source="status")
    })
	User mapUpdateUserRequest(final UpdateUserRequest updateUserRequest);
}
