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

@Query("SELECT ls FROM LeaveSchedule ls WHERE ls.key.date BETWEEN :startDate AND :endDate AND ls.region = :region AND ls.key.leaveId like 'D%' ORDER BY  ls.key.date")
    List<LeaveSchedule> findByLeaveDateBetween(@Param("startDate") LocalDate startDate, 
                                               @Param("endDate") LocalDate endDate,
                                               @Param("region") String region);


    
}
