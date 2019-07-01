package com.gahlls.example.mvcmongodb.response.root;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.gahlls.example.mvcmongodb.response.DataUserResponse;
import com.gahlls.example.mvcmongodb.response.ResponseDefault;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper=false)
@AllArgsConstructor
@NoArgsConstructor
public class ResponseUserRoot extends ResponseDefault{

	@JsonProperty("data")
	private DataUserResponse dataUserResponse;
}
