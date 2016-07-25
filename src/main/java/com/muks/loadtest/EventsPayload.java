package com.muks.loadtest;


public class EventsPayload {
    private long id;
    private long age;
    private String name;
    private String city;
    private String description;

    public EventsPayload() {}

    public EventsPayload(long id, String name, long age, String city, String description) { }

    public EventsPayload(String name, long age, String city, String description) {
        this.id = id;
        this.age = age;
        this.name = name;
        this.city = city;
        this.description = description;
    }

    public void setId(long id) {
        this.id = id;
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
    public String getDescription() { return description; }

    public String toString() {
        return "{ID: " + id + ", " +
                "Name: " + name + ", " +
                "Age: " + age + ", " +
                "City: " + city + ", " +
                "Description: " + description + "}";
    }
}
