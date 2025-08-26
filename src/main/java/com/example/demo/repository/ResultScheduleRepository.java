package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.jpa.ResultSchedule;
import com.example.demo.jpa.ResultScheduleKey;
import java.util.List;

@Repository
public interface ResultScheduleRepository extends JpaRepository<ResultSchedule, ResultScheduleKey> {

    @Query("SELECT rs FROM ResultSchedule rs WHERE rs.key.region = :region")
    List<ResultSchedule> findByRegion(@Param("region") String region);

    @Query(value = """
            SELECT COUNT(*) FROM RESULT_SCHEDULE
            WHERE (
                CHAIRSIDE_NAME1 LIKE %:searchString% OR
                CHAIRSIDE_NAME2 LIKE %:searchString% OR
                CHAIRSIDE_NAME3 LIKE %:searchString% OR
                FLOATER_NAME1 LIKE %:searchString% OR
                FLOATER_NAME2 LIKE %:searchString% OR
                FLOATER_NAME3 LIKE %:searchString% OR
                FRONT_DESK1 LIKE %:searchString% OR
                FRONT_DESK2 LIKE %:searchString% OR
                FRONT_DESK3 LIKE %:searchString%
            ) AND date LIKE :targetDate
            """, nativeQuery = true)
    Long countByIsAllDay(@Param("searchString") String searchString, @Param("targetDate") String targetDate);

}
