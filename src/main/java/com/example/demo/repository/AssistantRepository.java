package com.example.demo.repository;

import java.time.LocalDate;
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

        @Query(value = """
                        SELECT a.*
                        FROM ASSISTANTS a
                        LEFT JOIN (
                            SELECT CHAIRSIDE_NAME1 AS name
                            FROM RESULT_SCHEDULE
                            WHERE DATE = :date AND CHAIRSIDE_NAME1 IS NOT NULL
                            UNION
                            SELECT CHAIRSIDE_NAME2
                            FROM RESULT_SCHEDULE
                            WHERE DATE = :date AND CHAIRSIDE_NAME2 IS NOT NULL
                            UNION
                            SELECT CHAIRSIDE_NAME3
                            FROM RESULT_SCHEDULE
                            WHERE DATE = :date AND CHAIRSIDE_NAME3 IS NOT NULL
                            UNION
                            SELECT FLOATER_NAME1
                            FROM RESULT_SCHEDULE
                            WHERE DATE = :date AND FLOATER_NAME1 IS NOT NULL
                            UNION
                            SELECT FLOATER_NAME2
                            FROM RESULT_SCHEDULE
                            WHERE DATE = :date AND FLOATER_NAME2 IS NOT NULL
                            UNION
                            SELECT FLOATER_NAME3
                            FROM RESULT_SCHEDULE
                            WHERE DATE = :date AND FLOATER_NAME3 IS NOT NULL
                            UNION
                            SELECT FRONT_DESK1
                            FROM RESULT_SCHEDULE
                            WHERE DATE = :date AND FRONT_DESK1 IS NOT NULL
                            UNION
                            SELECT FRONT_DESK2
                            FROM RESULT_SCHEDULE
                            WHERE DATE = :date AND FRONT_DESK2 IS NOT NULL
                            UNION
                            SELECT FRONT_DESK3
                            FROM RESULT_SCHEDULE
                            WHERE DATE = :date AND FRONT_DESK3 IS NOT NULL
                        ) scheduled_assistants
                        ON a.NAME = scheduled_assistants.name
                        WHERE scheduled_assistants.name IS NULL
                        AND a.region like :region
                        """, nativeQuery = true)
        List<Assistants> findUnscheduledAssistants(@Param("date") LocalDate date,@Param("region") String region);

        List<Assistants> findByRegion(String region, Sort sort);

}
