package com.muks.loadtests;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
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
    @RequestMapping(value = "trackdump", method = RequestMethod.POST, headers="Accept=application/json")
    public HttpStatus consumeEventsAndDump(@RequestBody String json,
                                           @RequestHeader("Content-Encoding") String contentEncoding,
                                           @RequestHeader("Content-Type") String contentType) {

        payloadPingsCounter.incrementAndGet();
        System.out.println("# Track dump() : Content-Encoding: "
                + contentEncoding + ", Content-Type: " + contentType);

        return (HttpStatus.OK);
    }


    /** ========================================================================================
     *
     * eventPingsCounter - method which accpets a json payload and logs back the same as a http response
     *
     * @param json
     * @return
     */
    @RequestMapping(value = "track-1", method = RequestMethod.POST, headers="Accept=application/json")
    public HttpStatus baseConsumeEvents(@RequestBody String json) {
        eventPingsCounter.incrementAndGet();
        System.out.println("\n# Track-1() : Payload: " + json);

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


    /** ========================================================================================
     *
     * eventPingsCounter - method which accpets a json payload and logs back the same as a http response
     *
     * @param
     * @return
     *//*
    @RequestMapping(value = "gzip1", method = RequestMethod.POST)
    public HttpStatus consumeGzip(@RequestBody String file) {
        eventPingsCounter.incrementAndGet();

//        String name = "test.gz";
//
//        if (!file.isEmpty()) {
//            try {
//                byte[] bytes = file.getBytes();
//                BufferedOutputStream stream =
//                        new BufferedOutputStream(new FileOutputStream(new File(name)));
//                stream.write(bytes);
//                stream.close();
//                System.out.println("You successfully uploaded " + name + "!");
//            } catch (Exception e) {
//                System.out.println("You failed to upload " + name + " => " + e.getMessage());
//            }
//        } else {
//            System.out.println("You failed to upload " + name + " because the file was empty.");
//        }


        try {
            GZIPInputStream gis = new GZIPInputStream(new ByteArrayInputStream(file.getBytes()));
            System.out.println(gis.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }


        return (HttpStatus.OK);
    }



    public static final String ROOT = "upload-dir";
    @RequestMapping(value = "gzip", method = RequestMethod.POST)
    public String handleFileUpload(@RequestParam("file") MultipartFile file,
                                   RedirectAttributes redirectAttributes) {

        if (!file.isEmpty()) {
            try {
                Files.copy(file.getInputStream(), Paths.get(ROOT, file.getOriginalFilename()));
                redirectAttributes.addFlashAttribute("message",
                        "You successfully uploaded " + file.getOriginalFilename() + "!");
            } catch (IOException|RuntimeException e) {
                redirectAttributes.addFlashAttribute("message", "Failued to upload " + file.getOriginalFilename() + " => " + e.getMessage());
            }
        } else {
            redirectAttributes.addFlashAttribute("message", "Failed to upload " + file.getOriginalFilename() + " because it was empty");
        }

        return "redirect:/";
    }
*/

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

}