package com.spring.boot.rocks.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/service2")
public class SecondServiceController {

    @GetMapping("/message")
    public String serviceTwoHome(){
        return "The response message from Second Service";
    }

}
