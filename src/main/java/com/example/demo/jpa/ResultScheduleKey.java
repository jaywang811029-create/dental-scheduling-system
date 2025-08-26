package com.example.demo.jpa;

import java.time.LocalDate;

import jakarta.persistence.Embeddable;

@Embeddable
public class ResultScheduleKey {

    private LocalDate date;
    private String shiftType;
    private String region;

    public ResultScheduleKey() {
    }


    public ResultScheduleKey(LocalDate date, String shiftType, String region) {
        this.date = date;
        this.shiftType = shiftType;
        this.region = region;
    }


    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getShiftType() {
        return shiftType;
    }

    public void setShiftType(String shiftType) {
        this.shiftType = shiftType;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    @Override
    public String toString() {
        return "ResultScheduleKey [date=" + date + ", shiftType=" + shiftType + ", region=" + region + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((date == null) ? 0 : date.hashCode());
        result = prime * result + ((shiftType == null) ? 0 : shiftType.hashCode());
        result = prime * result + ((region == null) ? 0 : region.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        ResultScheduleKey other = (ResultScheduleKey) obj;
        if (date == null) {
            if (other.date != null)
                return false;
        } else if (!date.equals(other.date))
            return false;
        if (shiftType == null) {
            if (other.shiftType != null)
                return false;
        } else if (!shiftType.equals(other.shiftType))
            return false;
        if (region == null) {
            if (other.region != null)
                return false;
        } else if (!region.equals(other.region))
            return false;
        return true;
    }

}
