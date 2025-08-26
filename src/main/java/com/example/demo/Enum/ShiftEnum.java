package com.example.demo.Enum;

import java.util.Arrays;
import java.util.List;

public enum ShiftEnum {
    MORNING("早", 4, List.of("AM")),
    AFTERNOON("午", 4, List.of("PM")),
    NIGHT("晚", 4, List.of("NIGHT")),
    MORNING_AFTERNOON("早午", 8, List.of("AM", "PM")),
    AFTERNOON_NIGHT("午晚", 8, List.of("PM", "NIGHT")),
    FULL("早午晚", 12, List.of("AM", "PM", "NIGHT"));

    private final String code; // 中文值
    private final int hours;   // 總工時
    private final List<String> sessions; // 涉及的班別

    ShiftEnum(String code, int hours, List<String> sessions) {
        this.code = code;
        this.hours = hours;
        this.sessions = sessions;
    }

    public String getCode() {
        return code;
    }

    public int getHours() {
        return hours;
    }

    public List<String> getSessions() {
        return sessions;
    }

    // 由中文找到 Enum
    public static ShiftEnum fromCode(String code) {
        return Arrays.stream(values())
                     .filter(e -> e.code.equals(code))
                     .findFirst()
                     .orElseThrow(() -> new IllegalArgumentException("無效的班別: " + code));
    }
}

