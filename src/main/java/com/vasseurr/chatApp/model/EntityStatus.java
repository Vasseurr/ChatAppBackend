package com.vasseurr.chatApp.model;

public enum EntityStatus {
    PASSIVE(0), ACTIVE(1), ;

    private int value;

    EntityStatus(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
