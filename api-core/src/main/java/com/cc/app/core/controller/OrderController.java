package com.cc.app.core.controller;

import com.cc.app.core.feign.Demo2Feign;
import com.cc.app.core.feign.DemoFeign;
import com.cc.app.core.service.AService;
import com.cc.app.core.service.BService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CyclicBarrier;

@RestController
@RequestMapping("/order")
public class OrderController {

    private Logger logger = LoggerFactory.getLogger(OrderController.class);

    private static Integer orderNo = 0;

    @PostMapping("/id")
    public Integer get(){
        Integer order = 0;
        try {
            long threadId = Thread.currentThread().getId();
            order = orderNo;
            Thread.sleep(300000);
            order++;
            orderNo = order;
            logger.info("线程：{}， orderno-{}", threadId, order);
        }catch (Exception e){
            e.printStackTrace();
        }
        return order;
    }

    @Autowired
    private DemoFeign demoFeign;
    @Autowired
    private Demo2Feign demo2Feign;

    @GetMapping("/test")
    public void test(){
        try {
            CyclicBarrier barrier = new CyclicBarrier(1, () -> {
                try {
                    logger.info("准备...");
                    Thread.sleep(3000);
                    logger.info("开始...");
                }catch (Exception e){
                    e.printStackTrace();
                }
            });
            for(int i = 0; i < 1; i++) {
                int id = i + 1;
                new TaskThread(barrier, demoFeign, id).start();
//                new Task2Thread(barrier, demo2Feign, id).start();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Autowired
    private AService aService;
    @Autowired
    private BService bService;

    @GetMapping("/run")
    public void run(){
        aService.run();
    }
    @GetMapping("/run2")
    public void run2(){
        bService.run();
    }
}

class TaskThread extends Thread {

    DemoFeign demoFeign;

    CyclicBarrier barrier;

    int id;

    public TaskThread(CyclicBarrier barrier, DemoFeign demoFeign, int id) {
        this.barrier = barrier;
        this.demoFeign = demoFeign;
        this.id = id;
    }

    @Override
    public void run() {
        try {
            System.out.println("task1..."+getId());
            barrier.await();
            demoFeign.create(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

class Task2Thread extends Thread {

    Demo2Feign demo2Feign;

    CyclicBarrier barrier;

    int id;

    public Task2Thread(CyclicBarrier barrier, Demo2Feign demo2Feign, int id) {
        this.barrier = barrier;
        this.demo2Feign = demo2Feign;
        this.id = id;
    }

    @Override
    public void run() {
        try {
            System.out.println("task2..."+getId());
            barrier.await();
            demo2Feign.create(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}