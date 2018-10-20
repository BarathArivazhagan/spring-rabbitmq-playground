
package com.barath.app.order.messaging;

import java.io.UnsupportedEncodingException;

import com.barath.app.tenancy.context.TenancyContextHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.barath.app.order.entity.OrderItem;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
@Profile("rmq")
public class RabbitMqMessagePublisher implements MessagePublisher {

	private static final Logger LOGGER = 
			LoggerFactory.getLogger(RabbitMqMessagePublisher.class);

	@Autowired
	private RabbitTemplate template;

	@Value("${order.request.queue}")
	private String orderReqQueue;

	@Value("${cancel.order.queue}")
	private String cancelOrderQueue;

	@Value("${exchange.name}")
	private String exchangeName;

	

	@Override
	public void placeOrder(OrderItem order) {
		ObjectMapper mapper = new ObjectMapper();
		
		try {
			String message= mapper.writeValueAsString(order);
			MessageProperties props = new MessageProperties();
			props.
				setContentType(MessageProperties.CONTENT_TYPE_TEXT_PLAIN);

			byte[] messages=message.getBytes("UTF8");
			Message text =
					new Message(messages, props);
			text.getMessageProperties().getHeaders().put("TENANT_ID", TenancyContextHolder.getContext().getTenant().getIdentity());
			template.send(exchangeName, orderReqQueue, text);
			if (LOGGER.isInfoEnabled()) {
				LOGGER.info("Message posted:{}", text);
			}
		} catch (JsonProcessingException e) {
			LOGGER.error("error ", e);
			
		} catch (UnsupportedEncodingException e) {
			LOGGER.error("error ", e);
			
		}
	}

	@Override
	public void cancelOrder(OrderItem order) {
		ObjectMapper mapper = new ObjectMapper();		
		try {
			String message = mapper.writeValueAsString(order);
			MessageProperties properties = new MessageProperties();
			properties.
				setContentType(MessageProperties.CONTENT_TYPE_TEXT_PLAIN);
			byte[] messages=message.getBytes("UTF8");
			Message text = 
					new Message(messages, properties);
			template.send(exchangeName, cancelOrderQueue, text);
			if (LOGGER.isInfoEnabled()) {
				LOGGER.info("Message posted:{}", text);
			}
		} catch (JsonProcessingException e) {
			LOGGER.error("error ", e);
			
		} catch (UnsupportedEncodingException e) {
			LOGGER.error("error ", e);
			
		}
	}

	
	public RabbitTemplate getRabbitTemplate() {
		return template;
	}

	public void setRabbitTemplate(RabbitTemplate rabbitTemplate) {
		this.template = rabbitTemplate;
	}

	
	/**
	 * Default constructor for creating instances of RabbitMqMessagePublisher.
	 */
	public RabbitMqMessagePublisher(){
		//Default constructor
	}


	public RabbitTemplate getTemplate() {
		return template;
	}

	public void setTemplate(RabbitTemplate template) {
		this.template = template;
	}
	
	
	
	
}
