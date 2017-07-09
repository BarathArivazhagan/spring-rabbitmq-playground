package com.barath.app;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
@EnableRabbit
public class SubscriberConfiguration {
	
	@Value("${queue.name}")
	private String queueName;
	
	@Value("${exchange.name}")
	private String exchangeName;
	
	@Value("${spring.rabbitmq.host:localhost}")
	private String rabbitMQHost;
	
	
	@Value("${spring.rabbitmq.port:5672}")
	private int rabbitMQPort;
	
	
	@Bean
	public CachingConnectionFactory connectionFactory(){
		CachingConnectionFactory factory=new CachingConnectionFactory();
		factory.setHost(rabbitMQHost);
		factory.setPort(rabbitMQPort);
		return factory;
	}
	
	@Bean
	public Queue queue(){
		return new Queue(queueName);
	}
	
	@Bean
	public TopicExchange topicExchange(){
		TopicExchange topicExchange=new TopicExchange(exchangeName);
		return topicExchange;
	}
	
	
	@Bean
    public Binding binding(Queue queue, TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(queueName);
    }
	
	@Bean
	public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory){
		return new RabbitTemplate(connectionFactory);
	}
	
	
	@Bean
	public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory() {
	        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
	        factory.setConnectionFactory(connectionFactory());
	        factory.setConcurrentConsumers(3);
	        factory.setMaxConcurrentConsumers(10);
	        return factory;
	    }

}
