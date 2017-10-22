package com.barath.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.lang.invoke.MethodHandles;

@Service
public class PublisherService {
	
	private static final ObjectMapper mapper=new ObjectMapper();

	private static final Logger logger= LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
	@Autowired
	private RabbitTemplate rabbitTemplate;
	
	@Value("${queue.name}")
	private String queueName;
	
	
	public void publishMessage(Customer customer){
		
		try {
			String customerJson=mapper.writeValueAsString(customer);
			logger.info("Customer  {}",customerJson);
			rabbitTemplate.convertAndSend(queueName,customer);
		} catch (JsonProcessingException e) {
			
			e.printStackTrace();
		}
		
	}

}
