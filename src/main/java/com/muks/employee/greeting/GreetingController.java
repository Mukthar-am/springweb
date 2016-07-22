package com.muks.employee.greeting;

import com.muks.employee.employee.Employee;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by 15692 on 23/06/16.
 *
 * URI: http://localhost:8080/springweb-1.0/greeting?user=muks
 */

@RestController
@RequestMapping("/greeting")
public class GreetingController {

    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();


    @RequestMapping(value = "getPerson", method = RequestMethod.GET)
    public ResponseEntity<Greeting> greeting(@RequestParam(value="name", defaultValue="World") String name) {
        Greeting greeting = new Greeting(counter.incrementAndGet(), String.format(template, name));
        System.out.println(greeting.toString());
        return new ResponseEntity<Greeting>(greeting, HttpStatus.OK);
    }

    @RequestMapping(value = "postPerson", method = RequestMethod.POST)
    public ResponseEntity<Greeting> greetingPost(@RequestParam(value="name", defaultValue="World") String name) {
        Greeting greeting = new Greeting(counter.incrementAndGet(), String.format(template, name));
        System.out.println("# Post Controller: " + greeting.toString());
        return new ResponseEntity<Greeting>(greeting, HttpStatus.OK);
    }
}