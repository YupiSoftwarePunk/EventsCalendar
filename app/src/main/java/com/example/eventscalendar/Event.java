package com.example.eventscalendar;

import java.io.Serializable;

public class Event implements Serializable {
    private String name;
    private String url;
    private String startsAt;
    private String city;
    private String address;

    public Event(String name, String url, String startsAt, String city, String address) {
        this.name = name;
        this.url = url;
        this.startsAt = startsAt;
        this.city = city;
        this.address = address;
    }

    public Event(String name, String url) {
        this.name = name;
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    public String getStartsAt() {
        return startsAt;
    }

    public String getCity() {
        return city;
    }

    public String getAddress() {
        return address;
    }

    @Override
    public String toString() {
        return "Event{" +
                "name='" + name + '\'' +
                ", url='" + url + '\'' +
                ", startsAt='" + startsAt + '\'' +
                ", city='" + city + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
