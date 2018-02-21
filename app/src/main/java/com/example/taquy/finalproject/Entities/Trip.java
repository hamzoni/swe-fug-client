package com.example.taquy.finalproject.Entities;

import com.example.taquy.finalproject.Misc.Tool;

import java.io.Serializable;
import java.util.Date;

public class Trip implements Serializable {
    private int id;
    private User driver;
    private User passenger;
    private String from;
    private String to;
    private Date time;
    private Double price;
    private String description;
    private int status;

    public static final int OPENED = 0; // the trip is available and ready to serve
    public static final int CLOSED = 1; // the trip is not available due to over date or closed by driver
    public static final int PAUSED = 2; // the trip is still valid (date) but is temporarily hide by driver

    public static final Status[] STATUS = new Status[] {
        new Status(OPENED, "Open"),
        new Status(CLOSED, "Close"),
        new Status(PAUSED, "Pause")
    };

    public static class Status {
        private int id;
        private String name;

        public Status(int id, String name) {
            this.id = id;
            this.name = name;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    public Trip() {
        driver = new User();
        passenger = new User();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getDriver() {
        return driver;
    }

    public void setDriver(User driver) {
        this.driver = driver;
    }

    public User getPassenger() {
        return passenger;
    }

    public void setPassenger(User passenger) {
        this.passenger = passenger;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public String getToDisplay() {
        String to = getTo();
        if (to == null) return "Any where";
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public Date getTime() {
        return time;
    }

    public String getTimeDisplay() {
        Date time = getTime();
        if (time == null) return "Any time";
        return Tool.dateToString(time);
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public Double getPrice() {
        return price;
    }

    public String getPriceDisplay() {
        if (getPrice() == null) return "Free";
        if (getPrice() <= 0) return "Free";
        return price + "";
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
