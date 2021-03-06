package com.sun.entity;


import lombok.Data;

@Data
public class Request {

    private String requestId;

    private String className;


    private String methodName;

    private Class<?>[] parameterTypes;

    private Object[] parameters;

}
