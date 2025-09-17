package com.example.demo.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.jpa.LeaveSchedule;
import com.example.demo.jpa.LeaveScheduleKey;

@Repository
public interface LeaveScheduleRepository extends JpaRepository<LeaveSchedule, LeaveScheduleKey> {

        @Query("SELECT ls FROM LeaveSchedule ls WHERE ls.key.date BETWEEN :startDate AND :endDate AND ls.key.region = :region AND ls.key.leaveId like 'D%' ORDER BY  ls.key.date")
        List<LeaveSchedule> findByLeaveDateBetween(@Param("startDate") LocalDate startDate,
                        @Param("endDate") LocalDate endDate,
                        @Param("region") String region);

        @Query(value = "SELECT COUNT(*) > 0 FROM (" +
                        "SELECT " +
                        "SUM(CASE WHEN LEAVE_ID LIKE 'D%' THEN LENGTH(SHIFT_TYPE) ELSE 0 END) + " +
                        "SUM(CASE WHEN LEAVE_ID LIKE 'A%' THEN 1 ELSE 0 END) AS calculate_work_load " +
                        "FROM LEAVE_SCHEDULE " +
                        "WHERE REGION = :region AND DATE = :targetDate " +
                        "GROUP BY DATE " +
                        "HAVING calculate_work_load > :minWorkload) AS subquery", nativeQuery = true)
        boolean existsByHighWorkloadAndDate(
                        @Param("region") String region,
                        @Param("minWorkload") int minWorkload,
                        @Param("targetDate") LocalDate targetDate);

        @Query("SELECT ls FROM LeaveSchedule ls WHERE ls.key.date = :date AND ls.key.region like :region AND ls.isLeave = :isLeave")
        List<LeaveSchedule> findByKeyDateAndRegionAndIsLeave(
                        @Param("date") LocalDate date,
                        @Param("region") String region,
                        @Param("isLeave") boolean isLeave);

        @Query("SELECT ls FROM LeaveSchedule ls WHERE ls.key.date BETWEEN :startDate AND :endDate AND ls.key.region = :region AND ls.key.leaveId like 'A%' ORDER BY  ls.key.date")
        List<LeaveSchedule> findByAssistanLeaveDate(@Param("startDate") LocalDate startDate,
                        @Param("endDate") LocalDate endDate,
                        @Param("region") String region);

        @Query("SELECT ls FROM LeaveSchedule ls WHERE ls.key.date = :date AND ls.key.leaveId like :leaveId AND ls.isLeave = :isLeave")
        List<LeaveSchedule> deleteLeaveAssistan(
                        @Param("date") LocalDate date,
                        @Param("leaveId") String leaveId,
                        @Param("isLeave") boolean isLeave);

}
