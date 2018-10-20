package com.barath.app;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AMQPConfiguration {
	
	@Value("${exchange.name:test-fanout}")
	private String exchangeName;
	
	@Value("${queue.name:test-queue}")
	private String queueName;
	
	@Bean
	public FanoutExchange exchange(){
		return new FanoutExchange(exchangeName);
	}
	
	@Bean
	public Queue  queue(){
		return new Queue(queueName);
	}
	
	@Bean
	public Binding bindQueueWithExchange(){
		return BindingBuilder.bind(queue()).to(exchange());
	}
	
	@Bean
	public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory){
		RabbitTemplate rabbitTemplate= new RabbitTemplate(connectionFactory);
		rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
		return rabbitTemplate;
	}
	
	@Bean
	public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(ConnectionFactory connectionFactory) {
	        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
	        factory.setConnectionFactory(connectionFactory);
	        factory.setConcurrentConsumers(3);
	        factory.setMaxConcurrentConsumers(10);
	        factory.setMessageConverter(new Jackson2JsonMessageConverter());
	        return factory;
	    }

}
