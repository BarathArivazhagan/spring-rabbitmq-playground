package com.barath.app;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import com.barath.app.Customer.CustomerGender;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class TestJacksonConversion {
	
	
	@Test
	public void testConversion() throws JsonParseException, JsonMappingException, IOException{
		Set<Account> accounts=new HashSet<Account>();
		accounts.add(new Account(1000000000L));
		Customer customer=new Customer(1000L, "barath", CustomerGender.MALE, accounts);
		ObjectMapper mapper=new ObjectMapper();
		String customerJson=mapper.writeValueAsString(customer);
		
		Customer customerObj=mapper.readValue(customerJson.getBytes(), Customer.class);
		
		System.out.println("SAVED CUSTOEMR "+customerObj.toString()); 
		
	}

}
