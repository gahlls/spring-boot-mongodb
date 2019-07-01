package com.gahlls.example.mvcmongodb.response.mapper.uses;

import org.mapstruct.Named;
import org.springframework.stereotype.Component;

import com.gahlls.example.mvcmongodb.model.enums.Genre;

@Named("GenreResponseMap")
@Component
public class GenreResponseMap {

	public String map(Genre genero) {
		return genero.getDescribe();
	}
}
