package com.ecommerce.error;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.web.context.request.WebRequest;

public class ApiErrorAttributes extends DefaultErrorAttributes{

	@Override
	public Map<String, Object> getErrorAttributes(WebRequest webRequest, ErrorAttributeOptions options) {
		Map<String, Object> allErrorAttributes = super.getErrorAttributes(webRequest, options);
		
		Map<String, Object> errorAttributes = new HashMap<>();
		int statusCode = (int) allErrorAttributes.get("status");
		errorAttributes.put("status", HttpStatus.valueOf(statusCode));
		errorAttributes.put("date" , LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm:ss")));
		return errorAttributes;
	}
}
