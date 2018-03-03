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
import java.util.Optional;

@Service
public class PublisherService {
	
	private static final ObjectMapper mapper=new ObjectMapper();

	private static final Logger logger= LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

	private RabbitTemplate rabbitTemplate;

	public PublisherService(RabbitTemplate rabbitTemplate){
		this.rabbitTemplate=rabbitTemplate;
	}
	
	@Value("${queue.name}")
	private String queueName;

	@Value("${sync.queue.name}")
	private  String syncQueueName;
	
	
	public void publishCustomerMessage(Customer customer){
		
		try {
			String customerJson=mapper.writeValueAsString(customer);
			logger.info("Customer  {}",customerJson);
			rabbitTemplate.convertAndSend(queueName,customer);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		
	}

	public String publishAndReceiveMessage(String message){

		logger.info("publishing and receving the message {}",message);
		Message reponseMessage=rabbitTemplate.sendAndReceive(syncQueueName,MessageBuilder.withBody(message.getBytes()).build());
		Optional.ofNullable(reponseMessage).ifPresent( response -> logger.info("Response message from Rabbit {}",response.getBody()));
		return new String(reponseMessage.getBody());
	}

}
