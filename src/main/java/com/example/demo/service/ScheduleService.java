package com.example.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.jpa.ResultSchedule;

@Service
public interface ScheduleService {

    public void doWork(String region);

    public List<ResultSchedule> doQuery(String region);






    

}
