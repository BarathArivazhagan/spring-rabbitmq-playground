package com.barath.app.order.error;

import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(ignoreUnknownFields=true,prefix="amc")
public class GlobalErrorCodes {
	
	private Map<Integer,String> errors=null;

	public Map<Integer, String> getErrors() {
		return errors;
	}

	public void setErrors(Map<Integer, String> errors) {
		this.errors = errors;
	}
	
	
	
	
	

}
