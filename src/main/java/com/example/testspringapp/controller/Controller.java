package com.example.testspringapp.controller;

import com.example.testspringapp.TestSpringAppApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@CrossOrigin
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

    @GetMapping("/json")
    public ResponseEntity<?> create() {
        Map<String, String> data = new HashMap<>();
        data.put("key1", "value1");
        data.put("key2", "value2");
        return new ResponseEntity<>(data, HttpStatus.OK);
    }

    @PostMapping("/add/{firstNumber}/{secondNumber}")
    public ResponseEntity<?> create(@PathVariable String firstNumber, @PathVariable String secondNumber) {
        try {
            int num1 = Integer.parseInt(firstNumber);
            int num2 = Integer.parseInt(secondNumber);
            int sum = num1 + num2;
            return new ResponseEntity<>(String.valueOf(sum), HttpStatus.OK);
        } catch (Exception exception){
            return new ResponseEntity<>("invalid arguments\n" + exception, HttpStatus.BAD_REQUEST);
        }
    }
}
