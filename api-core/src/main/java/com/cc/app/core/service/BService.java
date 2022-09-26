package com.cc.app.core.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BService {

    @Autowired
    private AService service;

    @Transactional
    public void run(){
        System.out.println(">>>BService...");
        service.printA();
    }

    public void printB(){
        System.out.println(">>>B-BService...");
    }
}
