package com.example.demo.jpa;


import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

@Embeddable
public class LeaveScheduleKey implements Serializable {
    
    
    private String leaveId;
    private LocalDate date;

    // JPA 規範要求必須提供無參數建構子
    public LeaveScheduleKey() {}

    public LeaveScheduleKey(String leaveId, LocalDate date) {
        this.leaveId = leaveId;
        this.date = date;
    }
    
    // 必須實作 equals() 和 hashCode()
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LeaveScheduleKey that = (LeaveScheduleKey) o;
        return Objects.equals(leaveId, that.leaveId) && Objects.equals(date, that.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(leaveId, date);
    }
    
    // 以下為 getter 和 setter
    public String getId() {
        return leaveId;
    }

    public void setId(String leaveId) {
        this.leaveId = leaveId;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "LeaveScheduleKey [leaveId=" + leaveId + ", date=" + date + "]";
    }

    
}