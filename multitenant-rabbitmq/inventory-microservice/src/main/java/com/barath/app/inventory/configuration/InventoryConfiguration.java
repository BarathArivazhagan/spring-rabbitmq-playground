package com.barath.app.inventory.configuration;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;



/**
 * @author barath.arivazhagan
 * 
 *
 */
@Profile("rmq")
@Configuration
@EnableRabbit
public class InventoryConfiguration {

	@Value("${exchange.name}")
	private String exchangeName;
	
	

	@Value("${order.request.queue}")
	private String orderReqQueue;
	
	@Value("${order.response.queue}")
	private String orderResQueue;

	@Value("${cancel.order.queue}")
	private String cancelOrderQueue;

	

	/**
	 * Default Constructor
	 */
	public InventoryConfiguration() {
		
	}

	@Bean
	public Queue orderReqQueue() {
		return new Queue(orderReqQueue, true);
	}

	@Bean
	public Queue orderResQueue() {
		return new Queue(orderResQueue, true);
	}
	
	@Bean
	public Queue cancelOrderQueue() {
		return new Queue(orderResQueue, true);
	}

	@Bean
	public DirectExchange exchange() {
		return new DirectExchange(exchangeName);
	}
	
	
	@Bean
	public Binding orderRequestQueueBinding() {
		return BindingBuilder.bind(orderReqQueue()).to(exchange()).with(orderReqQueue);
	}

	@Bean
	public Binding orderResponseQueueBinding() {
		return BindingBuilder.bind(orderResQueue()).to(exchange()).with(orderResQueue);
	}

	
	@Bean
	public Binding cancelOrderQueueBinding() {
		return BindingBuilder.bind(cancelOrderQueue()).to(exchange()).with(cancelOrderQueue);
	}

	@Bean
	public SimpleMessageListenerContainer container(ConnectionFactory connectionFactory){
		SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
		container.setConnectionFactory(connectionFactory);		
		return container;
	}

	

	@Bean
	public RabbitTemplate rabbitTemplate(final ConnectionFactory connectionFactory) {
		final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
		return rabbitTemplate;
	}

}
