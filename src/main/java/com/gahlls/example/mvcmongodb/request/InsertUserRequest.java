package com.gahlls.example.mvcmongodb.request;

import java.time.LocalDate;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.gahlls.example.mvcmongodb.model.enums.Role;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class InsertUserRequest {

	@ApiModelProperty(notes = "Email", required = true, dataType = "String")
	@NotNull(message = "{required.parameter}")
	@NotBlank(message = "{required.parameter}")
	private String email;
	
	@ApiModelProperty(notes = "Password", required = true, dataType = "String")
	@NotNull(message = "{required.parameter}")
	@NotBlank(message = "{required.parameter}")
	private String password;
	
	@ApiModelProperty(notes = "First name", required = true, dataType = "String")
	@NotNull(message = "{required.parameter}")
	@NotBlank(message = "{required.parameter}")
	private String firstName;
	
	@ApiModelProperty(notes = "Last name", required = true, dataType = "String")
	@NotNull(message = "{required.parameter}")
	@NotBlank(message = "{required.parameter}")
	private String lastName;

	@ApiModelProperty(notes = "Date born", required = true)
	@JsonFormat(pattern = "dd/MM/yyyy")
	@NotNull(message = "{required.parameter}")
	@JsonDeserialize(using = LocalDateDeserializer.class)  
	@JsonSerialize(using = LocalDateSerializer.class)  
	private LocalDate dateBorn;
	
	@ApiModelProperty(notes = "Genre", required = true, dataType = "Integer")
	@NotNull(message = "{required.parameter}")
	private Integer genre;
	
	@ApiModelProperty(notes = "Cellphone", required = true, dataType = "Long")
	@NotNull(message = "{required.parameter}")
	private Long cellphone;
	
	@ApiModelProperty(notes = "Role", required = true)
	@NotNull(message = "{required.parameter}")
	private Role role;
	
	@ApiModelProperty(notes = "Status", required = true)
	private String status;
	
	@ApiModelProperty(notes = "Code", required = true)
	private String code;
	
	@ApiModelProperty(notes = "State", required = true)
	private String state;
	
	@ApiModelProperty(notes = "City", required = true)
	private String city;
	
	@ApiModelProperty(notes = "District", required = true)
	private String district;
	
	@ApiModelProperty(notes = "Address", required = true)
	private String address;
}
