package com.example.testspringapp.controller;

import com.example.testspringapp.Models.Table;
import com.example.testspringapp.Serialization.TableSerializer;
import com.example.testspringapp.TestSpringAppApplication;
import com.github.javafaker.Faker;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping(value = "/api")
public class Controller {
    TestSpringAppApplication backendService = new TestSpringAppApplication();
    private final Faker faker = new Faker(new Locale("en"));


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
        } catch (Exception exception) {
            return new ResponseEntity<>("invalid arguments\n" + exception, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/generate")
    public String generateRegex(@RequestBody String regex) {
        return String.format("%s\n", faker.regexify(regex));
    }

    @GetMapping("/generate/address")
    public String generateAddress() {
        String streetName = faker.address().streetName();
        String number = faker.address().buildingNumber();
        String city = faker.address().city();
        String country = faker.address().country();

        return String.format("%s\n%s\n%s\n%s",
                number,
                streetName,
                city,
                country);
    }

    @GetMapping("/generate/string")
    public String generateString(@RequestParam(value = "count", defaultValue = "10") Integer count) {
        String regex = String.format("[a-zA-Z]{%s}", count.toString());
        return faker.regexify(regex);
    }

    @GetMapping("/generate/phoneNumber")
    public String generatePhoneNumber() {
        return String.format("%s\n%s\n",
                faker.phoneNumber().phoneNumber(),
                faker.phoneNumber().cellPhone());
    }

    @GetMapping("/generate/number")
    public String generateNumber(@RequestParam(value = "count", defaultValue = "10") Integer count) {
        String regex = String.format("[1-9]{%s}", count.toString());
        return faker.regexify(regex);
    }

    @GetMapping("/generate/numberBetween")
    public String generateNumber(@RequestParam(value = "min", defaultValue = "0") Integer min, @RequestParam(value = "max", defaultValue = "100") Integer max) {
        return String.format("%s", faker.number().numberBetween(min, max));
    }

    @GetMapping("/generate/company")
    public String generateCompany() {
        return String.format("%s\n%s\n%s\n%s",
                faker.company().name(),
                faker.company().industry(),
                faker.company().profession(),
                faker.company().url());
    }

    @GetMapping("/generate/firstname")
    public String generateFirstName() {
        return String.format("%s\n", faker.name().firstName());
    }

    @GetMapping("/generate/lastname")
    public String generateLastName() {
        return String.format("%s\n", faker.name().lastName());
    }

    @GetMapping("/generate/fullname")
    public String generateFullName() {
        return String.format("%s\n", faker.name().fullName());
    }

    @GetMapping("/dbase")
    @ResponseBody
    public ResponseEntity<?> getDatabase() {
        String[] columns = {"id", "name", "desc"};
        String[] dataTypes = {"integer", "string", "string"};
        String[] constraintTypes = {"PK", "", ""};
        Table table = new Table(columns, dataTypes, constraintTypes);

        TableSerializer serializer = new TableSerializer();
        String json = serializer.serialize(table);
        return new ResponseEntity<>(json ,HttpStatus.OK);
    }
}
