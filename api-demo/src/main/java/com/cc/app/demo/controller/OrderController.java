package com.cc.app.demo.controller;

import com.cc.app.demo.service.OrderService;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping("/order")
public class OrderController {

    private Logger logger = LoggerFactory.getLogger(OrderController.class);

    @Autowired
    private CuratorFramework curatorFramework;
    @Autowired
    private OrderService orderService;

    @GetMapping("/create")
    public Integer create(@RequestParam(name = "id") String id){
        Integer orderNo = null;
        try{
            String nodeName = "/order";
            Stat stat = curatorFramework.checkExists().forPath(nodeName);
            if (stat == null) {
                curatorFramework.create().creatingParentsIfNeeded().withMode(CreateMode.PERSISTENT).forPath(nodeName);
            }
            InterProcessMutex lock = new InterProcessMutex(curatorFramework, nodeName);
            orderNo = this.orderService.create(lock, id);
//            orderNo = this.orderService.create(id);
        }catch (Exception e){
            e.printStackTrace();
        }
        return orderNo;
    }

    @GetMapping("/info")
    public String info(@RequestParam(name = "name") String name){
        String text = "hello" + name + new Date().getTime();
        logger.info(text);
        return text;
    }
}
