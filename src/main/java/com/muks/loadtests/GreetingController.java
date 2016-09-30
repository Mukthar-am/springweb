package com.muks.loadtests;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
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
    private final AtomicLong eventPingsCounter = new AtomicLong();

    private final AtomicLong payloadPingsCounter = new AtomicLong();

    @RequestMapping(method = RequestMethod.POST, headers="Accept=application/json")
    public HttpStatus track(@RequestBody String json) {
        payloadPingsCounter.incrementAndGet();
        System.out.println("# Payload: " + json.toString());

        return (HttpStatus.OK);
    }

    /** ========================================================================================
     *
     * eventPingsCounter - method which accpets a json payload and logs back the same as a http response
     *
     * @param json
     * @return
     */
    @RequestMapping(value = "batchdump", method = RequestMethod.POST, headers="Accept=application/json")
    public HttpStatus consumeBatchEventsAndDump(@RequestBody String json) {
        payloadPingsCounter.incrementAndGet();

        JsonNode payloadTreeBatch = stringToJsonNode(json);

        for (int i = 0; i < payloadTreeBatch.size(); i++) {
            JsonNode innerArrayNode = payloadTreeBatch.get(i);
            Iterator<Map.Entry<String, JsonNode>> innerArrNodeFields = innerArrayNode.fields();
            while (innerArrNodeFields.hasNext()) {
                Map.Entry<String, JsonNode> payloadInnermost = innerArrNodeFields.next();
                if (payloadInnermost.getKey().equalsIgnoreCase("device")) {
                    System.out.println(payloadInnermost.getValue());
                }
            }
        }

        return (HttpStatus.OK);
    }


    /** ========================================================================================
     *
     * eventPingsCounter - method which accpets a json payload and logs back the same as a http response
     *
     * @param json
     * @return
     */
    @RequestMapping(value = "trackdump", method = RequestMethod.POST, headers="Accept=application/json")
    public HttpStatus consumeEventsAndDump(@RequestBody String json) {

        payloadPingsCounter.incrementAndGet();

        Iterator<Map.Entry<String, JsonNode>> payloadTree = stringToJsonNode(json).fields();
        while (payloadTree.hasNext()) {
            Map.Entry<String, JsonNode> payloadNode = payloadTree.next();

            if (payloadNode.getKey().equalsIgnoreCase("device")) {
                System.out.println(payloadNode.getValue());
            }
        }

        return (HttpStatus.OK);
    }


    /** ========================================================================================
     *
     * eventPingsCounter - method which accpets a json payload and logs back the same as a http response
     *
     * @param json
     * @return
     */
    @RequestMapping(value = "track", method = RequestMethod.POST, headers="Accept=application/json")
    public HttpStatus consumeEvents(@RequestBody String json) {
        eventPingsCounter.incrementAndGet();
        System.out.println("\n# Payload: " + json + "---------------------\n");

        return (HttpStatus.OK);
    }

    /**
     *  Returns the event count value via a rest GET call
     * @return  - the value of eventPingsCounter
     */
    @RequestMapping(value = "info", method = RequestMethod.GET)
    public AtomicLong getEventCounts() {
        return (eventPingsCounter);
    }

    /**
     *  Returns the event count value via a rest GET call
     * @return  - the value of eventPingsCounter
     */
    @RequestMapping(value = "infopings", method = RequestMethod.GET)
    public AtomicLong getPayloadUploadCounts() {
        return (payloadPingsCounter);
    }

    /**
     * Resetting events-counter variable
     */
    @RequestMapping(value = "reset", method = RequestMethod.GET)
    public HttpStatus resetEventsCounter() {
        this.eventPingsCounter.set(0l);
        this.payloadPingsCounter.set(0l);
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


    public static JsonNode stringToJsonNode(String value) {
        JsonNode map = null;
        try {

            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
            mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
            mapper.configure(JsonParser.Feature.ALLOW_MISSING_VALUES, true);
            mapper.configure(JsonParser.Feature.ALLOW_NON_NUMERIC_NUMBERS, true);

            // convert JSON string to Map
            map = mapper.readValue(value, JsonNode.class);
            //map = mapper.readValue(value, new TypeReference<Map<String, Object>>() {});

        } catch (IOException e) {
            e.printStackTrace();
        }

        return map;
    }
}