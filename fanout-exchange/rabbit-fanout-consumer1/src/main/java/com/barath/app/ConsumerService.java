package com.barath.app;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class ConsumerService {
	
	@RabbitListener(queues="${queue.name}")
	public void receiveCustomer(Order order){
		
		System.out.println("Listener is called ");
		if( order !=null){
			System.out.println("ORDER RECEIVED "+order.toString());
		}
	}

}
