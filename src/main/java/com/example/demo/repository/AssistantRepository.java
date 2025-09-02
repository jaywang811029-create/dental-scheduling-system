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
        @Query("SELECT a FROM Assistants a JOIN  a.weeklySchedule ws  WHERE ws.MondayWorkTime = :shiftType AND a.region like :region   AND a.totalHours < :totalHours   ")
        List<Assistants> findByMondayWorkTime(
                        @Param("shiftType") String shiftType,
                        @Param("region") String region,
                        @Param("totalHours") Integer totalHours,
                        Sort sort);

        @Query("SELECT a FROM Assistants a JOIN  a.weeklySchedule ws  WHERE ws.TuesdayWorkTime = :shiftType AND a.region like :region  AND a.totalHours < :totalHours    ")
        List<Assistants> findByTuesdayWorkTime(
                        @Param("shiftType") String shiftType,
                        @Param("region") String region,
                        @Param("totalHours") Integer totalHours,
                        Sort sort);

        @Query("SELECT a FROM Assistants a JOIN  a.weeklySchedule ws WHERE ws.WednesdayWorkTime = :shiftType AND a.region like :region  AND a.totalHours < :totalHours    ")
        List<Assistants> findByWednesdayWorkTime(
                        @Param("shiftType") String shiftType,
                        @Param("region") String region,
                        @Param("totalHours") Integer totalHours,
                        Sort sort);

        @Query("SELECT a FROM Assistants a JOIN  a.weeklySchedule ws WHERE ws.ThursdayWorkTime = :shiftType AND a.region like :region  AND a.totalHours < :totalHours    ")
        List<Assistants> findByThursdayWorkTime(
                        @Param("shiftType") String shiftType,
                        @Param("region") String region,
                        @Param("totalHours") Integer totalHours,
                        Sort sort);

        @Query("SELECT a FROM Assistants a JOIN  a.weeklySchedule ws WHERE ws.FridayWorkTime = :shiftType AND a.region like :region  AND a.totalHours < :totalHours    ")
        List<Assistants> findByFridayWorkTime(
                        @Param("shiftType") String shiftType,
                        @Param("region") String region,
                        @Param("totalHours") Integer totalHours,
                        Sort sort);

        @Query("SELECT a FROM Assistants a JOIN  a.weeklySchedule ws WHERE ws.SaturdayWorkTime = :shiftType AND a.region like :region  AND a.totalHours < :totalHours    ")
        List<Assistants> findBySaturdayWorkTime(
                        @Param("shiftType") String shiftType,
                        @Param("region") String region,
                        @Param("totalHours") Integer totalHours,
                        Sort sort);

        List<Assistants> findByRegion(String region, Sort sort);

}
