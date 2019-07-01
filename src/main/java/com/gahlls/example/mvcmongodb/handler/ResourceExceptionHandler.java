package com.gahlls.example.mvcmongodb.handler;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.gahlls.example.mvcmongodb.exception.AuthorizationException;
import com.gahlls.example.mvcmongodb.exception.BadRequestException;
import com.gahlls.example.mvcmongodb.exception.NotFoundException;
import com.gahlls.example.mvcmongodb.exception.ObjectError;
import com.gahlls.example.mvcmongodb.response.ResponseDefault;

@ControllerAdvice
@RestController
public class ResourceExceptionHandler extends ResponseEntityExceptionHandler {
	
	@Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        List<ObjectError> errors = getErrors(ex);
        ResponseDefault exceptionResponse = new ResponseDefault(String.valueOf(HttpStatus.BAD_REQUEST.value()), status.getReasonPhrase(), String.valueOf(LocalDate.now()), 
        		"Request to have invalid fields", request.getDescription(false), errors);
        return new ResponseEntity<>(exceptionResponse, status);
    }

	private List<ObjectError> getErrors(MethodArgumentNotValidException ex) {
		 return ex.getBindingResult().getFieldErrors().stream()
	                .map(error -> new ObjectError(error.getDefaultMessage(), error.getField(), error.getRejectedValue()))
	                .collect(Collectors.toList());
	}

	@ExceptionHandler(Exception.class)
	public final ResponseEntity<ResponseDefault> handleAllExceptions(Exception ex, WebRequest request) {
		ResponseDefault exceptionResponse = new ResponseDefault(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()), HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase()
				,String.valueOf(LocalDate.now()), ex.getMessage(), request.getDescription(false), null);
		return new ResponseEntity<>(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(NotFoundException.class)
	public final ResponseEntity<ResponseDefault> handleNotFoundException(NotFoundException ex, WebRequest request) {
		ResponseDefault exceptionResponse = new ResponseDefault(String.valueOf(HttpStatus.NOT_FOUND.value()), HttpStatus.NOT_FOUND.getReasonPhrase(), 
				String.valueOf(LocalDate.now()), ex.getMessage(), request.getDescription(false), null);
		return new ResponseEntity<>(exceptionResponse, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(BadRequestException.class)
	public final ResponseEntity<ResponseDefault> handleBadRequestException(BadRequestException ex,
			WebRequest request) {
		ResponseDefault exceptionResponse = new ResponseDefault(String.valueOf(HttpStatus.BAD_REQUEST.value()), HttpStatus.BAD_REQUEST.getReasonPhrase(), 
				String.valueOf(LocalDate.now()), ex.getMessage(), request.getDescription(false), null);
		return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(AuthorizationException.class)
	public final ResponseEntity<ResponseDefault> authorization(AuthorizationException ex,
			WebRequest request) {
		ResponseDefault exceptionResponse = new ResponseDefault(String.valueOf(HttpStatus.FORBIDDEN.value()),HttpStatus.FORBIDDEN.getReasonPhrase(), 
				String.valueOf(LocalDate.now()), ex.getMessage(), request.getDescription(false), null);
		return new ResponseEntity<>(exceptionResponse, HttpStatus.FORBIDDEN);
	}
}
