package com.example.demo.config;

public enum DoctorCount {
    ONE(111, 3), TWO(121, 4), THREE(132, 6);

    private final int workCode;
    private final int requiredAssistants;

    DoctorCount(int workCode, int requiredAssistants) {
        this.workCode = workCode;
        this.requiredAssistants = requiredAssistants;
    }

    public int getWorkCode() {
        return workCode;
    }

    public int getRequiredAssistants() {
        return requiredAssistants;
    }

    public static DoctorCount of(int count) {
        switch (count) {
            case 1:
                return ONE;
            case 2:
                return TWO;
            case 3:
                return THREE;
            default:
                throw new IllegalArgumentException("Unsupported doctor count: " + count);
        }
    }
}
