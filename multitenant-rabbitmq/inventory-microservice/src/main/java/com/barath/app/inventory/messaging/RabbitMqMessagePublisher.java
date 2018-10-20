package com.barath.app.inventory.messaging;

import java.io.UnsupportedEncodingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.barath.app.inventory.dto.Order;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author barath.arivazhagan
 *
 */
@Component
@Profile("rmq")
public class RabbitMqMessagePublisher implements MessagePublisher {

	private static final Logger LOGGER = LoggerFactory.getLogger(RabbitMqMessagePublisher.class);

	@Value("${exchange.name}")
	private String exchangeName;

	@Value("${order.response.queue}")
	private String orderResQueue;

	@Autowired
	private RabbitTemplate template;


	/**
	 * Default constructor
	 */
	public RabbitMqMessagePublisher() {
	}

	@Override
	public void confirmOrder(Order order) {
		ObjectMapper objectMapper = new ObjectMapper();
		String message;
		try {
			message = objectMapper.writeValueAsString(order);
			MessageProperties props = new MessageProperties();
			props.setContentType(MessageProperties.CONTENT_TYPE_TEXT_PLAIN);
			props.setContentEncoding("UTF-8");
			Message textMessage = new Message(message.getBytes("UTF8"), props);
			System.out.println("TENANT FROM MDC "+	MDC.get("TENANT_ID"));
			textMessage.getMessageProperties().getHeaders().put("TENANT_ID",MDC.get("TENANT_ID"));
			template.send(exchangeName,orderResQueue,textMessage);
			if (LOGGER.isInfoEnabled()) {
				LOGGER.info("Message posted in Response Queue: {}", textMessage.toString());
			}
		}  catch (JsonProcessingException e) {
			LOGGER.error("error ", e);
			
		} catch (UnsupportedEncodingException e) {
			LOGGER.error("error ", e);
			
		}
	}


	public RabbitTemplate getTemplate() {
		return template;
	}

	public void setTemplate(RabbitTemplate template) {
		this.template = template;
	}

	


	

}
