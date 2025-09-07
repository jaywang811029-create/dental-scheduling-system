package com.example.demo.service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.Enum.ShiftEnum;
import com.example.demo.component.AssistantFinder;
import com.example.demo.component.ScheduleCalculator;
import com.example.demo.controller.ScheduleDTO;
import com.example.demo.jpa.Assistants;
import com.example.demo.jpa.LeaveSchedule;
import com.example.demo.jpa.LeaveScheduleKey;
import com.example.demo.jpa.ResultSchedule;
import com.example.demo.jpa.ResultScheduleKey;
import com.example.demo.repository.AssistantRepository;
import com.example.demo.repository.LeaveScheduleRepository;
import com.example.demo.repository.ResultScheduleRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ScheduleServiceImpl implements ScheduleService {

    private final LeaveScheduleRepository leaveScheduleRepository;
    private final ResultScheduleRepository resultScheduleRepository;
    private final AssistantRepository assistantRepository;

    @Autowired
    private final AssistantFinder assistantFinder;

    @Autowired
    private final ScheduleCalculator scheduleCalculator;

    private static final Logger logger = LoggerFactory.getLogger(ScheduleServiceImpl.class);

    @Override
    public List<ResultSchedule> doQuery(String region) {

        if ("竹北".equals(region)) {
            return resultScheduleRepository.findByRegion(region);
        } else if ("新竹".equals(region)) {
            return resultScheduleRepository.findByRegion(region);
        }
        throw new UnsupportedOperationException("Unimplemented method 'doQuery'");

    }

    @Override
    public void doWork(String region) {

        // 若人力不夠需要上全，一週不要連續兩天，all off all or all 午晚 all
        // 六日、日一休假要平均
        // 若有可all班同仁有早午班也要平均
        // 竹北六送洗衣服、一拿衣服要平均
        // 固定新竹上班人員:如君、奕佳、語婕、儒偵、炳兒
        // 可支援兩邊人員:育玲、思妤、俞凱、玟潔、芯翎、明芬
        // 固定竹北人員:佳融、筱蘋、昌閔、予均、冠伶
        // 俞凱跟筱蘋班錯開，思妤和芯翎班錯開
        // 總而言之全部要公平

        if (region.equals("新竹")) {
            System.out.println("測試=============================================");
            // 清空當月班表
            resultScheduleRepository.deleteAll();
            // 員工時數從新計算
            scheduleCalculator.deleteAllHours();
        }

        ScheduleDTO amDTO;
        ScheduleDTO pmDTO;
        ScheduleDTO nightDTO;

        // set1.取得每個禮拜第一天跟最後一天
        Map<Integer, ArrayList<LocalDate>> dayOfWeekMap = this.getDayOfWeek();

        // 當天醫師班表
        List<String> amDrOnDutyList = new ArrayList<>();
        List<String> pmDrOnDutyList = new ArrayList<>();
        List<String> nightDrOnDutyList = new ArrayList<>();

        // 當前日期
        LocalDate currentDate = null;
        // 計算

        // 休假建立方法單純處理，就把Join砍掉減少複雜度
        // 判斷假設有休假的話，就把雙邊跑的人員補上去

        for (ArrayList<LocalDate> start_end : dayOfWeekMap.values()) {

            // set2.醫師班表
            LocalDate startDate = start_end.get(0);
            LocalDate endDate = start_end.get(1);
            List<LeaveSchedule> leaves = leaveScheduleRepository.findByLeaveDateBetween(startDate, endDate, region);

            for (LeaveSchedule doctorLeave : leaves) {

                // logger.warn("日期={},ID={}",doctorLeave.getKey().getDate(),doctorLeave.getKey().getId());

                if (currentDate == null) {
                    // 當月第一天
                    currentDate = doctorLeave.getKey().getDate();
                } else if (!currentDate.isEqual(doctorLeave.getKey().getDate())) {
                    amDTO = new ScheduleDTO();
                    pmDTO = new ScheduleDTO();
                    nightDTO = new ScheduleDTO();

                    // 處理當日班表邏輯，新增班表
                    processAndSaveDailySchedule(currentDate,
                            region,
                            amDTO,
                            pmDTO,
                            nightDTO,
                            amDrOnDutyList,
                            pmDrOnDutyList,
                            nightDrOnDutyList,
                            start_end);

                    currentDate = doctorLeave.getKey().getDate();
                    amDrOnDutyList = new ArrayList<>();
                    pmDrOnDutyList = new ArrayList<>();
                    nightDrOnDutyList = new ArrayList<>();

                }

                // 處理醫師排班
                if (currentDate.isEqual(doctorLeave.getKey().getDate())) {
                    String shift = doctorLeave.getShiftType().trim();
                    String name = doctorLeave.getName();

                    if (shift.contains(ShiftEnum.MORNING.getCode())) {
                        amDrOnDutyList.add(name);
                    }
                    if (shift.contains(ShiftEnum.AFTERNOON.getCode())) {
                        pmDrOnDutyList.add(name);
                    }
                    if (shift.contains(ShiftEnum.NIGHT.getCode())) {
                        nightDrOnDutyList.add(name);
                    }
                }
            }
            amDTO = new ScheduleDTO();
            pmDTO = new ScheduleDTO();
            nightDTO = new ScheduleDTO();
            // 1. 先取得該月的最後一天
            LocalDate lastDayOfMonth = currentDate.with(TemporalAdjusters.lastDayOfMonth());

            // 2. 從該月的最後一天，往回找最接近的禮拜六
            LocalDate lastSaturday = lastDayOfMonth.with(TemporalAdjusters.previousOrSame(DayOfWeek.SATURDAY));

            if (currentDate.isEqual(lastSaturday)) {
                processAndSaveDailySchedule(currentDate,
                        region,
                        amDTO,
                        pmDTO,
                        nightDTO,
                        amDrOnDutyList,
                        pmDrOnDutyList,
                        nightDrOnDutyList,
                        start_end);
            }
        }
    }

    /**
     * 處理並儲存單日排班資料的邏輯
     */
    private void processAndSaveDailySchedule(
            LocalDate currentDate,
            String region,
            ScheduleDTO amDTO,
            ScheduleDTO pmDTO,
            ScheduleDTO nightDTO,
            List<String> amDrOnDutyList,
            List<String> pmDrOnDutyList,
            List<String> nightDrOnDutyList,
            ArrayList<LocalDate> star_end) {

        // 日期
        amDTO.setDate(currentDate.toString());
        pmDTO.setDate(currentDate.toString());
        nightDTO.setDate(currentDate.toString());

        // 當日醫師
        amDTO.setDoctors(amDrOnDutyList);// 1
        pmDTO.setDoctors(pmDrOnDutyList);// 2
        nightDTO.setDoctors(nightDrOnDutyList);// 1

        // 醫師人數
        // 一個醫師 111 (3)
        // 兩個醫師 121 (4)
        // 三個醫師 132(6) or 232(最好7) or 131(最壞5)
        // 計算早、午、晚醫師人數，計算人力後加總。ex:121+111+132 [0]=121 [1]=1+2+1
        Integer ampmCalculation = calculation(amDrOnDutyList.size())[0]
                + calculation(pmDrOnDutyList.size())[0];
        String ampmWorkCode = ampmCalculation.toString();
        String nightWorkCode = calculation(nightDrOnDutyList.size())[0].toString();
        String amWorkCode = calculation(amDrOnDutyList.size())[0].toString();
        String pmWorkCode = calculation(pmDrOnDutyList.size())[0].toString();

        // 劃分早午崗位 ex:[1,1,1] 1櫃台 1跟診 1流動
        int[] ampmArray = ampmWorkCode.chars()
                .map(Character::getNumericValue)
                .toArray();
        ampmArray = handleWorkArray(ampmArray);

        int[] amArray = amWorkCode.chars()
                .map(Character::getNumericValue)
                .toArray();
        amArray = handleWorkArray(amArray);

        int[] pmArray = pmWorkCode.chars()
                .map(Character::getNumericValue)
                .toArray();
        pmArray = handleWorkArray(pmArray);

        // 劃分晚班崗位
        int[] nightWorkArray = nightWorkCode.toString().chars()
                .map(Character::getNumericValue)
                .toArray();
        nightWorkArray = handleWorkArray(nightWorkArray);

        /*-----------------
         | 早午班排班邏輯   |
         ------------------*/
        if (currentDate.toString().equals("2025-08-09")) {
            System.out.println("");
        }

        // 早午班員工List
        List<Assistants> amAssisList = assistantFinder.findByDayAndShift(
                currentDate,
                ShiftEnum.MORNING_AFTERNOON.getCode(), 170, region + "%");

        scheduleCalculator.removeAssistantLeave(amAssisList, ShiftEnum.MORNING_AFTERNOON, currentDate, region,
                star_end);// 刪除休假員工

        // 計算支援人數 ex:早午固定1個，需要3個，需補2個早午晚人力，如果是負數代表當天不用上班(禮拜一早)
        int remaining = calculation(amDrOnDutyList.size())[1] - amAssisList.size();

        // amAssisList(早班早午人力) < 早班總人力 ex: 2位醫師[121] = 4人
        if (amAssisList.size() < calculation(amDrOnDutyList.size())[1]) {
            // 早班不夠人群求替補
            findSupporAssistants(amAssisList, currentDate, remaining, region);
        }
        System.out.println("DEBUG=" + currentDate);

        // 執行早午班排班邏輯
        scheduleCalculator.evenSchedulingLogic(
                ampmArray,
                amArray,
                amAssisList,
                amDTO,
                pmDTO,
                nightDTO);

        /*---------------------
         | 晚班排班邏輯  (先優) |
         ---------------------*/

        // 統整午班跟晚班總人數
        int[] pmNightArray = new int[3];
        // 晚班員工
        List<Assistants> nightDayAssisList = assistantFinder.findByDayAndShift(
                currentDate,
                ShiftEnum.NIGHT.getCode(), 170, region);

        // 優先排單診晚班員工
        scheduleCalculator.singleSchedulingLogic(
                ampmArray,
                pmNightArray,
                nightWorkArray,
                nightDayAssisList,
                nightDTO);


        /*---------------------------
         | 午晚班排班邏輯 (第二優先)  |
         ----------------------------*/
        // 午晚班員工
        List<Assistants> pmDayAssisList = assistantFinder.findByDayAndShift(
                currentDate,
                ShiftEnum.AFTERNOON_NIGHT.getCode(), 170, region+"%");

        nightWorkArray = handleWorkArray(nightWorkArray);
        // 次先排固定午晚班員工
        pmNightArray = scheduleCalculator.nightSchedulingLogic(
                pmNightArray,
                ampmArray,
                nightWorkArray,
                pmArray,
                pmDayAssisList,
                amDTO,
                pmDTO,
                nightDTO);

        /*-----------------
         | 全班排班邏輯   |
         ------------------*/
        // 最後在排早午晚員工List

        List<Assistants> allDayAssisList;
        allDayAssisList = assistantFinder.findByDayAndShift(
                currentDate,
                ShiftEnum.FULL.getCode(), 170, "%" + region + "%");// 測試
        scheduleCalculator.removeAssistantLeave(allDayAssisList, ShiftEnum.FULL, currentDate, region, star_end);//
        // 刪除休假員工

        // 補充剩餘人數，因可能午晚崗位不同，需獨立出來
        pmNightArray = scheduleCalculator.nightSchedulingLogic(
                pmNightArray,
                ampmArray,
                nightWorkArray,
                pmArray,
                allDayAssisList,
                amDTO,
                pmDTO,
                nightDTO);

        // 新增當日班表到資料庫
        insertResultData(region, currentDate, ShiftEnum.MORNING.getCode(), amDTO);
        insertResultData(region, currentDate, ShiftEnum.AFTERNOON.getCode(), pmDTO);
        insertResultData(region, currentDate, ShiftEnum.NIGHT.getCode(), nightDTO);

        if (pmNightArray[0] != 0 || pmNightArray[1] != 0 || pmNightArray[2] != 0) {
            List<LeaveSchedule> leaveNames = leaveScheduleRepository.findByKeyDateAndRegionAndIsLeave(currentDate,
                   "%"+ region + "%", true);
            List<Assistants> freeAssistants = assistantRepository.findUnscheduledAssistants(currentDate,
                    "%" + region + "%");
            Set<String> names = freeAssistants.stream()
                    .map(Assistants -> {
                        return Assistants.getName() + "|" + Assistants.getTotalHours();
                    })
                    .collect(Collectors.toSet());
            Set<String> leaveNamesSet = leaveNames.stream()
                    .map(LeaveSchedule -> {
                        return LeaveSchedule.getName() + "=" + LeaveSchedule.getRegion();
                    })
                    .collect(Collectors.toSet());
            logger.warn("\n{}->{},\n目前空缺=櫃台{},跟診{},流動{},\n可支援人力{},\n目前休假{}\n", region,
            pmDTO.getDate(),
            pmNightArray[0], pmNightArray[1], pmNightArray[2], names.toString(),
            leaveNamesSet.toString());
        }
    }

    void test(ScheduleDTO amDTO, ScheduleDTO pmDTO, ScheduleDTO nightDTO) {
        List<String> am1 = amDTO.getFrontDesks();
        List<String> am2 = amDTO.getChairsides();
        List<String> am3 = amDTO.getFloaters();
        List<String> pm1 = pmDTO.getFrontDesks();
        List<String> pm2 = pmDTO.getChairsides();
        List<String> pm3 = pmDTO.getFloaters();
        List<String> night1 = nightDTO.getFrontDesks();
        List<String> night2 = nightDTO.getChairsides();
        List<String> night3 = nightDTO.getFloaters();

        // 使用 Stream.of 將所有 List 合併成一個 Stream
        // 然後利用 flatMap 將每個 List 的元素展平成一個單一的 Stream
        Stream<String> allStringsStream = Stream.of(
                am1, am2, am3,
                pm1, pm2, pm3,
                night1, night2, night3).flatMap(List::stream);

        // 使用 Collectors.groupingBy 來分組並計數
        Map<String, Long> countMap = allStringsStream
                .collect(Collectors.groupingBy(s -> s, Collectors.counting()));

        // 印出結果
        countMap.forEach((key, value) -> System.out.println("檢測=" + key + " : " + value + " 次"));

    }

    /*
     * 人力計算
     */
    public Integer[] calculation(int doctorCount) {
        // 正確的陣列初始化語法
        Integer[] result = new Integer[2];
        int workCode = 0;
        int sum = 0;

        if (doctorCount == 1) {
            workCode = 111;
            sum = 3;
        } else if (doctorCount == 2) {
            workCode = 121;
            sum = 4;
        } else if (doctorCount == 3) {
            workCode = 132;
            sum = 6;
        }
        // 將值賦予陣列的元素
        result[0] = workCode;
        result[1] = sum;
        // 回傳陣列變數
        return result;
    }

    public Map<Integer, ArrayList<LocalDate>> getDayOfWeek() {

        // 1. 設定要查詢的年份與月份
        int year = 2025;
        int month = 8; // 八月

        // 2. 取得該月份的上下文
        YearMonth yearMonth = YearMonth.of(year, month);

        // 3. 取得該月的第一天與最後一天
        LocalDate startDate = yearMonth.atDay(1);
        LocalDate lastDayOfMonth = yearMonth.atEndOfMonth();

        Map<Integer, ArrayList<LocalDate>> map = new HashMap<>();
        Integer weekNumber = 1;

        // 4. 迴圈遍歷該月份，直到超過月底
        while (startDate.isBefore(lastDayOfMonth.plusDays(1))) {

            // 找到該「週」的結束日（即禮拜六）
            LocalDate weekEndDate = startDate;
            // 讓週的結束日往前推進，直到遇到星期日或月底
            while (weekEndDate.isBefore(lastDayOfMonth.plusDays(1)) && weekEndDate.getDayOfWeek() != DayOfWeek.SUNDAY) {
                weekEndDate = weekEndDate.plusDays(1);
            }
            // 由於迴圈會在遇到星期日時結束，所以要退回一天，確保結束日是星期六
            weekEndDate = weekEndDate.minusDays(1);

            // 如果開始日期已經超過結束日期，代表剩下的天數不足一週（只有星期天），則跳過
            if (startDate.isAfter(weekEndDate)) {
                startDate = startDate.plusDays(1);
                continue;
            }

            System.out.printf(
                    "第 %d 週: %s 到 %s\n",
                    weekNumber,
                    startDate.toString(),
                    weekEndDate.toString());
            ArrayList<LocalDate> list = new ArrayList<LocalDate>();
            list.add(startDate);
            list.add(weekEndDate);
            map.put(weekNumber, list);

            // 5. 進入下一週的開始日
            // 新的開始日是這週結束日（星期六）的後兩天，即星期一
            startDate = weekEndDate.plusDays(2);
            weekNumber++;
        }

        return map;

    }

    /*
     * 找支援人力 (會刪除昨日上全班的人員)
     * 
     */
    public void findSupporAssistants(List<Assistants> amAssisList, LocalDate currentDate, Integer remaining,
            String region) {
            
       
        
        List<Assistants> shuffleAssistanList = new ArrayList<>();

        // 早班不夠人群求替補，禮拜六需要找早午
        shuffleAssistanList = assistantFinder.findByDayAndShift(
                currentDate,
                currentDate.getDayOfWeek() == DayOfWeek.SATURDAY? ShiftEnum.MORNING_AFTERNOON.getCode(): ShiftEnum.FULL.getCode() ,
                 170, 
                 "%" + region + "%");// 試改
        
                LocalDate yesterdayDate = currentDate.minusDays(1);
        // 如果昨天有上全的早班就不排
        shuffleAssistanList.removeIf(assistan -> resultScheduleRepository
                .countByIsAllDay(assistan.getName(), yesterdayDate.toString()) == 3);

        // 刪除休假的人員
        shuffleAssistanList.removeIf(assistant -> {
            LeaveScheduleKey key = new LeaveScheduleKey(assistant.getId(), currentDate);
            return leaveScheduleRepository.findByKey(key).size() > 0;
        });

        // Collections.shuffle(shuffleAssistanList);//隨機
        if (shuffleAssistanList.size() >= remaining && remaining > 0) {
            // 支援人數>需求人數
            amAssisList.addAll(shuffleAssistanList.subList(0, remaining));
        } else if (shuffleAssistanList.size() < remaining) {
            // 支援人數<需求人數 (請跨區幫忙)
            shuffleAssistanList = new ArrayList<>();
            shuffleAssistanList = assistantFinder.findByDayAndShift(
                    currentDate,
                    ShiftEnum.FULL.getCode(), 170, "%" + region + "%");
            // 如果昨天有上全的早班就不排
            shuffleAssistanList.removeIf(assistan -> resultScheduleRepository
                    .countByIsAllDay(assistan.getName(), yesterdayDate.toString()) == 3);

            // 刪除休假的人員
            shuffleAssistanList.removeIf(assistant -> {
                LeaveScheduleKey key = new LeaveScheduleKey(assistant.getId(), currentDate);
                return leaveScheduleRepository.findByKey(key).size() > 0;
            });
            amAssisList.addAll(shuffleAssistanList.subList(0, shuffleAssistanList.size()));
        }

    }

    /**
     * 處理工作陣列：如果長度足夠且第一個元素為 0，則將前三個元素都設為 0
     */
    public static int[] handleWorkArray(int[] workArray) {

        if (workArray[0] == 0) {
            workArray = new int[3];
            workArray[0] = 0;
            workArray[1] = 0;
            workArray[2] = 0;
        }
        return workArray;
    }

    /*
     * 新增資料到畫面上
     * 
     */
    public void insertResultData(String region, LocalDate date, String shiftType, ScheduleDTO dto) {

        ResultSchedule resultDTO = new ResultSchedule();
        ResultScheduleKey key = new ResultScheduleKey();
        key.setDate(date);
        key.setShiftType(shiftType);
        key.setRegion(region);
        resultDTO.setKey(key);

        // 填充醫師
        List<String> doctors = dto.getDoctors();
        resultDTO.setDoctorName1(getPersonSafely(doctors, 0));
        resultDTO.setDoctorName2(getPersonSafely(doctors, 1));
        resultDTO.setDoctorName3(getPersonSafely(doctors, 2));

        // 填充櫃檯
        List<String> frontDesks = dto.getFrontDesks();
        resultDTO.setFrontDesk1(getPersonSafely(frontDesks, 0));
        resultDTO.setFrontDesk2(getPersonSafely(frontDesks, 1));
        resultDTO.setFrontDesk3(getPersonSafely(frontDesks, 2));

        // 填充跟診助理
        List<String> chairsides = dto.getChairsides();
        resultDTO.setChairsideName1(getPersonSafely(chairsides, 0));
        resultDTO.setChairsideName2(getPersonSafely(chairsides, 1));
        resultDTO.setChairsideName3(getPersonSafely(chairsides, 2));

        // 填充流動
        List<String> floaters = dto.getFloaters();
        resultDTO.setFloaterName1(getPersonSafely(floaters, 0));
        resultDTO.setFloaterName2(getPersonSafely(floaters, 1));
        resultDTO.setFloaterName3(getPersonSafely(floaters, 2));

        // 填充流動
        Map<Integer, String> suppors = dto.getSuppors();
        resultDTO.setSupportName1(suppors.get(1));
        resultDTO.setSupportName2(suppors.get(2));

        resultScheduleRepository.saveAndFlush(resultDTO);
    }

    private String getPersonSafely(List<String> list, int index) {
        if (list != null && list.size() > index) {
            String name = list.get(index);
            return name.isEmpty() ? "　　" : name;
        }
        return "　　";
    }

}
