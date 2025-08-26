package com.example.demo.component;

import java.time.LocalDate;
import java.util.Map;

public class ShiftTimeProvider {
    private static final Map<String, String[]> SHIFT_TIMES = Map.of(
            "早", new String[] { "T09:00:00", "T12:00:00" },
            "午", new String[] { "T13:00:00", "T17:00:00" },
            "晚", new String[] { "T18:00:00", "T21:30:00" });

    public static String getStart(LocalDate date, String shift) {
        return date.toString() + SHIFT_TIMES.getOrDefault(shift, new String[] { "T00:00:00", "T23:59:59" })[0];
    }

    public static String getEnd(LocalDate date, String shift) {
        return date.toString() + SHIFT_TIMES.getOrDefault(shift, new String[] { "T00:00:00", "T23:59:59" })[1];
    }
}
