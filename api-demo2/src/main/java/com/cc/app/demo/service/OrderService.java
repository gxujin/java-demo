package com.cc.app.demo.service;

import com.cc.app.demo.feign.CoreService;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    private Logger logger = LoggerFactory.getLogger(OrderService.class);

    @Autowired
    private CoreService coreService;

    private static Integer orderNo = 0;

    public Integer create(InterProcessMutex lock, String id) throws Exception {
        try{
            long threadId = Thread.currentThread().getId();
            logger.info("请求id：{}，线程：{}，create...", id, threadId);
            lock.acquire();
            logger.info("请求id：{}，线程：{}，获取订单号开始...", id, threadId);
            orderNo = coreService.orderId();
            logger.info("请求id：{}，线程：{}，订单号: {}", id, threadId, orderNo);
            logger.info("请求id：{}，线程：{}，获取订单号结束.", id, threadId);
        }catch (Exception e){
            throw e;
        }finally {
            lock.release();
        }
        return orderNo;
    }

    public Integer create(String id) {
        try{
            long threadId = Thread.currentThread().getId();
            logger.info("请求id：{}，线程：{}，create...", id, threadId);
            logger.info("请求id：{}，线程：{}，获取订单号开始...", id, threadId);
            orderNo = coreService.orderId();
            logger.info("请求id：{}，线程：{}，订单号: {}", id, threadId, orderNo);
            logger.info("请求id：{}，线程：{}，获取订单号结束.", id, threadId);
        }catch (Exception e){
            throw e;
        }
        return orderNo;
    }
}
