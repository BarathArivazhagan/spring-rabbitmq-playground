package com.barath.app;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class PublisherService {
	
	private static final ObjectMapper mapper=new ObjectMapper();
	@Autowired
	private RabbitTemplate rabbitTemplate;
	
	@Value("${queue.name}")
	private String queueName;
	
	
	public void publishMessage(Customer customer){
		
		try {
			String customerJson=mapper.writeValueAsString(customer);
			System.out.println("Customer "+customerJson);
			Message message=MessageBuilder.withBody(customerJson.getBytes()).build();
			rabbitTemplate.convertAndSend(queueName,customer);
		} catch (JsonProcessingException e) {
			
			e.printStackTrace();
		}
		
	}

}
