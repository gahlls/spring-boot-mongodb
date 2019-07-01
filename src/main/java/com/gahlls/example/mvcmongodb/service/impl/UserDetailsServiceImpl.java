package com.gahlls.example.mvcmongodb.service.impl;

import static java.util.Objects.isNull;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.gahlls.example.mvcmongodb.model.User;
import com.gahlls.example.mvcmongodb.repository.UserRepository;
import com.gahlls.example.mvcmongodb.security.UserSpringSecurity;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		
		Optional<User> user = userRepository.findByEmail(email);
		
		if(isNull(user)) 
			throw new UsernameNotFoundException(email);
		
		return new UserSpringSecurity(user.get().getId(), user.get().getEmail(), user.get().getPassword(), user.get().getRoles());
	}
}
