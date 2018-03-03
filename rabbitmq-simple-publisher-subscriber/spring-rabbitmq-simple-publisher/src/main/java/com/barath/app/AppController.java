package com.barath.app;

import org.springframework.web.bind.annotation.*;

@RestController
public class AppController {

    private PublisherService publisherService;

    public AppController(PublisherService publisherService){
        this.publisherService=publisherService;
    }


    @PostMapping("/customer")
    public Customer publishCustomerToRabbitMQ(@RequestBody Customer customer){

        publisherService.publishCustomerMessage(customer);
        return customer;
    }

    @GetMapping("/publish/sync")
    public String publishSyncMessage(@RequestParam("message") String message){
        return publisherService.publishAndReceiveMessage(message);
    }

}
