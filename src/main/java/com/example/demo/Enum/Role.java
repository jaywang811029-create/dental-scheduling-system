package com.example.demo.Enum;

public enum Role {
    FRONT_DESK(2), 
    CHAIR_SIDE(3), 
    FLOATER(1);

    private final int workSlots;

    Role(int workSlots) {
        this.workSlots = workSlots;
    }

    public int getWorkSlots() {
        return workSlots;
    }
}



