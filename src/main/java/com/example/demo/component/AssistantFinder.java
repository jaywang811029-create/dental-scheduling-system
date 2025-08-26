package com.example.demo.component;

import java.time.DayOfWeek;
import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import com.example.demo.jpa.Assistants;
import com.example.demo.repository.AssistantRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class AssistantFinder {

    private final AssistantRepository assistantRepository;

    public List<Assistants> findByDayAndShift(DayOfWeek dayOfWeek, String shiftType, String region) {


        //是否降冪排序
        Sort sort= region.contains("新竹") ?  Sort.by(Sort.Order.desc("precedence"),Sort.Order.asc("totalHours") )
        : Sort.by(Sort.Order.asc("precedence"),Sort.Order.asc("totalHours") );
    

        switch (dayOfWeek) {
            case MONDAY:
                return assistantRepository.findByMondayWorkTime(shiftType, region, sort);
            case TUESDAY:
                return assistantRepository.findByTuesdayWorkTime(shiftType, region, sort);
            case WEDNESDAY:
                return assistantRepository.findByWednesdayWorkTime(shiftType, region, sort);
            case THURSDAY:
                return assistantRepository.findByThursdayWorkTime(shiftType, region, sort);
            case FRIDAY:
                return assistantRepository.findByFridayWorkTime(shiftType, region, sort);
            case SATURDAY:
                return assistantRepository.findBySaturdayWorkTime(shiftType, region, sort);
            default:
                throw new IllegalArgumentException("無效的星期: " + dayOfWeek);
        }
    }
}
