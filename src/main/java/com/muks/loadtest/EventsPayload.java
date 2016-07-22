package com.muks.loadtest;


public class EventsPayload {
    private long age;
    private String name;
    private String city;

    public EventsPayload() {
    }
    public EventsPayload(String name, long age, String city) {
        this.age = age;
        this.name = name;
        this.city = city;
    }

    public long getAge() {
        return age;
    }
    public String getName() {
        return name;
    }
    public String getCity() {
        return city;
    }

    public String toString() {
        return "{[EventId: " + age + ", GreetingContent: " + name + "]}";
    }
}
