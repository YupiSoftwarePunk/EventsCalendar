package com.example.eventscalendar;

import android.location.Location;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

public class Event {

    @SerializedName("name")
    private String name;

    @SerializedName("url")
    private String url;

    @SerializedName("starts_at")
    private String startsAt;

    @SerializedName("location")
    private Location location;

    public Event(String name, String url, String startsAt, Location location) {
        this.name = name;
        this.url = url;
        this.startsAt = startsAt;
        this.location = location;
    }


    // setters
    public void setName(String name) {
        this.name = name;
    }
    public void setLocation(Location location) {
        this.location = location;
    }
    public void setStartsAt(String startsAt) {
        this.startsAt = startsAt;
    }

    public void setUrl(String url) {
        this.url = url;
    }


    // getters
    public String getStartsAt() {
        return startsAt;
    }
    public Location getLocation() {
        return location;
    }
    public String getName() {
        return name;
    }
    public String getUrl() {
        return url;
    }



    // Метод для сериализации объекта в JSON
    public String toJson() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    // Метод для десериализации объекта из JSON
    public static Event fromJson(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, Event.class);
    }

//    @Override
//    public String toString() {
//        return "Event{" +
//                "name='" + name + '\'' +
//                ", url='" + url + '\'' +
//                ", startsAt='" + startsAt + '\'' +
//                ", location=" + location +
//                '}';
//    }
}
