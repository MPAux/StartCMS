//package com.bytecode.startcms.controller.error;
//
//import org.springframework.dao.EmptyResultDataAccessException;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.bind.annotation.RestControllerAdvice;
//import org.springframework.web.context.request.ServletWebRequest;
//
//@RestControllerAdvice(basePackages = {"com.bytecode.startcms.controller.rest"})
//public class GlobalRestErrorController {
//	@ExceptionHandler({EmptyResultDataAccessException.class})
//	public ResponseEntity<Object> getEmptyResultDataAccessException(EmptyResultDataAccessException ex, ServletWebRequest webRequest) {
//		ApiError apiError = new ApiError();
//	}
//}

//https://www.baeldung.com/global-error-handler-in-a-spring-rest-api
