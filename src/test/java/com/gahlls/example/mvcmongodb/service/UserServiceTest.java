package com.gahlls.example.mvcmongodb.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.Rule;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.gahlls.example.mvcmongodb.builders.UserBuilder;
import com.gahlls.example.mvcmongodb.exception.AuthorizationException;
import com.gahlls.example.mvcmongodb.exception.BadRequestException;
import com.gahlls.example.mvcmongodb.exception.NotFoundException;
import com.gahlls.example.mvcmongodb.model.User;
import com.gahlls.example.mvcmongodb.repository.UserRepository;
import com.gahlls.example.mvcmongodb.security.UserSpringSecurity;
import com.gahlls.example.mvcmongodb.service.impl.UserServiceImpl;
import com.gahlls.example.mvcmongodb.util.AuthenticateUser;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

	@InjectMocks
	private UserServiceImpl userServiceImpl;
	
	@Mock
	private BCryptPasswordEncoder en;
	
	@Mock
	private UserRepository userRepository;
	
	@Mock
	private AuthenticateUser authenticateUser;
	
	@Rule
	public ExpectedException exception = ExpectedException.none();
	
	@Test
	@DisplayName("Test that saves user with non-existent email")
	public void insert_approach1() {
		User user = UserBuilder.user().builder();
		userServiceImpl.insert(user);
		verify(userRepository).insert(user);
	}
	
	@Test
	@DisplayName("Test that does not allow saving user with existing email")
	public void insert_approach2() {
		
		User user = UserBuilder.user().builder();
		Optional<User> userFinded = Optional.of(UserBuilder.user().builder());
		
		when(userRepository.findByEmail(any(String.class))).thenReturn(userFinded);
		
		exception.expect(BadRequestException.class);
		exception.expectMessage("There is already a user with email: " + userFinded.get().getEmail());
		userServiceImpl.insert(user);
	}
	
	@Test
	@DisplayName("Test user search by id")
	public void findById_approach1() {

		User userFinded = UserBuilder.user().builder();
		UserSpringSecurity userSpringSecurity = new UserSpringSecurity(userFinded.getId(), userFinded.getEmail(), userFinded.getPassword(), userFinded.getRoles());
		
		when(authenticateUser.authenticanted()).thenReturn(userSpringSecurity);
		when(userRepository.findById(userFinded.getId())).thenReturn(Optional.of(userFinded));
		
		userServiceImpl.findById(userFinded.getId());
		verify(userRepository).findById(userFinded.getId());
	}
	
	@Test
	@DisplayName("Test that does not find user by id")
	public void findById_approach2() {

		User userFinded = UserBuilder.user().id("id não encontrado").builder();
		UserSpringSecurity userSpringSecurity = new UserSpringSecurity(userFinded.getId(), userFinded.getEmail(), userFinded.getPassword(), userFinded.getRoles());
		
		when(authenticateUser.authenticanted()).thenReturn(userSpringSecurity);
		when(userRepository.findById(userFinded.getId())).thenThrow(new NotFoundException("User not found with id: " + userFinded.getId()));

		exception.expect(NotFoundException.class);
		exception.expectMessage("User not found with id: " + userFinded.getId());
		userServiceImpl.findById(userFinded.getId());
	}
	
	@Test
	@DisplayName("Access denied test for user with different id or another user")
	public void findById_approach3() {

		String id = "456";
		User userFinded = UserBuilder.user().id("123").builder();
		UserSpringSecurity userSpringSecurity = new UserSpringSecurity(userFinded.getId(), userFinded.getEmail(), userFinded.getPassword(), userFinded.getRoles());
		
		when(authenticateUser.authenticanted()).thenReturn(userSpringSecurity);
		
		exception.expect(AuthorizationException.class);
		exception.expectMessage("Unathorized");
		userServiceImpl.findById(id);
	}
	
	@Test
	@DisplayName("Access denied for unauthenticated user test")
	public void findById_approach4() {

		String id = "abc";
		when(authenticateUser.authenticanted()).thenReturn(null);
		exception.expect(AuthorizationException.class);
		exception.expectMessage("Unathorized");
		userServiceImpl.findById(id);
	}
	
	@Test
	@DisplayName("Test user search by email")
	public void findByEmail_approach1() {

		String email = "joao@gmail.com";
		Optional<User> userFinded = Optional.of(UserBuilder.user().email("joao@gmail.com").builder());
		
		when(userRepository.findByEmail(email)).thenReturn(userFinded);
		
		userServiceImpl.findByEmail(email);
		verify(userRepository).findByEmail(email);
	}
	
	@Test
	@DisplayName("Test that does not find email")
	public void findByEmail_approach2() {

		String email = "maria@teste.com";
		when(userRepository.findByEmail(email)).thenThrow(new NotFoundException("There is already a user with email: " + email));
		
		exception.expect(NotFoundException.class);
		exception.expectMessage("There is already a user with email: " + email);
		userServiceImpl.findByEmail(email);
	}
	
	@Test
	@DisplayName("Test that updates user")
	public void update_approach1() {

		User usuarioParaAtualizar = UserBuilder.user().firstName("Maria").builder();
		User usuarioEncontrado = UserBuilder.user().builder();
		UserSpringSecurity userSpringSecurity = new UserSpringSecurity(usuarioParaAtualizar.getId(), usuarioParaAtualizar.getEmail(), usuarioParaAtualizar.getPassword(), usuarioParaAtualizar.getRoles());
		
		when(authenticateUser.authenticanted()).thenReturn(userSpringSecurity);
		when(userRepository.findById(usuarioParaAtualizar.getId())).thenReturn(Optional.of(usuarioEncontrado));
		when(userRepository.save(usuarioParaAtualizar)).thenReturn(usuarioParaAtualizar);
		
		User usuarioAtualizado = userRepository.save(usuarioParaAtualizar);
		userServiceImpl.update(usuarioParaAtualizar.getId(), usuarioParaAtualizar);
		
		assertEquals(usuarioParaAtualizar, usuarioAtualizado);
	}
}
