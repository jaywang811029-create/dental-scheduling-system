package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;

public class ScheduleDTO {

    private String date; // 日期
    private List<String> doctors; // 醫師
    private List<String> frontDesks = new ArrayList<>(); // 櫃台
    private List<String> chairsides = new ArrayList<>();; // 跟診助理
    private List<String> floaters = new ArrayList<>();; // 流動
    //上全的人
    //

    public ScheduleDTO() {
    }

    public ScheduleDTO(String date, List<String> doctors, List<String> frontDesks, List<String> chairsides,
            List<String> floaters) {
        this.date = date;
        this.doctors = doctors;
        this.frontDesks = frontDesks;
        this.chairsides = chairsides;
        this.floaters = floaters;
    }

    public List<String> getDoctors() {
        return doctors;
    }

    public void setDoctors(List<String> doctors) {
        this.doctors = doctors;
    }

    public List<String> getFrontDesks() {
        return frontDesks;
    }

    public void setFrontDesks(List<String> frontDesks) {
        this.frontDesks = frontDesks;
    }

    public List<String> getChairsides() {
        return chairsides;
    }

    public void setChairsides(List<String> chairsides) {
        this.chairsides = chairsides;
    }

    public List<String> getFloaters() {
        return floaters;
    }

    public void setFloaters(List<String> floaters) {
        this.floaters = floaters;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "ScheduleDTO [date=" + date + ", doctors=" + doctors + ", frontDesks=" + frontDesks + ", chairsides="
                + chairsides + ", floaters=" + floaters + "]";
    }

}
