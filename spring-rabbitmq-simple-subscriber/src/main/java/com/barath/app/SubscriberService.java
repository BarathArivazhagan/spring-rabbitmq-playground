package com.barath.app;

import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.util.Optional;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

/**
 *  Subscriber class to receive messages from rabbitmq queues and exchanges
 */
@Service
@Slf4j
public class SubscriberService {

	private static final ObjectMapper mapper=new ObjectMapper();
	private static final Logger logger= LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getName());

	/**
	 * This method receives a customer when message is published to the rabbitmq queue
	 *
	 * @param customer
	 */
	@RabbitListener(queues="${queue.name}")
	public void receiveCustomer(Customer customer){

	  logger.info(" rabbit listener to receive customer invoked ");
	  Optional.ofNullable(customer).ifPresent( cust -> logger.info("customer received with details {}",cust));
	}

	/**
	 *This method receives and sends a message when message is published to the sync rabbitmq queue
	 * This demonstrates the usage of sync way of publishing and receiving messages using amqp protocol
	 *
	 * @param message
	 * @return message  an instance of {@link org.springframework.amqp.core.Message }
	 */
	@RabbitListener(queues="${sync.queue.name}")
	public Message receiveAndSendMessage(Message message){
		    Optional.ofNullable(message).ifPresent( mess -> logger.info( "message received in sync queue {}",mess.getBody()));
			return message;
	}

}
