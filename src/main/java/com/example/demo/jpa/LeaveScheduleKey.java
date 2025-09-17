package com.example.demo.jpa;

import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

@Embeddable
public class LeaveScheduleKey implements Serializable {

    private String leaveId;
    private LocalDate date;
    private String region;

    // JPA 規範要求必須提供無參數建構子
    public LeaveScheduleKey() {
    }

    public LeaveScheduleKey(String leaveId, LocalDate date, String region) {
        this.leaveId = leaveId;
        this.date = date;
        this.region = region;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((leaveId == null) ? 0 : leaveId.hashCode());
        result = prime * result + ((date == null) ? 0 : date.hashCode());
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
        LeaveScheduleKey other = (LeaveScheduleKey) obj;
        if (leaveId == null) {
            if (other.leaveId != null)
                return false;
        } else if (!leaveId.equals(other.leaveId))
            return false;
        if (date == null) {
            if (other.date != null)
                return false;
        } else if (!date.equals(other.date))
            return false;
        if (region == null) {
            if (other.region != null)
                return false;
        } else if (!region.equals(other.region))
            return false;
        return true;
    }

    public String getLeaveId() {
        return leaveId;
    }

    public void setLeaveId(String leaveId) {
        this.leaveId = leaveId;
    }

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

    @Override
    public String toString() {
        return "LeaveScheduleKey [leaveId=" + leaveId + ", date=" + date + ", region=" + region + "]";
    }

}