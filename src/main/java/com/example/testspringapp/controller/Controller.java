package com.example.testspringapp.controller;

import com.example.testspringapp.Models.Table;
import com.example.testspringapp.Serialization.TableSerializer;
import com.example.testspringapp.TestSpringAppApplication;
import com.github.javafaker.Faker;
import com.google.gson.Gson;
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
        String tableName1 = "Position";
        String[] columns1 = {"PositionID", "PositionName"};
        String[] dataTypes1 = {"integer", "string"};
        String[] constraintTypes1 = {"PK", ""};
        String[] references1 = {};
        Table table1 = new Table(tableName1, columns1, dataTypes1, constraintTypes1, references1);


        String tableName2 = "Project";
        String[] columns2 = {"ProjectID", "ProjectName", "Description"};
        String[] dataTypes2 = {"integer", "string", "string"};
        String[] constraintTypes2 = {"PK", "", ""};
        String[] references2 = {};
        Table table2 = new Table(tableName2, columns2, dataTypes2, constraintTypes2, references2);

        String tableName3 = "Employee";
        String[] columns3 = {"EmployeeID", "FirstName", "LastName", "PositionID", "ProjectID"};
        String[] dataTypes3 = {"integer", "string", "string", "integer", "integer"};
        String[] constraintTypes3 = {"PK", "", "", "FK", "FK"};
        String[] references3 = {"Position", "Project"};
        Table table3 = new Table(tableName3, columns3, dataTypes3, constraintTypes3, references3);

        Table[] tables = {table1, table2, table3};

        Gson gson = new Gson();
        String json = gson.toJson(tables);
        return new ResponseEntity<>(json ,HttpStatus.OK);
    }
}
