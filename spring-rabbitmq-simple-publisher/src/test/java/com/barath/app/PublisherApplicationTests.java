package com.barath.app;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.barath.app.Customer.CustomerGender;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PublisherApplicationTests {

	@Autowired
	private PublisherService publisher;
	
	
	@Test
	public void testSendCustomer(){
		
		Set<Account> accounts=new HashSet<Account>();
		accounts.add(new Account(1000000000L));
		Customer customer=new Customer(1000L, "barath", CustomerGender.MALE, accounts);
		publisher.publishMessage(customer);
		
	}

}
