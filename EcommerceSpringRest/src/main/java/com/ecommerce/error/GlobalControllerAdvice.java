package com.ecommerce.error;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.ecommerce.error.exceptions.NewUserWithDifferentPasswordsException;

@RestControllerAdvice
public class GlobalControllerAdvice extends ResponseEntityExceptionHandler{

	@ExceptionHandler(NewUserWithDifferentPasswordsException.class)
	public ResponseEntity<ApiError> handleNewUserError(Exception ex){
		return buildErrorResponseEntity(HttpStatus.BAD_REQUEST, ex.getMessage());
	}
	

	@Override
	protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers,
			HttpStatusCode statusCode, WebRequest request) {
		ApiError apiError = new ApiError(statusCode ,ex.getMessage());
		return ResponseEntity.status(statusCode).headers(headers).body(apiError);
	}
	
	private ResponseEntity<ApiError> buildErrorResponseEntity(HttpStatusCode statusCode, String message) {
		return ResponseEntity.status(statusCode)
				.body(ApiError.builder()
						.statusCode(statusCode)
						.message(message)
						.build());
	}

}