package com.example.demo.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Enum.Event;
import com.example.demo.dto.ScheduleViewDTO;
import com.example.demo.service.ScheduleService;

@CrossOrigin(origins = "http://127.0.0.1:5500")
@RestController
@RequestMapping("/")
public class ScheduleController {

        private final ScheduleService service;

        public ScheduleController(ScheduleService service) {
                this.service = service;
        }

        @GetMapping("/builder")
        public ResponseEntity<?> doWork() {

                // 執行排班功能
                service.doWork("新竹");
                service.doWork("竹北");
                // 返回 204 No Content 表示操作成功，無需返回資料
                return ResponseEntity.noContent().build();

        }

        @GetMapping("/leave")
        public List<Event> doQueryLeave(
                        @RequestParam("startDate") String startDate,
                        @RequestParam("endDate") String endDate,
                        @RequestParam("region") String region) {

                return service.doQueryLeave(startDate, endDate, region);
        }

        @GetMapping("/api/schedules") 
        public List<ScheduleViewDTO> doQuerySchedules(
                        @RequestParam("startDate") String startDate,
                        @RequestParam("endDate") String endDate,
                        @RequestParam("region") String region) {

                List<ScheduleViewDTO> result = service.doQuerySchedules(startDate, endDate, region);
                return result;

        }

}

// 根據區域查詢排班資料庫
// List<ResultSchedule> resutlList = service.doQuery(region);

// resutlList.stream()
// .map(result -> new Event(
// formatter.format2(result.getKey().getShiftType(), result),
// ShiftTimeProvider.getStart(result.getKey().getDate(),
// result.getKey().getShiftType()),
// ShiftTimeProvider.getEnd(result.getKey().getDate(),
// result.getKey().getShiftType()),
// List.of("noon")))
// .forEach(events::add);