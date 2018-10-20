package com.barath.app.order.error;

import java.util.List;

import org.springframework.http.HttpStatus;

public class GlobalError {
	
	private int errorCode;
	
	private String errorMessage;
	
	private HttpStatus status;
	 
	private List<String> errors;

	public int getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public HttpStatus getStatus() {
		return status;
	}

	public void setStatus(HttpStatus status) {
		this.status = status;
	}

	public List<String> getErrors() {
		return errors;
	}

	public void setErrors(List<String> errors) {
		this.errors = errors;
	}

	public GlobalError(int errorCode, String errorMessage, HttpStatus status, List<String> errors) {
		super();
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
		this.status = status;
		this.errors = errors;
	}

	public GlobalError() {
		super();
		
	}

	@Override
	public String toString() {
		return "AMCError [errorCode=" + errorCode + ", errorMessage=" + errorMessage + ", status=" + status
				+ ", errors=" + errors + "]";
	}
	 
	
	
	
	
	

}
