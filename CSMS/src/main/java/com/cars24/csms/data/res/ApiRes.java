package com.cars24.csms.data.res;

import lombok.Data;

@Data
public class ApiRes {
    private int statusCode;
    private boolean success;
    private String message;
    private String service;
    private Object data;
}
