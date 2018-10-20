package com.barath.app;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;


@Service
public class ProducerService {
	
	private static final ObjectMapper mapper=new ObjectMapper();
	
	@Autowired
	private RabbitTemplate rabbitTemplate;
	
	@Autowired
	private FanoutExchange fanout;
	
	
	public void publishOrder(Order order){
		
		try {				
			rabbitTemplate.convertAndSend(fanout.getName(),"",order);
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		
	}

}
