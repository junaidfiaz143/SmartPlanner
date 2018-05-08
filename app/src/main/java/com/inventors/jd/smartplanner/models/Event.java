package com.inventors.jd.smartplanner.models;

/**
 * Created by jd on 20-Apr-18.
 */

public class Event {
    private int id;
    private String title;
    private String description;
    private String time;
    private String from_day;
    private String from_month;
    private String from_year;
    private String to_day;
    private String to_month;
    private String to_year;
    private String location;
    private String image;
    private String week_days;
    private int type;

    public Event() {
    }

    public Event(int id, String title, String description, String time, String from_day, String from_month, String from_year, String to_day, String to_month, String to_year, String location, String image, String week_days, int type) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.time = time;
        this.from_day = from_day;
        this.from_month = from_month;
        this.from_year = from_year;
        this.to_day = to_day;
        this.to_month = to_month;
        this.to_year = to_year;
        this.location = location;
        this.image = image;
        this.week_days = week_days;
        this.type = type;
    }

    public Event(String title, String description, String time, String from_day, String from_month, String from_year, String to_day, String to_month, String to_year, String location, String image, String week_days, int type) {
        this.title = title;
        this.description = description;
        this.time = time;
        this.from_day = from_day;
        this.from_month = from_month;
        this.from_year = from_year;
        this.to_day = to_day;
        this.to_month = to_month;
        this.to_year = to_year;
        this.location = location;
        this.image = image;
        this.week_days = week_days;
        this.type = type;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setFrom_day(String from_day) {
        this.from_day = from_day;
    }

    public void setFrom_month(String from_month) {
        this.from_month = from_month;
    }

    public void setFrom_year(String from_year) {
        this.from_year = from_year;
    }

    public void setTo_day(String to_day) {
        this.to_day = to_day;
    }

    public void setTo_month(String to_month) {
        this.to_month = to_month;
    }

    public void setTo_year(String to_year) {
        this.to_year = to_year;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setWeek_days(String week_days) {
        this.week_days = week_days;
    }

    public void setType(int type) {
        this.type = type;
    }






    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getTime() {
        return time;
    }

    public String getFrom_day() {
        return from_day;
    }

    public String getFrom_month() {
        return from_month;
    }

    public String getFrom_year() {
        return from_year;
    }

    public String getTo_day() {
        return to_day;
    }

    public String getTo_month() {
        return to_month;
    }

    public String getTo_year() {
        return to_year;
    }

    public String getLocation() {
        return location;
    }

    public String getImage() {
        return image;
    }

    public String getWeek_days() {
        return week_days;
    }

    public int getType() {
        return type;
    }
}
