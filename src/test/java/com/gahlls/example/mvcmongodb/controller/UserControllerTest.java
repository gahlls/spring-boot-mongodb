package com.gahlls.example.mvcmongodb.controller;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.rules.ErrorCollector;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gahlls.example.mvcmongodb.api.controller.UserController;
import com.gahlls.example.mvcmongodb.builders.ResponseUserRootBuilder;
import com.gahlls.example.mvcmongodb.builders.SaveUserRequestBuilder;
import com.gahlls.example.mvcmongodb.builders.UserBuilder;
import com.gahlls.example.mvcmongodb.constants.UserConstants;
import com.gahlls.example.mvcmongodb.exception.AuthorizationException;
import com.gahlls.example.mvcmongodb.exception.BadRequestException;
import com.gahlls.example.mvcmongodb.exception.NotFoundException;
import com.gahlls.example.mvcmongodb.model.User;
import com.gahlls.example.mvcmongodb.request.InsertUserRequest;
import com.gahlls.example.mvcmongodb.request.mapper.UserRequestMapper;
import com.gahlls.example.mvcmongodb.response.UserEncapsulation;
import com.gahlls.example.mvcmongodb.response.mapper.UserResponseMapper;
import com.gahlls.example.mvcmongodb.response.root.ResponseUserRoot;
import com.gahlls.example.mvcmongodb.service.UserService;

@RunWith(SpringJUnit4ClassRunner.class)
@AutoConfigureMockMvc
public class UserControllerTest {
	
	private MockMvc mockMvc;
	
	@InjectMocks
	private UserController userController;
	
	@Mock
	private UserService userService;
	
	@Mock
	private UserRequestMapper userRequestMapper;
	
	@Mock
	private UserResponseMapper userResponseMapper;
	
	@Rule
	public ErrorCollector error = new ErrorCollector();
	
	@Rule
	public ExpectedException exception = ExpectedException.none();
	
	private User user;
	private InsertUserRequest insertUserRequest;
	private ResponseUserRoot responseUserRoot;
	private ObjectMapper mapper;
	
	@Before
	public void setUp() {
		this.user = UserBuilder.user().builder();
		this.insertUserRequest = SaveUserRequestBuilder.usuario().builder();
		this.responseUserRoot = ResponseUserRootBuilder.user().builder();
		this.mapper = new ObjectMapper();
		this.mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
	}
	
	@Test
	@DisplayName("Test that saves user with non-existent email")
	public void insert_approach1() throws Exception {
		
		when(userRequestMapper.mapInsertUserRequest(any(InsertUserRequest.class))).thenReturn(this.user);
		when(userService.insert(any(User.class))).thenReturn(this.user);
		
		String userJson = this.mapper.writeValueAsString(this.insertUserRequest);
		
		MvcResult result = mockMvc.perform(post(UserConstants.STUDENT_END_POINT)
				.accept(MediaType.APPLICATION_JSON)
				.content(userJson)
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated()).andReturn();
		
		assertEquals(HttpStatus.CREATED.value(), result.getResponse().getStatus());
		assertEquals("http://localhost".concat(UserConstants.STUDENT_END_POINT+"/"+this.user.getId()), result.getResponse().getHeader(HttpHeaders.LOCATION));
	}
	
	@Test
	@DisplayName("Test that does not allow saving user with existing email")
	public void insert_approach2() throws Exception {
		 
		this.user.setEmail("maria@teste.com");
		
		when(userRequestMapper.mapInsertUserRequest(any(InsertUserRequest.class))).thenReturn(this.user);
		when(userService.insert(any(User.class))).thenThrow(new BadRequestException("There is already a user with email: " + user.getEmail()));
		
		String userJson = this.mapper.writeValueAsString(this.insertUserRequest);
	
		MvcResult result = mockMvc.perform(post(UserConstants.STUDENT_END_POINT)
				.accept(MediaType.APPLICATION_JSON)
				.content(userJson)
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest()).andReturn();
		
		error.checkThat(HttpStatus.BAD_REQUEST.value(), is(equalTo(result.getResponse().getStatus())));
		error.checkThat("There is already a user with email: " + user.getEmail(), is(equalTo(result.getResolvedException().getMessage())));
	}
	
	@Test
	@DisplayName("Test validating required fields")
	public void insert_approach3() throws Exception {
		
		this.insertUserRequest.setFirstName(null);
		
		when(userRequestMapper.mapInsertUserRequest(any(InsertUserRequest.class))).thenReturn(this.user);
		when(userService.insert(any(User.class))).thenReturn(this.user);
		
		String userJson = this.mapper.writeValueAsString(insertUserRequest);
		
		mockMvc.perform(post(UserConstants.STUDENT_END_POINT)
				.accept(MediaType.APPLICATION_JSON)
				.content(userJson)
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest());
		
		verifyZeroInteractions(userService);
	}
	
	@Test
	@DisplayName("Test user search by id")
	public void findById_approach1() throws Exception {
		
		when(userService.findById(any(String.class))).thenReturn(this.user);
		when(userResponseMapper.mapUser(new UserEncapsulation(this.user))).thenReturn(this.responseUserRoot);
		
		String userJson = this.mapper.writeValueAsString(this.responseUserRoot);
		 
		mockMvc.perform(get(UserConstants.STUDENT_END_POINT.concat("/"+any(String.class)))
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content().json(userJson));
	}
	
	@Test
	@DisplayName("Test that does not find user by id")
	public void findById_approach2() throws Exception {
		
		when(userService.findById(any(String.class))).thenThrow(new NotFoundException("User not found with id: 123"));
		
		MvcResult result = mockMvc.perform(get(UserConstants.STUDENT_END_POINT.concat("/"+any(String.class)))
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound())
				.andReturn();
		
		error.checkThat(HttpStatus.NOT_FOUND.value(), is(equalTo(result.getResponse().getStatus())));
		error.checkThat("User not found with id: 123", is(equalTo(result.getResolvedException().getMessage())));
	}
	
	@Test
	@DisplayName("Access denied access check test")
	public void findById_approach3() throws Exception {
		
		when(userService.findById(any(String.class))).thenThrow(new AuthorizationException("Unathorized"));
		
		mockMvc.perform(get(UserConstants.STUDENT_END_POINT.concat("/"+any(String.class)))
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isForbidden());
	}
}
