package com.cc.app.core.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AService {

    @Autowired
    private BService service;

    public void run(){
        System.out.println(">>>AService...");
        service.printB();
    }

    public void printA(){
        System.out.println(">>>AService...");
    }
}
