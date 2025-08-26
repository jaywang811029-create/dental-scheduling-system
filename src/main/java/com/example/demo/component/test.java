package com.example.demo.component;

import java.util.List;

import com.example.demo.Enum.ShiftEnum;
import com.example.demo.controller.ScheduleDTO;
import com.example.demo.jpa.Assistants;
import com.example.demo.repository.AssistantRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class test {

    private AssistantRepository assistantRepository;

    /**
     * 單診排班邏輯，目前單診只有晚班
     */
    public void singleSchedulingLogic(
            int[] nightWorkArray, // 晚班崗位需求 [櫃台, 跟診, 流動]
            List<Assistants> assistants,
            ScheduleDTO nightDTO // 晚班班表
    ) {
        for (Assistants assistant : assistants) {
            if (assistant.isFrontDesk() && nightWorkArray[0] > 0) {
                assignShift(assistant, 0, nightWorkArray, null, nightDTO);
            } else if (assistant.isChairside() && nightWorkArray[1] > 0) {
                assignShift(assistant, 1, nightWorkArray, null, nightDTO);
            } else if (assistant.isFloater() && nightWorkArray[2] > 0) {
                assignShift(assistant, 2, nightWorkArray, null, nightDTO);
            }
        }
    }

    /**
     * 雙診排班邏輯 (早+午連班)
     */
    public int[] evenSchedulingLogic(
            String shiftType,
            int[] ampmWorkArray, // 早午合併班的崗位需求 [櫃台, 跟診, 流動]
            int[] amWorkArray, // 早班的崗位需求 (判斷跟診人數用)
            List<Assistants> assistants,
            ScheduleDTO amDTO, // 早班班表
            ScheduleDTO pmDTO, // 午班班表
            ScheduleDTO nightDTO // 晚班班表 (雙診不使用，但保留參數)
    ) {
        System.out.println(amDTO.getDate() + " workArray=" +
                ampmWorkArray[0] + ampmWorkArray[1] + ampmWorkArray[2] + "," + amWorkArray[2]);

        for (Assistants assistant : assistants) {
            // 櫃台：早午各一
            if (assistant.isFrontDesk() && ampmWorkArray[0] >= 2) {
                assignShift(assistant, 0, ampmWorkArray, null, amDTO, pmDTO);

                // 跟診：要檢查早班數量是否足夠
            } else if (assistant.isChairside() && ampmWorkArray[1] >= 2
                    && amDTO.getChairsides().size() < amWorkArray[1]) {
                assignShift(assistant, 1, ampmWorkArray, amWorkArray, amDTO, pmDTO);

                // 流動：早午各一
            } else if (assistant.isFloater() && ampmWorkArray[2] >= 2) {
                assignShift(assistant, 2, ampmWorkArray, null, amDTO, pmDTO);
            }
        }
        return ampmWorkArray;
    }

    /**
     * 將助理排入指定班別
     *
     * @param assistant
     *            助理物件
     * @param roleIndex
     *            崗位索引 (0=FrontDesk, 1=Chairside, 2=Floater)
     * @param workArray
     *            當前班別的崗位需求陣列 (早午合併或晚班)
     * @param amWorkArray
     *            早班需求 (只有連班的跟診需要檢查，其他情境可傳 null)
     * @param scheduleDTOs
     *            要更新的班表 (可傳 1 個或多個，例如早+午)
     */
    private void assignShift(
            Assistants assistant,
            int roleIndex,
            int[] workArray,
            int[] amWorkArray,
            ScheduleDTO... scheduleDTOs) {
        // 判斷是否為「早午連班」：有兩個班表就是連班
        boolean isDoubleShift = (scheduleDTOs.length == 2);

        // 扣除需求數量：連班扣 2，單班扣 1
        workArray[roleIndex] -= (isDoubleShift ? 2 : 1);

        // 加入班表
        for (ScheduleDTO dto : scheduleDTOs) {
            switch (roleIndex) {
                case 0 -> dto.getFrontDesks().add(assistant.getName());
                case 1 -> dto.getChairsides().add(assistant.getName());
                case 2 -> dto.getFloaters().add(assistant.getName());
            }
        }

        // 計算應增加的工時
        int hoursToAdd = isDoubleShift
                ? ShiftEnum.MORNING_AFTERNOON.getHours()
                : ShiftEnum.NIGHT.getHours();

        assistant.setTotalHours(assistant.getTotalHours() + hoursToAdd);

        // 存回資料庫
        assistantRepository.save(assistant);
    }
}
