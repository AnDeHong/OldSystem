package com.example.response;

import lombok.Data;


public enum ErrorMsg {
    DATA_EXIST("DATA_EXIST"),
    NO_DATA("NO_DATA");
    private String error;

    ErrorMsg(String error) {
        this.error = error;
    }
    public String getError() {
        return this.error;
    }
}
