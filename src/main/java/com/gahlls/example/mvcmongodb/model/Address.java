package com.gahlls.example.mvcmongodb.model;

import lombok.Data;

@Data
public class Address {
	
	private String status;
	private String code;
	private String state;
	private String city;
	private String district;
	private String address;
}
