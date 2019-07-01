package com.gahlls.example.mvcmongodb.model.enums;

import static java.util.Objects.nonNull;

public enum Genre {

	MASCULINO(1, "Male"), 
	FEMININO(2, "Female"), 
	OUTRO(3, "Others"); 
	
	private int id;
	private String describe;
	
	private Genre(int id, String describe) {
		this.id = id;
		this.describe = describe;
	}
	
	public int getId() {
		return id;
	}

	public String getDescribe() {
		return describe;
	}
	
	public static Genre toEnum(Integer id) {
		
		if(nonNull(id)) {
			
			for(Genre x : Genre.values()) {
				if(id.equals(x.getId())) {
					return x;
				}
			}
		}
		
		throw new IllegalArgumentException("Invalid id" + id);
	}
}
