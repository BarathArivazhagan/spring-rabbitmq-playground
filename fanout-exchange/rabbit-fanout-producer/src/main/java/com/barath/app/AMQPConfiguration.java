package com.barath.app;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
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
	
	@Value("${queue1.name:test-queue}")
	private String queue1;
	
	@Value("${queue2.name:test-queue}")
	private String queue2;
	
	@Bean
	public FanoutExchange exchange(){
		return new FanoutExchange(exchangeName);
	}
	
	@Bean
	public Queue  queue1(){
		return new Queue(queue1);
	}
	
	@Bean
	public Queue  queue2(){
		return new Queue(queue2);
	}
	
	@Bean
	public Binding bindQueue1WithExchange(){
		return BindingBuilder.bind(queue1()).to(exchange());
	}
	
	@Bean
	public Binding bindQueue2WithExchange(){
		return BindingBuilder.bind(queue2()).to(exchange());
	}
	
	@Bean
	public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory){
		RabbitTemplate rabbitTemplate= new RabbitTemplate(connectionFactory);
		rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
		return rabbitTemplate;
	}

}
