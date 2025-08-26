package com.example.demo.Enum;

import java.util.List;

public record Event(
        String title,
        String start,
        String end,
        List<String> classNames
) {}
