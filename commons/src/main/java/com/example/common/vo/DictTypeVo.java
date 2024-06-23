package com.example.common.vo;

public enum DictTypeVo {
    IMAGE("image"),
    TEXT("text"),
    DISHTYPE("dish.type"),
    DISHIMG("dish-image"),
    ORDER("order"),
    NOTICE("notice"),
    ACTIVITY("activity"),
    INFORMATION("information"),
    MANBING("MANBING");
    private final String code;

    DictTypeVo(String code) {
        this.code = code;
    }
    public String getCode() {
        return this.code;
    }
}
