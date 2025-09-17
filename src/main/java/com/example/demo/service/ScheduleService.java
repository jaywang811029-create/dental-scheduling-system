package com.example.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.Enum.Event;
import com.example.demo.dto.ScheduleViewDTO;

@Service
public interface ScheduleService {

    /**
     * 排班邏輯
     * @param region 地區
     * @apiNote 先從新竹排完再排竹北
     */
    public void doWork(String region);

    /**
     * 查詢班表
     * @param startDate 日期起
     * @param endDate 日期迄
     * @param region 地區
     */
    public List<ScheduleViewDTO>  doQuerySchedules(String startDate,String endDate,String region);

    /**
     * 查詢當天休假員工
     * @param startDate 日期起
     * @param endDate 日期迄
     * @param region 地區
     */
     public List<Event> doQueryLeave(String startDate, String endDate, String region);






    

}
