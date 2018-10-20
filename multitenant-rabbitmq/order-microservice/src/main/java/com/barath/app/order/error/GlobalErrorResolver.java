package com.barath.app.order.error;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class GlobalErrorResolver implements ApplicationContextAware {
	
	private static Map<Integer,String> errors=null;
	
	
	private static ApplicationContext context;
		
	
	public static GlobalError resolveError(int errorCode,Exception ex){
		
		GlobalError error=null;
		if(errorCode > 0 ){
			
			String errorMessage=errors.get(Integer.valueOf(errorCode));
			List<String> errorTrace=populateErrorStatusList(ex);
			HttpStatus status=HttpStatus.valueOf(errorCode) !=null ? HttpStatus.valueOf(errorCode) : HttpStatus.INTERNAL_SERVER_ERROR;
			error=new GlobalError(errorCode, errorMessage, status, errorTrace);
		}else{
			
		}
		return error;
		
		
		
	}
	
	public static String resolveErrorMessage(int errorCode){
		
		String errorMessage=null;
		if(errorCode > 0 ){
			
			errorMessage=errors.get(Integer.valueOf(errorCode));
			
		}
		return errorMessage;
		
		
		
	}
	
	
	private static List<String> populateErrorStatusList(Exception ex){
		
		List<String> errorTrace=new ArrayList<String>();
		if( ex !=null){
			
			for(StackTraceElement element : ex.getStackTrace()){
				errorTrace.add(element.toString());
			}			
			
		}
		return errorTrace;
	}



	@Override
	public void setApplicationContext(ApplicationContext context) throws BeansException {
		GlobalErrorResolver.context=context;
		
	}



	public static ApplicationContext getContext() {
		return context;
	}



	public static void setContext(ApplicationContext context) {
		GlobalErrorResolver.context = context;
	}
	
	@PostConstruct
	public void init(){
		
		errors=GlobalErrorResolver.getContext().getBean(GlobalErrorCodes.class).getErrors();
	
	}

	public static GlobalError resolveErrorMessage(String errorMessage,Exception ex) {
	
			return new GlobalError(500, errorMessage, HttpStatus.INTERNAL_SERVER_ERROR, populateErrorStatusList(ex));
		
	}
	
	
	
	
	

}
