package com.spring.boot.rocks.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/service1")
public class FirstServiceController {

    @GetMapping("/message")
    public String serviceOneHome(){
        return "The response message from First Service";
    }

}