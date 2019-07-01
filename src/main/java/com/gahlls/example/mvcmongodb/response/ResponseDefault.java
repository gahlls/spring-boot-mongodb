package com.gahlls.example.mvcmongodb.response;

import java.time.LocalDate;
import java.util.List;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.gahlls.example.mvcmongodb.exception.ObjectError;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResponseDefault {

	@JsonProperty("code")
	private String code;
	
	@JsonProperty("status")
	private String status;
	
	@JsonProperty("date")
	private String timestamp;
	
	@JsonProperty("message")
	private String message;
	
	@JsonProperty("details")
	private String details;
	
	@JsonProperty("errors")
	private List<ObjectError> errors;
	
	public ResponseDefault() {
		this.code = String.valueOf(HttpStatus.OK.value());
		this.status = HttpStatus.OK.getReasonPhrase();
		this.message = "Success";
		this.timestamp = String.valueOf(LocalDate.now());
	}
}
