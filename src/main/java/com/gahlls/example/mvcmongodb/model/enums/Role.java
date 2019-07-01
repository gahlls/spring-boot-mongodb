package com.gahlls.example.mvcmongodb.model.enums;

import static java.util.Objects.nonNull;

public enum Role {

	ADMIN(1, "ROLE_ADMIN"),
	NO_STUDENT(2, "ROLE_NO_STUDENT"),
	STUDENT(3, "ROLE_STUDENT");
	
	private int id;
	private String describe;
	
	private Role(int id, String describe) {
		this.id = id;
		this.describe = describe;
	}
	
	public int getId() {
		return id;
	}

	public String getDescribe() {
		return describe;
	}
	
	public static Role toEnum(Integer id) {
		
		if(nonNull(id)) {
			
			for(Role x : Role.values()) {
				if(id.equals(x.getId())) {
					return x;
				}
			}
		}
		
		throw new IllegalArgumentException("Invalid id" + id);
	}
}
