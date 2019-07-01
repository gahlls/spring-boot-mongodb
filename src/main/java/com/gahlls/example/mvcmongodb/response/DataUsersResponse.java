package com.gahlls.example.mvcmongodb.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DataUsersResponse{
	
	@JsonProperty("users")
	private List<UserResponse> usersResponse;
}
