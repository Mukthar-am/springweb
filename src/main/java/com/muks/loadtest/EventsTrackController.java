package com.muks.loadtest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by 15692 on 23/06/16.
 *
 * URI: http://localhost:8080/springweb-1.0/greeting?user=muks
 */

@Controller
@RequestMapping("track")
public class EventsTrackController {
    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

//    @RequestMapping(value = "events", method = RequestMethod.GET)
    public ResponseEntity<EventsPayload> greetingPost(@RequestParam(value="name", defaultValue="World") String name) {
        EventsPayload eventsPayload = new EventsPayload(counter.incrementAndGet(), String.format(template, name));
        System.out.println("# Load Track: " + eventsPayload.toString());
        return new ResponseEntity<EventsPayload>(eventsPayload, HttpStatus.OK);
    }
}