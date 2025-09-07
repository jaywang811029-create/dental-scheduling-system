package com.example.demo.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.component.ScheduleFormatter;
import com.example.demo.dto.ScheduleViewDTO;
import com.example.demo.dto.ShiftDetail;
import com.example.demo.jpa.ResultSchedule;
import com.example.demo.repository.ResultScheduleRepository;
import com.example.demo.service.ScheduleService;

@CrossOrigin(origins = "http://127.0.0.1:5500")
@RestController
@RequestMapping("/")
public class ScheduleController {

        private final ScheduleService service;
        private final ScheduleFormatter formatter;
        private final ResultScheduleRepository resultScheduleRepository;

        public ScheduleController(ScheduleService service, ScheduleFormatter formatter,
                        ResultScheduleRepository resultScheduleRepository) {
                this.service = service;
                this.formatter = formatter;
                this.resultScheduleRepository = resultScheduleRepository;
        }



        @GetMapping("/event")
        public ResponseEntity<Void> getEvents() {
      
                        // 執行排班功能
                service.doWork("新竹");
                service.doWork("竹北");
                // 返回 204 No Content 表示操作成功，無需返回資料
                return ResponseEntity.noContent().build();

        }

        @GetMapping("/api/schedules")
        public List<ScheduleViewDTO> getSchedules(
                        @RequestParam("startDate") String startDate,
                        @RequestParam("endDate") String endDate,
                        @RequestParam("region") String region) {

                LocalDate start = LocalDate.parse(startDate);
                LocalDate end = LocalDate.parse(endDate);

                // 查詢指定日期範圍和地區的排班資料
                List<ResultSchedule> schedules = resultScheduleRepository.findByKeyDateBetweenAndKeyRegion(
                                start, end, region);

                // 按日期分組並轉換為 DTO
                Map<LocalDate, List<ResultSchedule>> groupedByDate = schedules.stream()
                                .collect(Collectors.groupingBy(schedule -> schedule.getKey().getDate()));

                List<ScheduleViewDTO> result = new ArrayList<>();
                for (LocalDate date : groupedByDate.keySet()) {
                        ScheduleViewDTO dto = new ScheduleViewDTO();
                        dto.setDate(date);
                        dto.setRegion(region);


                        // 根據 shiftType 分配資料
                        for (ResultSchedule schedule : groupedByDate.get(date)) {
                                ShiftDetail shiftDetail = new ShiftDetail();
                                shiftDetail.setFrontDesk1(schedule.getFrontDesk1());
                                shiftDetail.setChairsideName1(schedule.getChairsideName1());
                                shiftDetail.setDoctorName1(schedule.getDoctorName1());
                                shiftDetail.setChairsideName2(schedule.getChairsideName2());
                                shiftDetail.setDoctorName2(schedule.getDoctorName2());
                                shiftDetail.setChairsideName3(schedule.getChairsideName3());
                                shiftDetail.setDoctorName3(schedule.getDoctorName3());
                                shiftDetail.setFloaterName1(schedule.getFloaterName1());
                                shiftDetail.setFloaterName2(schedule.getFloaterName2());

                                switch (schedule.getKey().getShiftType()) {
                                        case "早":
                                                dto.setMorning(shiftDetail);
                                                break;
                                        case "午":
                                                dto.setNoon(shiftDetail);
                                                break;
                                        case "晚":
                                                dto.setNight(shiftDetail);
                                                break;
                                }
                        }

                        // 如果某班次無資料，設置預設值
                        if (dto.getMorning() == null)
                                dto.setMorning(new ShiftDetail());
                        if (dto.getNoon() == null)
                                dto.setNoon(new ShiftDetail());
                        if (dto.getNight() == null)
                                dto.setNight(new ShiftDetail());

                        result.add(dto);
                }

                return result;
        }

}

                // 根據區域查詢排班資料庫
                // List<ResultSchedule> resutlList = service.doQuery(region);

                // resutlList.stream()
                //                 .map(result -> new Event(
                //                                 formatter.format2(result.getKey().getShiftType(), result),
                //                                 ShiftTimeProvider.getStart(result.getKey().getDate(),
                //                                                 result.getKey().getShiftType()),
                //                                 ShiftTimeProvider.getEnd(result.getKey().getDate(),
                //                                                 result.getKey().getShiftType()),
                //                                 List.of("noon")))
                //                 .forEach(events::add);

                // return events;