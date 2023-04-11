package com.example.testspringapp.controller;

import com.example.testspringapp.TestSpringAppApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {
    TestSpringAppApplication backendService = new TestSpringAppApplication();

    @GetMapping("/")
    public String home() {

        return "Home page";
    }

    @GetMapping("/hello")
    public String sayHello(@RequestParam(value = "myName", defaultValue = "World") String name) {
        return String.format("Hello %s!", name);
    }

}
