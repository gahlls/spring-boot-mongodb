package com.gahlls.example.mvcmongodb.service.impl;

import java.util.Optional;

import javax.inject.Inject;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.gahlls.example.mvcmongodb.exception.AuthorizationException;
import com.gahlls.example.mvcmongodb.exception.BadRequestException;
import com.gahlls.example.mvcmongodb.exception.NotFoundException;
import com.gahlls.example.mvcmongodb.model.User;
import com.gahlls.example.mvcmongodb.model.enums.Role;
import com.gahlls.example.mvcmongodb.repository.UserRepository;
import com.gahlls.example.mvcmongodb.security.UserSpringSecurity;
import com.gahlls.example.mvcmongodb.service.UserService;
import com.gahlls.example.mvcmongodb.util.AuthenticateUser;

@Service
public class UserServiceImpl implements UserService {
	
	private BCryptPasswordEncoder en;
	private UserRepository userRepository;
	private AuthenticateUser authenticateUser;
	
	@Inject
	public UserServiceImpl(UserRepository studentRepository, BCryptPasswordEncoder en, AuthenticateUser authenticateUser) {
		this.userRepository = studentRepository;
		this.en = en;
		this.authenticateUser = authenticateUser;
	}

	@Override
	public User insert(User user) {
		Optional<User> userCurrent = userRepository.findByEmail(user.getEmail());
		if(userCurrent.isPresent()) 
			throw new BadRequestException("There is already a user with email: " + userCurrent.get().getEmail());
		
		user.setPassword(en.encode(user.getPassword()));
		return userRepository.insert(user);
	}

	@Override
	public User findById(String id) {
		UserSpringSecurity user = authenticateUser.authenticanted();
		if (user==null || !user.hasRole(Role.ADMIN) && !id.equals(user.getId())) 
			throw new AuthorizationException("Unathorized");
		
		return userRepository.findById(id).orElseThrow(() -> new NotFoundException("User not found with id: " + id));
	}
	
	@Override
	public User findByEmail(String email) {
		return userRepository.findByEmail(email).orElseThrow(() -> new NotFoundException("User not found with email: " + email));
	}
	
	@Override
	public User update(String id, User user) {
		User userCurrent = this.findById(id);
		user.setPassword(en.encode(user.getPassword()));
		user.setEmail(userCurrent.getEmail());
		user.setId(userCurrent.getId());
		return userRepository.save(user);
	}
}
