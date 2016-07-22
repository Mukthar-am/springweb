package com.muks.loadtest;


public class EventsPayload {
    private final long id;
    private final String content;

    public EventsPayload(long id, String content) {
        this.id = id;
        this.content = content;
    }

    public long getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public String toString() {
        return "{[EventId: " + id + ", GreetingContent: " + content + "]}";
    }
}
