package com.example.demo.jpa;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "Assistants")
public class Assistants {

    @Id
    private String id;

    private String name;
    private Integer frontDeskCount = 0;
    private Integer floaterCount = 0;
    private Integer chairsideCount = 0;
    private boolean isFrontDesk;
    private boolean isFloater;
    private boolean isChairside;
    private String region;
    private Integer totalHours = 0;
    private Integer boosHours = 0;
    private Integer precedence = 0;

    // 添加 OneToOne 關聯
    @OneToOne(mappedBy = "assistant")
    private WeeklySchedule weeklySchedule;

    public Assistants(String id, String name, Integer frontDeskCount, Integer floaterCount, Integer chairsideCount,
            boolean isFrontDesk, boolean isFloater, boolean isChairside, String region, Integer totalHours,
            Integer boosHours, Integer precedence, WeeklySchedule weeklySchedule) {
        this.id = id;
        this.name = name;
        this.frontDeskCount = frontDeskCount;
        this.floaterCount = floaterCount;
        this.chairsideCount = chairsideCount;
        this.isFrontDesk = isFrontDesk;
        this.isFloater = isFloater;
        this.isChairside = isChairside;
        this.region = region;
        this.totalHours = totalHours;
        this.boosHours = boosHours;
        this.precedence = precedence;
        this.weeklySchedule = weeklySchedule;
    }

    public WeeklySchedule getWeeklySchedule() {
        return weeklySchedule;
    }

    public void setWeeklySchedule(WeeklySchedule weeklySchedule) {
        this.weeklySchedule = weeklySchedule;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isFrontDesk() {
        return isFrontDesk;
    }

    public void setFrontDesk(boolean isFrontDesk) {
        this.isFrontDesk = isFrontDesk;
    }

    public boolean isFloater() {
        return isFloater;
    }

    public void setFloater(boolean isFloater) {
        this.isFloater = isFloater;
    }

    public boolean isChairside() {
        return isChairside;
    }

    public void setChairside(boolean isChairside) {
        this.isChairside = isChairside;
    }

    public Integer getTotalHours() {
        return totalHours;
    }

    public void setTotalHours(Integer totalHours) {
        this.totalHours = totalHours;
    }

    public Integer getBoosHours() {
        return boosHours;
    }

    public void setBoosHours(Integer boosHours) {
        this.boosHours = boosHours;
    }

    public Assistants() {
    }

    public Integer getPrecedence() {
        return precedence;
    }

    public void setPrecedence(Integer precedence) {
        this.precedence = precedence;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public Integer getFrontDeskCount() {
        return frontDeskCount;
    }

    public void setFrontDeskCount(Integer frontDeskCount) {
        this.frontDeskCount = frontDeskCount;
    }

    public Integer getFloaterCount() {
        return floaterCount;
    }

    public void setFloaterCount(Integer floaterCount) {
        this.floaterCount = floaterCount;
    }

    public Integer getChairsideCount() {
        return chairsideCount;
    }

    public void setChairsideCount(Integer chairsideCount) {
        this.chairsideCount = chairsideCount;
    }

    @Override
    public String toString() {
        return "Assistants [id=" + id + ", name=" + name + ", frontDeskCount=" + frontDeskCount + ", floaterCount="
                + floaterCount + ", chairsideCount=" + chairsideCount + ", isFrontDesk=" + isFrontDesk + ", isFloater="
                + isFloater + ", isChairside=" + isChairside + ", region=" + region + ", totalHours=" + totalHours
                + ", boosHours=" + boosHours + ", precedence=" + precedence + ", weeklySchedule=" + weeklySchedule
                + "]";
    }

}
