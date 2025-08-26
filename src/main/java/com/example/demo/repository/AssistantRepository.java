package com.example.demo.repository;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.jpa.Assistants;

@Repository
public interface AssistantRepository extends JpaRepository<Assistants, String> {
    @Query("SELECT a FROM Assistants a JOIN  a.weeklySchedule ws  WHERE ws.MondayWorkTime = :shiftType AND a.region like :region   AND a.totalHours < 170 ")
    // @Query("SELECT a FROM Assistants a JOIN WeeklySchedule ws ON a.id = ws.id LEFT JOIN LeaveSchedule ls ON a.id = ls.assistant.id AND ls.isLeave <> true WHERE ls.assistant IS NULL AND ws.MondayWorkTime = :shiftType AND a.region like :region   AND a.totalHours < 170")
    List<Assistants> findByMondayWorkTime(
        @Param("shiftType") String shiftType,
        @Param("region") String region, 
        Sort sort);

    @Query("SELECT a FROM Assistants a JOIN  a.weeklySchedule ws  WHERE ws.TuesdayWorkTime = :shiftType AND a.region like :region  AND a.totalHours < 170 ")
    //  @Query("SELECT a FROM Assistants a JOIN WeeklySchedule ws ON a.id = ws.id LEFT JOIN LeaveSchedule ls ON a.id = ls.assistant.id AND ls.isLeave <> true WHERE ls.assistant IS NULL AND ws.TuesdayWorkTime = :shiftType AND a.region like :region   AND a.totalHours < 170")
    List<Assistants> findByTuesdayWorkTime(
        @Param("shiftType")String shiftType,
        @Param("region") String region, 
        Sort sort);

    @Query("SELECT a FROM Assistants a JOIN  a.weeklySchedule ws WHERE ws.WednesdayWorkTime = :shiftType AND a.region like :region  AND a.totalHours < 170 ")
    // @Query("SELECT a FROM Assistants a JOIN WeeklySchedule ws ON a.id = ws.id LEFT JOIN LeaveSchedule ls ON a.id = ls.assistant.id AND ls.isLeave <> true WHERE ls.assistant IS NULL AND ws.WednesdayWorkTime = :shiftType AND a.region like :region   AND a.totalHours < 170")
    List<Assistants> findByWednesdayWorkTime(
        @Param("shiftType") String shiftType,
        @Param("region") String region, 
        Sort sort);

    @Query("SELECT a FROM Assistants a JOIN  a.weeklySchedule ws WHERE ws.ThursdayWorkTime = :shiftType AND a.region like :region  AND a.totalHours < 170 ")
    // @Query("SELECT a FROM Assistants a JOIN WeeklySchedule ws ON a.id = ws.id LEFT JOIN LeaveSchedule ls ON a.id = ls.assistant.id AND ls.isLeave <> true WHERE ls.assistant IS NULL AND ws.ThursdayWorkTime = :shiftType AND a.region like :region   AND a.totalHours < 170")
    List<Assistants> findByThursdayWorkTime(
        @Param("shiftType") String shiftType,
        @Param("region") String region, 
        Sort sort);

    @Query("SELECT a FROM Assistants a JOIN  a.weeklySchedule ws WHERE ws.FridayWorkTime = :shiftType AND a.region like :region  AND a.totalHours < 170 ")
    // @Query("SELECT a FROM Assistants a JOIN WeeklySchedule ws ON a.id = ws.id LEFT JOIN LeaveSchedule ls ON a.id = ls.assistant.id AND ls.isLeave <> true WHERE ls.assistant IS NULL AND ws.FridayWorkTime = :shiftType AND a.region like :region   AND a.totalHours < 170")
    List<Assistants> findByFridayWorkTime(
        @Param("shiftType") String shiftType,
        @Param("region") String region, 
        Sort sort);

    @Query("SELECT a FROM Assistants a JOIN  a.weeklySchedule ws WHERE ws.SaturdayWorkTime = :shiftType AND a.region like :region  AND a.totalHours < 170 ")
    // @Query("SELECT a FROM Assistants a JOIN WeeklySchedule ws ON a.id = ws.id LEFT JOIN LeaveSchedule ls ON a.id = ls.assistant.id AND ls.isLeave <> true WHERE ls.assistant IS NULL AND ws.SaturdayWorkTime = :shiftType AND a.region like :region   AND a.totalHours < 170")
    List<Assistants> findBySaturdayWorkTime(
        @Param("shiftType") String shiftType,
        @Param("region") String region, 
        Sort sort);

    List<Assistants> findByRegion(String region);

}
