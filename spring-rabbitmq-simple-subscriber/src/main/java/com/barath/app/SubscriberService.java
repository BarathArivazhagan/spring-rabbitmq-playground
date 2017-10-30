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

	
		@RabbitListener(queues="${queue.name}")
		public void receiveCustomer(Customer customer){
			
			System.out.println("Listener is called ");
			if( customer !=null){
				System.out.println("CUSTOMER RECEIVED "+customer.toString());
			}
		}

		@RabbitListener(queues="${sync.queue.name}")
	    public Message receiveAndSendMessage(Message message){
			System.out.println("Message received "+message.getBody());
			return message;
		}

}
