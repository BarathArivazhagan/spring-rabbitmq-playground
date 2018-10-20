package com.barath.app.order.configuration;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListenerConfigurer;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistrar;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.handler.annotation.support.DefaultMessageHandlerMethodFactory;



/**
 * Configuration class for RabbitMQ connection
 * <p>It extracts the property values from the application.properties
 * and bind with appropriate queues</p>
 *
 */
@Configuration
@EnableRabbit
@Profile("rmq")
public class OrderConfiguration {

	@Value("${order.request.queue}")
	private String orderReqQueue;

	@Value("${order.response.queue}")
	private String orderResQueue;


	@Value("${cancel.order.queue}")
	private String cancelOrderQueue;

	@Value("${exchange.name}")
	private String exchangeName;


	
	/**
	 * Default constructor
	 */
	public OrderConfiguration() {
		
	}


	public String getOrderReqQueue() {
		return orderReqQueue;
	}

	public void setOrderReqQueue(String orderReqQueue) {
		this.orderReqQueue = orderReqQueue;
	}

	public String getOrderResQueue() {
		return orderResQueue;
	}

	public void setOrderResQueue(String orderResQueue) {
		this.orderResQueue = orderResQueue;
	}

	public String getCancelOrderQueue() {
		return cancelOrderQueue;
	}

	public String getExchangeName() {
		return exchangeName;
	}




	public void setCancelOrderQueue(String cancelOrderQueue) {
		this.cancelOrderQueue = cancelOrderQueue;
	}


	public void setExchangeName(String exchangeName) {
		this.exchangeName = exchangeName;
	}




	/**
	 * All the required beans are created.
	 */
	@Bean
	public DirectExchange appExchange() {
		return new DirectExchange(exchangeName);
	}

	@Bean
	public Queue orderReqQueue() {
		return new Queue(orderReqQueue);
	}


	@Bean
	public Queue orderResQueue() {
		return new Queue(orderResQueue);
	}

	@Bean
	public Queue cancelOrderQueue() {
		return new Queue(cancelOrderQueue);
	}

	@Bean
	public Binding orderReqQueueBinding() {
		return BindingBuilder.bind(orderReqQueue())
				.to(appExchange())
				.with(orderReqQueue);
	}

	@Bean
	public Binding cancelOrderQueueBinding() {
		return BindingBuilder.bind(cancelOrderQueue())
				.to(appExchange())
				.with(cancelOrderQueue);
	}

	@Bean
	public RabbitTemplate rabbitTemplate
				(final ConnectionFactory connectionFactory) {
		
		final RabbitTemplate rabbitTemplate = 
				new RabbitTemplate(connectionFactory);
		rabbitTemplate.setMessageConverter(jackson2JsonMessageConverter());
		return rabbitTemplate;
	}

	@Bean
	public Jackson2JsonMessageConverter jackson2JsonMessageConverter() {
		return new Jackson2JsonMessageConverter();
	}





	@Bean
	public SimpleMessageListenerContainer container
			(ConnectionFactory connectionFactory) {
		SimpleMessageListenerContainer container = 
				new SimpleMessageListenerContainer();
		container.setConnectionFactory(connectionFactory);
		return container;
	}



}
