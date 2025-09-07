package com.example.demo.dto;

import java.time.LocalDate;

public class ScheduleViewDTO {

    private LocalDate date;
    private String region;
    private ShiftDetail morning;
    private ShiftDetail noon;
    private ShiftDetail night;

    // Getters and Setters
    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public ShiftDetail getMorning() {
        return morning;
    }

    public void setMorning(ShiftDetail morning) {
        this.morning = morning;
    }

    public ShiftDetail getNoon() {
        return noon;
    }

    public void setNoon(ShiftDetail noon) {
        this.noon = noon;
    }

    public ShiftDetail getNight() {
        return night;
    }

    public void setNight(ShiftDetail night) {
        this.night = night;
    }

}
