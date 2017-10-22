package com.barath.app;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AppController {

    private PublisherService publisherService;

    public AppController(PublisherService publisherService){
        this.publisherService=publisherService;
    }


    @PostMapping("/customer")
    public Customer publishCustomerToRabbitMQ(@RequestBody Customer customer){

        publisherService.publishMessage(customer);
        return customer;
    }

}
