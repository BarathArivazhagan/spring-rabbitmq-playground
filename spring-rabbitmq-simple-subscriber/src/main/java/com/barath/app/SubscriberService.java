package com.barath.app;

import java.io.IOException;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class SubscriberService {
	
	private static final ObjectMapper mapper=new ObjectMapper();
	
	//@RabbitListener(queues="${queue.name}")
	public void receiveMessage(Message message){
		
		System.out.println("Listener is called ");
		if( message !=null){
		Customer customer=null;
		try {
			if(log.isInfoEnabled()){
				log.info("Message received {}",message);
			}
			customer = mapper.readValue(message.getBody(),Customer.class);
			if(log.isInfoEnabled()){
				log.info("Customer cust {}",customer.toString());
			}
		} catch (JsonParseException e) {
			log.error("Error during Jackson conversion ",e);
			e.printStackTrace();
		} catch (JsonMappingException e) {
			log.error("Error during Jackson conversion ",e);
			e.printStackTrace();
		} catch (IOException e) {
			log.error("Error during Jackson conversion ",e);
			e.printStackTrace();
		}
		
		}
	}
	
	
		@RabbitListener(queues="${queue.name}")
		public void receiveCustomer(Customer customer){
			
			System.out.println("Listener is called ");
			if( customer !=null){
				System.out.println("CUSTOMER RECEIVED "+customer.toString());
			}
		}

}
