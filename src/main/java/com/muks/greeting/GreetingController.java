package com.muks.greeting;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.muks.loadtest.EventsPayload;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
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
    private final AtomicLong eventsCounter = new AtomicLong();


    /** ========================================================================================
     *
     * eventsCounter - method which accpets a json payload and logs back the same as a http response
     *
     * @param json
     * @return
     */
    @RequestMapping(value = "events", method = RequestMethod.POST, headers="Accept=application/json")
    public ResponseEntity<EventsPayload> loadEvents(@RequestBody String json) {

        EventsPayload payload = new EventsPayload();
        ObjectMapper mapper = new ObjectMapper();
        try {
            payload = mapper.readValue(json, EventsPayload.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        payload.setId(eventsCounter.incrementAndGet());
        System.out.println("# Payload: " + payload.toString());

        return new ResponseEntity<EventsPayload>(payload, HttpStatus.OK);
    }


    /** ========================================================================================
     *
     * eventsCounter - method which accpets a json payload and logs back the same as a http response
     *
     * @param json
     * @return
     */
    @RequestMapping(value = "track", method = RequestMethod.POST, headers="Accept=application/json")
    public HttpStatus consumeEvents(@RequestBody String json) {

        EventsPayload payload = new EventsPayload();
        ObjectMapper mapper = new ObjectMapper();
        try {
            payload = mapper.readValue(json, EventsPayload.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        payload.setId(eventsCounter.incrementAndGet());
        //System.out.println("# Payload: " + payload.toString());

        return (HttpStatus.OK);
    }

    /**
     *  Returns the event count value via a rest GET call
     * @return  - the value of eventsCounter
     */
    @RequestMapping(value = "info", method = RequestMethod.GET)
    public AtomicLong getEventCounts() {
        return (eventsCounter);
    }


    /**
     * Resetting events-counter variable
     */
    @RequestMapping(value = "reset", method = RequestMethod.GET)
    public HttpStatus resetEventsCounter() {
        this.eventsCounter.set(0l);
        return (HttpStatus.OK);
    }


    /** ========================================================================================= */
    @RequestMapping(value = "getPerson", method = RequestMethod.GET)
    public ResponseEntity<Greeting> greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
        Greeting greeting = new Greeting(counter.incrementAndGet(), String.format(template, name));
        System.out.println("Get() Greeting ID: " + greeting.toString());
        return new ResponseEntity<Greeting>(greeting, HttpStatus.OK);
    }

    @RequestMapping(value = "postPerson", method = RequestMethod.POST)
    public ResponseEntity<Greeting> greetingPost(@RequestParam(value = "name", defaultValue = "World") String name) {
        Greeting greeting = new Greeting(counter.incrementAndGet(), String.format(template, name));
        System.out.println("# Post Controller: Name = " + name + ", " + greeting.toString());
        return new ResponseEntity<Greeting>(greeting, HttpStatus.OK);
    }

}