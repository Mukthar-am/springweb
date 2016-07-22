package com.muks.greeting;

import com.muks.loadtest.EventsPayload;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
@RequestMapping("track")
public class GreetingController {
    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();
    private final AtomicLong events = new AtomicLong();


    @RequestMapping(value = "getPerson", method = RequestMethod.GET)
    public ResponseEntity<Greeting> greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
        Greeting greeting = new Greeting(counter.incrementAndGet(), String.format(template, name));
        System.out.println("Get() Greeting ID: " + greeting.toString());
        return new ResponseEntity<Greeting>(greeting, HttpStatus.OK);
    }

    @RequestMapping(value = "postPerson", method = RequestMethod.POST)
    public ResponseEntity<Greeting> greetingPost(@RequestParam(value = "name", defaultValue = "World") String name) {
        Greeting greeting = new Greeting(counter.incrementAndGet(), String.format(template, name));
        System.out.println("# Post Controller: " + greeting.toString());
        return new ResponseEntity<Greeting>(greeting, HttpStatus.OK);
    }

    @RequestMapping(value = "events", method = RequestMethod.GET)
    public ResponseEntity<EventsPayload> loadEvents(@RequestParam(value = "name", defaultValue = "World") String name) {
        EventsPayload eventsPayload = new EventsPayload(events.incrementAndGet(), String.format(template, name));
        return new ResponseEntity<EventsPayload>(eventsPayload, HttpStatus.OK);
    }


    @RequestMapping(value = "info", method = RequestMethod.GET)
    public AtomicLong getEventCounts() {
        return (events);
    }
}