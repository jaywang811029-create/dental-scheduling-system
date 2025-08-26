package com.example.demo.jpa;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "LeaveSchedule")
public class LeaveSchedule {

    @EmbeddedId
    private LeaveScheduleKey key;

    private String name;
    private String shiftType;
    private boolean isLeave;
    private String region;


    

    public LeaveSchedule(LeaveScheduleKey key, String name, String shiftType, boolean isLeave, String region) {
        this.key = key;
        this.name = name;
        this.shiftType = shiftType;
        this.isLeave = isLeave;
        this.region = region;
    }

    // JPA 規範要求必須提供無參數建構子
    public LeaveSchedule() {
    }

    public LeaveScheduleKey getKey() {
        return key;
    }

    public void setKey(LeaveScheduleKey key) {
        this.key = key;
    }

    public String getShiftType() {
        return shiftType;
    }

    public void setShiftType(String shiftType) {
        this.shiftType = shiftType;
    }

    public boolean isLeave() {
        return isLeave;
    }

    public void setLeave(boolean isLeave) {
        this.isLeave = isLeave;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }




    @Override
    public String toString() {
        return "LeaveSchedule [key=" + key + ", name=" + name + ", shiftType=" + shiftType + ", isLeave=" + isLeave
                + ", region=" + region + "]";
    }

    

}