package com.example.demo.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ScheduleDTO {

    private String date; // 日期
    private List<String> doctors; // 醫師
    private List<String> frontDesks = new ArrayList<>(); // 櫃台
    private List<String> chairsides = new ArrayList<>();; // 跟診助理
    private List<String> floaters = new ArrayList<>();; // 流動
    private Map<Integer, String> suppors = new HashMap<>(); // 不熟新人
    // 上全的人
    //

    public ScheduleDTO() {
    }

    public ScheduleDTO(String date, List<String> doctors, List<String> frontDesks, List<String> chairsides,
            List<String> floaters, Map<Integer, String> suppors) {
        this.date = date;
        this.doctors = doctors;
        this.frontDesks = frontDesks;
        this.chairsides = chairsides;
        this.floaters = floaters;
        this.suppors = suppors;
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

    public Map<Integer, String> getSuppors() {
        return suppors;
    }

    public void setSuppors(Map<Integer, String> suppors) {
        this.suppors = suppors;
    }

    @Override
    public String toString() {
        return "ScheduleDTO [date=" + date + ", doctors=" + doctors + ", frontDesks=" + frontDesks + ", chairsides="
                + chairsides + ", floaters=" + floaters + ", suppors=" + suppors + "]";
    }

}
