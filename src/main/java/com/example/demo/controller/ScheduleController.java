package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Enum.Event;
import com.example.demo.component.ScheduleFormatter;
import com.example.demo.component.ShiftTimeProvider;
import com.example.demo.jpa.ResultSchedule;
import com.example.demo.service.ScheduleService;

@CrossOrigin(origins = "http://127.0.0.1:5500")
@RestController
@RequestMapping("/")
public class ScheduleController {

        private final ScheduleService service;
        private final ScheduleFormatter formatter;

        public ScheduleController(ScheduleService service, ScheduleFormatter formatter) {
                this.service = service;
                this.formatter = formatter;
        }

        @GetMapping("/event")
        public List<Event> getEvents(@RequestParam(defaultValue = "新竹") String region) {
                List<Event> events = new ArrayList<>();

                //執行排班功能
                service.doWork(region);

                //根據區域查詢排班資料庫
                List<ResultSchedule> resutlList = service.doQuery(region);
                
                resutlList.stream()
                .map(result -> new Event(
                        formatter.format2(result.getKey().getShiftType(), result),
                        ShiftTimeProvider.getStart(result.getKey().getDate(), result.getKey().getShiftType()),
                        ShiftTimeProvider.getEnd(result.getKey().getDate(), result.getKey().getShiftType()),
                        List.of("noon")
                ))
                .forEach(events::add);

                // schedule.forEach((date, shifts) -> {
                //         shifts.forEach((shift, dto) -> {
                //                 String title = formatter.format(shift, dto);
                //                 String start = ShiftTimeProvider.getStart(date, shift);
                //                 String end = ShiftTimeProvider.getEnd(date, shift);

                //                 events.add(new Event(title, start, end, List.of("noon")));
                //         });
                // });

                return events;
        }

}