package com.example.demo.component;

import org.springframework.stereotype.Component;

import com.example.demo.jpa.ResultSchedule;

@Component
public class ScheduleFormatter {

    private static final String TEMPLATE_132 = """
            　　　　　%s班

            櫃台：%s
            跟診：%s　　｜%s
            跟診：%s　　｜%s　　　
            跟診：%s　　｜%s
            流動：%s
            流動：%s
            """;

    // 121
    private static final String TEMPLATE_121 = "　　　　　%s班\n\n" +
            "櫃台：%s\n" +
            "跟診：%s　　｜%s\n" +
            "跟診：%s　　｜%s　　　\n" +
            "流動：%s";

    // 111
    private static final String TEMPLATE_111 = "　　　　　%s班\n\n" +
            "櫃台：%s\n" +
            "跟診：%s　　｜%s\n" +
            "流動 : %s\n";

    public String format2(String shift, ResultSchedule dto) {
        return String.format(TEMPLATE_132,
                shift,
                dto.getFrontDesk1()+dto.getFrontDesk2(),
                dto.getChairsideName1(),
                dto.getDoctorName1(),
                dto.getChairsideName2(),
                dto.getDoctorName2(),
                dto.getChairsideName3(),
                dto.getDoctorName3(),
                dto.getFloaterName1(),
                dto.getFloaterName2());

    }

//    public String formatDynamicSchedule(String shift, ResultSchedule dto) {
//     // 先檢查是否完全沒有資料
//     boolean hasFrontDesk = dto.getFrontDesk1() != null && !dto.getFrontDesk1().isEmpty();
//     boolean hasChairside = (dto.getChairsideName1() != null && !dto.getChairsideName1().isEmpty()) ||
//                            (dto.getDoctorName1() != null && !dto.getDoctorName1().isEmpty()) ||
//                            (dto.getChairsideName2() != null && !dto.getChairsideName2().isEmpty()) ||
//                            (dto.getDoctorName2() != null && !dto.getDoctorName2().isEmpty()) ||
//                            (dto.getChairsideName3() != null && !dto.getChairsideName3().isEmpty()) ||
//                            (dto.getDoctorName3() != null && !dto.getDoctorName3().isEmpty());
//     boolean hasFloater = (dto.getFloaterName1() != null && !dto.getFloaterName1().isEmpty()) ||
//                          (dto.getFloaterName2() != null && !dto.getFloaterName2().isEmpty());

//     if (!hasFrontDesk && !hasChairside && !hasFloater) {
//         return ""; // 整個班次沒人，不顯示
//     }

//     StringBuilder sb = new StringBuilder();
//     sb.append("　　　　　").append(shift).append("班\n\n");

//     // 櫃台
//     if (hasFrontDesk) {
//         sb.append("櫃台：").append(dto.getFrontDesk1()).append("\n");
//     }

//     // 跟診
//     String[][] chairsideDoctors = {
//             {dto.getChairsideName1(), dto.getDoctorName1()},
//             {dto.getChairsideName2(), dto.getDoctorName2()},
//             {dto.getChairsideName3(), dto.getDoctorName3()}
//     };

//     for (String[] pair : chairsideDoctors) {
//         if ((pair[0] != null && !pair[0].isEmpty()) || (pair[1] != null && !pair[1].isEmpty())) {
//             sb.append("跟診：")
//               .append(pair[0] != null ? pair[0] : "")
//               .append("　　｜")
//               .append(pair[1] != null ? pair[1] : "")
//               .append("\n");
//         }
//     }

//     // 流動
//     String[] floaters = {dto.getFloaterName1(), dto.getFloaterName2()};
//     for (String floater : floaters) {
//         if (floater != null && !floater.isEmpty()) {
//             sb.append("流動：").append(floater).append("\n");
//         }
//     }

//     return sb.toString().trim();
// }


}

// public String formatDynamicSchedule(String shift, ResultSchedule dto) {
// StringBuilder sb = new StringBuilder();
// sb.append(" ").append(shift).append("班\n\n");

// // 櫃台
// List<String> frontDesks = new ArrayList<>();
// if (dto.getFrontDesk1() != null && !dto.getFrontDesk1().isEmpty()) {
// frontDesks.add(dto.getFrontDesk1());
// }
// if (!frontDesks.isEmpty()) {
// sb.append("櫃台：").append(String.join(" / ", frontDesks)).append("\n");
// }

// // 跟診
// String[][] chairsideDoctors = {
// {dto.getChairsideName1(), dto.getDoctorName1()},
// {dto.getChairsideName2(), dto.getDoctorName2()},
// {dto.getChairsideName3(), dto.getDoctorName3()}
// };

// List<String> followUps = new ArrayList<>();
// for (String[] pair : chairsideDoctors) {
// if ((pair[0] != null && !pair[0].isEmpty()) || (pair[1] != null &&
// !pair[1].isEmpty())) {
// followUps.add("跟診：" +
// (pair[0] != null ? pair[0] : "") +
// " ｜" +
// (pair[1] != null ? pair[1] : ""));
// }
// }
// followUps.forEach(line -> sb.append(line).append("\n"));

// // 流動
// String[] floaters = {dto.getFloaterName1(), dto.getFloaterName2()};
// List<String> floaterList = new ArrayList<>();
// for (String floater : floaters) {
// if (floater != null && !floater.isEmpty()) {
// floaterList.add("流動：" + floater);
// }
// }
// floaterList.forEach(line -> sb.append(line).append("\n"));

// return sb.toString().trim();
// }
// }
