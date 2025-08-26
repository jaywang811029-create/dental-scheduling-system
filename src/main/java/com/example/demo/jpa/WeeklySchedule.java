package com.example.demo.jpa;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "WeeklySchedule")
public class WeeklySchedule {

    @Id
    private String id;

    String name = "";
    String MondayWorkTime = "";
    String TuesdayWorkTime = "";
    String WednesdayWorkTime = "";
    String ThursdayWorkTime = "";
    String FridayWorkTime = "";
    String SaturdayWorkTime = "";
    String SundayWorkTime = "";

    @OneToOne
    @JoinColumn(name = "id", referencedColumnName = "id")
    private Assistants assistant;

    public WeeklySchedule() {
    }

    public WeeklySchedule(String id, String name, String mondayWorkTime, String tuesdayWorkTime,
            String wednesdayWorkTime, String thursdayWorkTime, String fridayWorkTime, String saturdayWorkTime,
            String sundayWorkTime, Assistants assistant) {
        this.id = id;
        this.name = name;
        MondayWorkTime = mondayWorkTime;
        TuesdayWorkTime = tuesdayWorkTime;
        WednesdayWorkTime = wednesdayWorkTime;
        ThursdayWorkTime = thursdayWorkTime;
        FridayWorkTime = fridayWorkTime;
        SaturdayWorkTime = saturdayWorkTime;
        SundayWorkTime = sundayWorkTime;
        this.assistant = assistant;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMondayWorkTime() {
        return MondayWorkTime;
    }

    public void setMondayWorkTime(String mondayWorkTime) {
        MondayWorkTime = mondayWorkTime;
    }

    public String getTuesdayWorkTime() {
        return TuesdayWorkTime;
    }

    public void setTuesdayWorkTime(String tuesdayWorkTime) {
        TuesdayWorkTime = tuesdayWorkTime;
    }

    public String getWednesdayWorkTime() {
        return WednesdayWorkTime;
    }

    public void setWednesdayWorkTime(String wednesdayWorkTime) {
        WednesdayWorkTime = wednesdayWorkTime;
    }

    public String getThursdayWorkTime() {
        return ThursdayWorkTime;
    }

    public void setThursdayWorkTime(String thursdayWorkTime) {
        ThursdayWorkTime = thursdayWorkTime;
    }

    public String getFridayWorkTime() {
        return FridayWorkTime;
    }

    public void setFridayWorkTime(String fridayWorkTime) {
        FridayWorkTime = fridayWorkTime;
    }

    public String getSaturdayWorkTime() {
        return SaturdayWorkTime;
    }

    public void setSaturdayWorkTime(String saturdayWorkTime) {
        SaturdayWorkTime = saturdayWorkTime;
    }

    public String getSundayWorkTime() {
        return SundayWorkTime;
    }

    public void setSundayWorkTime(String sundayWorkTime) {
        SundayWorkTime = sundayWorkTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Assistants getAssistant() {
        return assistant;
    }

    public void setAssistant(Assistants assistant) {
        this.assistant = assistant;
    }

    @Override
    public String toString() {
        return "WeeklySchedule [id=" + id + ", name=" + name + ", MondayWorkTime=" + MondayWorkTime
                + ", TuesdayWorkTime=" + TuesdayWorkTime + ", WednesdayWorkTime=" + WednesdayWorkTime
                + ", ThursdayWorkTime=" + ThursdayWorkTime + ", FridayWorkTime=" + FridayWorkTime
                + ", SaturdayWorkTime=" + SaturdayWorkTime + ", SundayWorkTime=" + SundayWorkTime + "]";
    }

}
