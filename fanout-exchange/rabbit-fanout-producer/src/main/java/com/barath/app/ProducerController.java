package com.barath.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ProducerController {
	
	@Autowired
	private ProducerService service;
	
	@GetMapping("/")
	public String invokeProducer(){
		
		Order order=new Order(1, "TV");
		service.publishOrder(order);
		
		return "MessagePublished";
	}

}
