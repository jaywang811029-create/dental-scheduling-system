package com.example.demo.component;

import java.util.List;

import org.springframework.stereotype.Component;

import com.example.demo.Enum.ShiftEnum;
import com.example.demo.controller.ScheduleDTO;
import com.example.demo.jpa.Assistants;
import com.example.demo.repository.AssistantRepository;
import com.example.demo.repository.ResultScheduleRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ScheduleCalculator {

    private final AssistantRepository assistantRepository;
    private final ResultScheduleRepository resultScheduleRepository;

    public void deleteAllHours() {
        List<Assistants> assistants = assistantRepository.findAll();

        for (Assistants assistant : assistants) {
            assistant.setTotalHours(0);
            assistantRepository.save(assistant);

        }
    }

    /*
     * 早午排班邏輯
     */
    public int[] evenSchedulingLogic(int[] ampmWorkArray, int[] amWorkArray,
            List<Assistants> assistants, ScheduleDTO amDTO,
            ScheduleDTO pmDTO,
            ScheduleDTO nightDTO) {

        System.out.println(amDTO.getDate() + " workArray=" + ampmWorkArray[0] + ampmWorkArray[1] + ampmWorkArray[2]
                + "," + amWorkArray[2]);

        for (Assistants assistant : assistants) {
            // 如果是會櫃台，剛好又沒排人直接給他上櫃台
            if (assistant.isFrontDesk() && ampmWorkArray[0] >= 2) {

                amDTO.getFrontDesks().add(assistant.getName());// 早班
                pmDTO.getFrontDesks().add(assistant.getName());// 午班
                ampmWorkArray[0] = ampmWorkArray[0] - 2;// 扣除崗位

                assistant.setTotalHours(assistant.getTotalHours() + ShiftEnum.MORNING_AFTERNOON.getHours());// 加總時數
                assistantRepository.save(assistant);
            } else if (assistant.isChairside() && ampmWorkArray[1] >= 2
                    && amDTO.getChairsides().size() < amWorkArray[1]) {
                // 若不會櫃台會跟診就去跟診

                amDTO.getChairsides().add(assistant.getName());
                pmDTO.getChairsides().add(assistant.getName());
                ampmWorkArray[1] = ampmWorkArray[1] - 2;

                assistant.setTotalHours(assistant.getTotalHours() + ShiftEnum.MORNING_AFTERNOON.getHours());
                assistantRepository.save(assistant);
            } else if (assistant.isFloater() && ampmWorkArray[2] >= 2) {
                // 如果櫃台、跟診都不會就去流動
                amDTO.getFloaters().add(assistant.getName());
                pmDTO.getFloaters().add(assistant.getName());
                ampmWorkArray[2] = ampmWorkArray[2] - 2;

                assistant.setTotalHours(assistant.getTotalHours() + ShiftEnum.MORNING_AFTERNOON.getHours());
                assistantRepository.save(assistant);
            }

        }
        return ampmWorkArray;
    }

    /*
     * 單診排班邏輯，目前單診只有晚班，暫定先排晚班邏輯
     */
    public void singleSchedulingLogic(
            int[] ampmArray,
            int[] pmNightArray,
            int[] nightWorkArray,
            List<Assistants> assistants,
            ScheduleDTO nightDTO) {

        pmNightArray[0] = ampmArray[0] + nightWorkArray[0];
        pmNightArray[1] = ampmArray[1] + nightWorkArray[1];
        pmNightArray[2] = ampmArray[2] + nightWorkArray[2];

        for (Assistants assistant : assistants) {

            // 如果是會櫃台，剛好又沒排人直接給他上櫃台
            if (assistant.isFrontDesk() && nightWorkArray[0] > 0) {
                nightDTO.getFrontDesks().add(assistant.getName());// 晚班
                pmNightArray[0] = pmNightArray[0] - 1;
                assistant.setTotalHours(assistant.getTotalHours() + ShiftEnum.NIGHT.getHours());
                assistantRepository.save(assistant);
            } else if (assistant.isChairside() && nightWorkArray[1] > 0) {
                nightDTO.getChairsides().add(assistant.getName());// 晚班
                pmNightArray[1] = pmNightArray[1] - 1;
                assistant.setTotalHours(assistant.getTotalHours() + ShiftEnum.NIGHT.getHours());
                assistantRepository.save(assistant);
            } else if (assistant.isFloater() && nightWorkArray[2] > 0) {
                nightDTO.getFloaters().add(assistant.getName());// 晚班
                pmNightArray[2] = pmNightArray[2] - 1;
                assistant.setTotalHours(assistant.getTotalHours() + ShiftEnum.NIGHT.getHours());
                assistantRepository.save(assistant);
            }
        }
    }

    /*
     * 晚班排班邏輯
     */
    public int[] nightSchedulingLogic(
            int[] pmNightArray,
            int[] ampmArray,
            int[] nightWorkArray,
            int[] pmWorkArray,
            List<Assistants> assistants,
            ScheduleDTO amDTO,
            ScheduleDTO pmDTO,
            ScheduleDTO nightDTO) {

        for (Assistants assistant : assistants) {

            // 先讓能連續相同崗位午晚的缺先安排
            // 如果是會櫃台，剛好又沒排人直接給他上櫃台
            if (assistant.isFrontDesk() && pmNightArray[0] >= 2) {
                if (!checkDuplicate(assistant.getName(), nightDTO)
                        && !checkDuplicate(assistant.getName(), pmDTO)) {
                    pmDTO.getFrontDesks().add(assistant.getName());// 寫入午班
                    nightDTO.getFrontDesks().add(assistant.getName());// 寫入晚班
                    // 扣除崗位
                    pmNightArray[0] = pmNightArray[0] - 2;
                    // 新增總時數
                    assistant.setTotalHours(assistant.getTotalHours() + ShiftEnum.AFTERNOON_NIGHT.getHours());
                    assistantRepository.save(assistant);
                    continue;
                }
            } else if (assistant.isChairside() && pmNightArray[1] >= 2
                    && pmDTO.getChairsides().size() < pmWorkArray[1]
                    && nightDTO.getChairsides().size() <= nightWorkArray[1]) {
                // 若不會櫃台會跟診就去跟診
                if (!checkDuplicate(assistant.getName(), nightDTO)
                        && !checkDuplicate(assistant.getName(), pmDTO)) {
                    pmDTO.getChairsides().add(assistant.getName());
                    nightDTO.getChairsides().add(assistant.getName());
                    pmNightArray[1] = pmNightArray[1] - 2;
                    // 新增總時數
                    assistant.setTotalHours(assistant.getTotalHours() + ShiftEnum.AFTERNOON_NIGHT.getHours());
                    assistantRepository.save(assistant);
                    continue;
                }
            } else if (assistant.isFloater() && pmNightArray[2] >= 2) {//
                // 如果櫃台、跟診都不會就去流動
                if (!checkDuplicate(assistant.getName(), nightDTO)
                        && !checkDuplicate(assistant.getName(), pmDTO)) {
                    pmDTO.getFloaters().add(assistant.getName());//
                    nightDTO.getFloaters().add(assistant.getName());//
                    pmNightArray[2] = pmNightArray[2] - 2;
                    // 新增總時數
                    assistant.setTotalHours(assistant.getTotalHours() + ShiftEnum.AFTERNOON_NIGHT.getHours());
                    assistantRepository.save(assistant);
                    continue;
                }

            }

            // 承接不同崗位邏輯
            if (assistant.isFrontDesk() && pmNightArray[0] > 0) {
                // IF 午班沒班 && 中午櫃台還有缺
                if (!checkDuplicate(assistant.getName(), pmDTO) && ampmArray[0] > 0) {
                    pmDTO.getFrontDesks().add(assistant.getName());// 午班櫃台
                    pmNightArray[0] = pmNightArray[0] - 1;// 扣除崗位

                    // 新增總時數
                    assistant.setTotalHours(assistant.getTotalHours() + ShiftEnum.AFTERNOON.getHours());
                    assistantRepository.save(assistant);

                    // IF 晚班沒班 && 晚上櫃台也缺人
                } else if (!checkDuplicate(assistant.getName(), nightDTO) && nightWorkArray[0] > 0) {

                    // 單純午晚班(ex:亦佳優先)
                    if (!checkDuplicate(assistant.getName(), pmDTO)
                            && !checkDuplicate(assistant.getName(), amDTO)) {
                        nightDTO.getFrontDesks().add(assistant.getName());// 晚班櫃台
                        pmNightArray[0] = pmNightArray[0] - 1;// 扣除崗位

                        // 新增總時數
                        assistant.setTotalHours(assistant.getTotalHours() + ShiftEnum.NIGHT.getHours());
                        assistantRepository.save(assistant);
                        continue;
                    }

                    // 如果早上沒班，中午有班，晚上沒班，處理午班不同崗位的人
                    if (checkDuplicate(assistant.getName(), pmDTO)
                            && !checkDuplicate(assistant.getName(), amDTO)) {
                        nightDTO.getFrontDesks().add(assistant.getName());// 晚班櫃台
                        pmNightArray[0] = pmNightArray[0] - 1;// 扣除崗位

                        // 新增總時數
                        assistant.setTotalHours(assistant.getTotalHours() + ShiftEnum.NIGHT.getHours());
                        assistantRepository.save(assistant);
                        continue;
                    }

                    // 如果早班有班，中午也有班，直接來上allDay
                    if (checkDuplicate(assistant.getName(), amDTO)
                            && checkDuplicate(assistant.getName(), pmDTO)) {
                        nightDTO.getFrontDesks().add(assistant.getName() + "*");// 晚班櫃台
                        pmNightArray[0] = pmNightArray[0] - 1;// 扣除崗位

                        assistant.setTotalHours(assistant.getTotalHours() + ShiftEnum.NIGHT.getHours());
                        assistantRepository.save(assistant);

                        // 萬不得已就上單診
                    } else {
                        nightDTO.getFrontDesks().add(assistant.getName() + "!");// 晚班櫃台
                        pmNightArray[0] = pmNightArray[0] - 1;// 扣除崗位

                        assistant.setTotalHours(assistant.getTotalHours() + ShiftEnum.NIGHT.getHours());
                        assistantRepository.save(assistant);
                        continue;
                    }
                }
            }

            // 會跟診技能，目前跟診也有缺人
            if (assistant.isChairside() && pmNightArray[1] > 0) {

                // 午班沒診，早午扣完還有缺跟診，中午的已排跟診人數 < 中午需要的人數(代表還少人)
                if (!checkDuplicate(assistant.getName(), pmDTO)
                        && ampmArray[1] > 0
                        && pmDTO.getChairsides().size() < pmWorkArray[1]) {
                    pmDTO.getChairsides().add(assistant.getName());//
                    pmNightArray[1] = pmNightArray[1] - 1;// 扣除崗位

                    // 新增總時數
                    assistant.setTotalHours(assistant.getTotalHours() + ShiftEnum.AFTERNOON.getHours());
                    assistantRepository.save(assistant);

                    // 晚上沒班，晚上跟診有缺，晚班跟診人數又小於目前缺的數量 ex:目前跟診人數2人 < 當天需要3個
                } else if (!checkDuplicate(assistant.getName(), nightDTO)) {

                    // 中午有班，早上沒班(中午跟晚上不同岡位的人)
                    if (checkDuplicate(assistant.getName(), pmDTO)
                            && !checkDuplicate(assistant.getName(), amDTO)) {
                        nightDTO.getChairsides().add(assistant.getName());//
                        pmNightArray[1] = pmNightArray[1] - 1;// 扣除崗位

                        // 新增總時數
                        assistant.setTotalHours(assistant.getTotalHours() + ShiftEnum.NIGHT.getHours());
                        assistantRepository.save(assistant);
                        continue;
                    }

                    // 早上有班、中午也有班，直接上全
                    if (checkDuplicate(assistant.getName(), amDTO)
                            && checkDuplicate(assistant.getName(), pmDTO)) {
                        nightDTO.getChairsides().add(assistant.getName() + "*");//
                        pmNightArray[1] = pmNightArray[1] - 1;// 扣除崗位

                        // 新增總時數
                        assistant.setTotalHours(assistant.getTotalHours() + ShiftEnum.NIGHT.getHours());
                        assistantRepository.save(assistant);
                        continue;
                    } else {
                        nightDTO.getChairsides().add(assistant.getName() + "!");//
                        pmNightArray[1] = pmNightArray[1] - 1;// 扣除崗位

                        assistant.setTotalHours(assistant.getTotalHours() + ShiftEnum.NIGHT.getHours());
                        assistantRepository.save(assistant);
                        continue;
                    }
                }
            }

            // 會流動，崗位缺也有，晚上也沒班
            if (assistant.isFloater()
                    && pmNightArray[2] > 0) {

                // 中午沒班，早午扣完還有剩中班缺，中午班要先處理
                if (!checkDuplicate(assistant.getName(), pmDTO)
                        && ampmArray[2] > 0
                        && pmWorkArray[2] > pmDTO.getFloaters().size()) {// 若中午有，晚上沒
                    pmDTO.getFloaters().add(assistant.getName());//
                    pmNightArray[2] = pmNightArray[2] - 1;// 扣除崗位

                    // 新增總時數
                    assistant.setTotalHours(assistant.getTotalHours() + ShiftEnum.AFTERNOON.getHours());
                    assistantRepository.save(assistant);

                    // 晚上沒班，崗位缺也有
                } else if (!checkDuplicate(assistant.getName(), nightDTO)
                        && pmNightArray[2] > 0
                        && nightWorkArray[2] > nightDTO.getFloaters().size()) //防止晚上已滿剩中午又進來
                        {

                    // 中午有班，早上沒班，晚上沒班(主要給午班不同崗位)
                    if (checkDuplicate(assistant.getName(), pmDTO) && !checkDuplicate(assistant.getName(), amDTO)) {
                        nightDTO.getFloaters().add(assistant.getName());//
                        pmNightArray[2] = pmNightArray[2] - 1;// 扣除崗位

                        // 新增總時數
                        assistant.setTotalHours(assistant.getTotalHours() + ShiftEnum.NIGHT.getHours());
                        assistantRepository.save(assistant);
                        continue;
                    }

                    // 早上有班，中午有班，直接來上全
                    if (checkDuplicate(assistant.getName(), amDTO) && checkDuplicate(assistant.getName(), pmDTO)) {
                        nightDTO.getFloaters().add(assistant.getName() + "*");//
                        pmNightArray[2] = pmNightArray[2] - 1;// 扣除崗位

                        // 新增總時數
                        assistant.setTotalHours(assistant.getTotalHours() + ShiftEnum.NIGHT.getHours());
                        assistantRepository.save(assistant);
                        continue;
                    } else {
                        // 可以排除單診抓以上班的人上全

                        nightDTO.getFloaters().add(assistant.getName() + "!");//
                        pmNightArray[2] = pmNightArray[2] - 1;// 扣除崗位
                        assistant.setTotalHours(assistant.getTotalHours() + ShiftEnum.NIGHT.getHours());
                        assistantRepository.save(assistant);
                        continue;
                    }
                }

            }
        }

        return pmNightArray;
    }

    /*
     * 查看該時段是否有上班
     * checkDuplicate = true (該時段有診) 有上班
     * checkDuplicate = false (該時段沒診) 沒上班
     */
    public boolean checkDuplicate(String name, ScheduleDTO dto) {
        boolean isPass = dto.getFrontDesks().contains(name) || //
                dto.getChairsides().contains(name) || //
                dto.getFloaters().contains(name);

        return isPass;
    }

}
