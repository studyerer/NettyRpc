package com.sun.entity;

import lombok.Data;

@Data
public class Response {

    private String requestId;

    private Throwable throwable;

    private Object result;
}
