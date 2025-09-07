package com.example.demo.component;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import com.example.demo.jpa.Assistants;
import com.example.demo.repository.AssistantRepository;
import com.example.demo.repository.ResultScheduleRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class AssistantFinder {

    private final AssistantRepository assistantRepository;
    private final ResultScheduleRepository resultScheduleRepository;

    public List<Assistants> findByDayAndShift(LocalDate localDate, String shiftType, Integer totalHours,
            String region) {
        // 是否降冪排序
        Sort defaultSort = region.contains("新竹") ? Sort.by(Sort.Order.desc("precedence"), Sort.Order.asc("totalHours"))
                : Sort.by(Sort.Order.asc("precedence"), Sort.Order.asc("totalHours"));
        return findByDayAndShift(localDate, shiftType, totalHours,
                region, defaultSort);
                
    }

    public List<Assistants> findByDayAndShift(LocalDate localDate, String shiftType, Integer totalHours,
            String region, Sort sort) {

        // 確認其他地區是否有上班
        DayOfWeek dayOfWeek = localDate.getDayOfWeek();
        String checkRegion = "新竹".equals(region) ? "竹北" : "新竹";
        List<Assistants> assistants = new ArrayList<>();

        switch (dayOfWeek) {
            case MONDAY:
                assistants = assistantRepository.findByMondayWorkTime(shiftType, region, totalHours, sort);
                assistants.removeIf(assistant -> resultScheduleRepository.checkRegionNameExist(localDate,
                        checkRegion, "%" + assistant.getName() + "%"));

                return assistants;
            case TUESDAY:
                assistants = assistantRepository.findByTuesdayWorkTime(shiftType, region, totalHours, sort);
                assistants.removeIf(assistant -> resultScheduleRepository.checkRegionNameExist(localDate,
                        checkRegion, "%" + assistant.getName() + "%"));

                return assistants;
            case WEDNESDAY:
                assistants = assistantRepository.findByWednesdayWorkTime(shiftType, region, totalHours, sort);
                assistants.removeIf(assistant -> resultScheduleRepository.checkRegionNameExist(localDate,
                        checkRegion, "%" + assistant.getName() + "%"));

                return assistants;
            case THURSDAY:
                assistants = assistantRepository.findByThursdayWorkTime(shiftType, region, totalHours, sort);
                assistants.removeIf(assistant -> resultScheduleRepository.checkRegionNameExist(localDate,
                        checkRegion, "%" + assistant.getName() + "%"));

                return assistants;
            case FRIDAY:
                assistants = assistantRepository.findByFridayWorkTime(shiftType, region, totalHours, sort);
                assistants.removeIf(assistant -> resultScheduleRepository.checkRegionNameExist(localDate,
                        checkRegion, "%" + assistant.getName() + "%"));

                return assistants;
            case SATURDAY:
                assistants = assistantRepository.findBySaturdayWorkTime(shiftType, region, totalHours, sort);
                assistants.removeIf(assistant -> resultScheduleRepository.checkRegionNameExist(localDate,
                        checkRegion, "%" + assistant.getName() + "%"));

                return assistants;
            default:
                throw new IllegalArgumentException("無效的星期: " + dayOfWeek);
        }
    }

}
