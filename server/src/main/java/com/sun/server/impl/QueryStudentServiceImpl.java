package com.sun.server.impl;

import com.sun.api.IQueryStudentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Service
@Slf4j
public class QueryStudentServiceImpl implements IQueryStudentService {
    @Override
    public String getName(String name) {
        System.out.println("my name is " + name);
        return "my name is " + name;
    }
}
