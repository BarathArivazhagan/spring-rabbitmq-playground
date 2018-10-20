package com.barath.app.order.exception;

import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindException;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.context.request.async.AsyncRequestTimeoutException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.barath.app.order.error.GlobalError;
import com.barath.app.order.error.GlobalErrorResolver;





@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
	
	private static final String NUMBER_PATTERN_REGEX="\\d+";
	
	
	@ExceptionHandler({Exception.class})
	public ResponseEntity<Object> handleGlobalException(Exception ex, WebRequest request) {
		
		HttpHeaders headers = new HttpHeaders();
		
		if (ex instanceof HttpRequestMethodNotSupportedException) {
			HttpStatus status = HttpStatus.METHOD_NOT_ALLOWED;
			return handleHttpRequestMethodNotSupported((HttpRequestMethodNotSupportedException) ex, headers, status,
					request);
		}
		if (ex instanceof HttpMediaTypeNotSupportedException) {
			HttpStatus status = HttpStatus.UNSUPPORTED_MEDIA_TYPE;
			return handleHttpMediaTypeNotSupported((HttpMediaTypeNotSupportedException) ex, headers, status, request);
		}
		if (ex instanceof HttpMediaTypeNotAcceptableException) {
			HttpStatus status = HttpStatus.NOT_ACCEPTABLE;
			return handleHttpMediaTypeNotAcceptable((HttpMediaTypeNotAcceptableException) ex, headers, status, request);
		}
		if (ex instanceof MissingPathVariableException) {
			HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
			return handleMissingPathVariable((MissingPathVariableException) ex, headers, status, request);
		}
		if (ex instanceof MissingServletRequestParameterException) {
			HttpStatus status = HttpStatus.BAD_REQUEST;
			return handleMissingServletRequestParameter((MissingServletRequestParameterException) ex, headers, status,
					request);
		}
		if (ex instanceof ServletRequestBindingException) {
			HttpStatus status = HttpStatus.BAD_REQUEST;
			return handleServletRequestBindingException((ServletRequestBindingException) ex, headers, status, request);
		}
		if (ex instanceof ConversionNotSupportedException) {
			HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
			return handleConversionNotSupported((ConversionNotSupportedException) ex, headers, status, request);
		}
		if (ex instanceof TypeMismatchException) {
			HttpStatus status = HttpStatus.BAD_REQUEST;
			return handleTypeMismatch((TypeMismatchException) ex, headers, status, request);
		}
		if (ex instanceof HttpMessageNotReadableException) {
			HttpStatus status = HttpStatus.BAD_REQUEST;
			return handleHttpMessageNotReadable((HttpMessageNotReadableException) ex, headers, status, request);
		}
		if (ex instanceof HttpMessageNotWritableException) {
			HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
			return handleHttpMessageNotWritable((HttpMessageNotWritableException) ex, headers, status, request);
		}
		if (ex instanceof MethodArgumentNotValidException) {
			HttpStatus status = HttpStatus.BAD_REQUEST;
			return handleMethodArgumentNotValid((MethodArgumentNotValidException) ex, headers, status, request);
		}
		if (ex instanceof MissingServletRequestPartException) {
			HttpStatus status = HttpStatus.BAD_REQUEST;
			return handleMissingServletRequestPart((MissingServletRequestPartException) ex, headers, status, request);
		}
		if (ex instanceof BindException) {
			HttpStatus status = HttpStatus.BAD_REQUEST;
			return handleBindException((BindException) ex, headers, status, request);
		}
		if (ex instanceof NoHandlerFoundException) {
			HttpStatus status = HttpStatus.NOT_FOUND;
			return handleNoHandlerFoundException((NoHandlerFoundException) ex, headers, status, request);
		}
		if (ex instanceof AsyncRequestTimeoutException) {
			HttpStatus status = HttpStatus.SERVICE_UNAVAILABLE;
			return handleAsyncRequestTimeoutException((AsyncRequestTimeoutException) ex, headers, status, request);
		}
		if( ex instanceof APPException){
			
			APPException appEx=((APPException) ex);		
			String errorMessage=appEx.getMessage();
			GlobalError error=null;
		
			if( !StringUtils.isEmpty(errorMessage) && errorMessage.matches(NUMBER_PATTERN_REGEX)){
				int errorCode=Integer.parseInt(errorMessage) ;
				error=GlobalErrorResolver.resolveError(errorCode,ex);
			}else{
				error=GlobalErrorResolver.resolveErrorMessage(errorMessage,ex);
			}			
			
			return handleCustomExceptionInternal(error, headers, request);
		}
		if( ex instanceof OrderItemCancelledErrorException){
			
			OrderItemCancelledErrorException cancelEx=((OrderItemCancelledErrorException) ex);		
			String errorMessage=cancelEx.getMessage();
			GlobalError error=null;
		
			if( !StringUtils.isEmpty(errorMessage) && errorMessage.matches(NUMBER_PATTERN_REGEX)){
				int errorCode=Integer.parseInt(errorMessage) ;
				error=GlobalErrorResolver.resolveError(errorCode,ex);
			}else{
				error=GlobalErrorResolver.resolveErrorMessage(errorMessage,ex);
			}			
			
			return handleCustomExceptionInternal(error, headers, request);
		}
		
		if( ex instanceof OrderItemsException){
			
			OrderItemsException orderEx=((OrderItemsException) ex);		
			String errorMessage=orderEx.getMessage();
			GlobalError error=null;
		
			if( !StringUtils.isEmpty(errorMessage) && errorMessage.matches(NUMBER_PATTERN_REGEX)){
				int errorCode=Integer.parseInt(errorMessage) ;
				error=GlobalErrorResolver.resolveError(errorCode,ex);
			}else{
				error=GlobalErrorResolver.resolveErrorMessage(errorMessage,ex);
			}			
			
			return handleCustomExceptionInternal(error, headers, request);
		}

		if (this.logger.isWarnEnabled()) {
			this.logger.warn("Unknown exception type: " + ex.getClass().getName());
		}
		HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
		return handleExceptionInternal(ex, null, headers, status, request);
	}

	private ResponseEntity<Object> handleCustomExceptionInternal(GlobalError error, HttpHeaders headers,WebRequest request) {
		return new ResponseEntity<>(error, headers, error.getStatus());
	}
	
	
	

	

}
