package com.gahlls.example.mvcmongodb.request.mapper.uses;

import org.mapstruct.Named;
import org.springframework.stereotype.Component;

import com.gahlls.example.mvcmongodb.model.enums.Genre;

@Named("GenreRequestMapper")
@Component
public class GenreRequestMap {

	public Genre map(Integer genre) {	
		return Genre.toEnum(genre);
	}
}
