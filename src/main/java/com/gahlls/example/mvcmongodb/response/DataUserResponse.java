package com.gahlls.example.mvcmongodb.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DataUserResponse {

	@JsonProperty("user")
	private UserResponse userResponse;
}
