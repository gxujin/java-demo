package com.cc.app.core;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication(scanBasePackages = {"com.cc.app","mapping"})
@EnableDiscoveryClient
@EnableFeignClients()
@MapperScan({"com.cc.app.*.dao"})
public class ApiCoreApplication {
    public static void main(String[] args) {
        SpringApplication.run(ApiCoreApplication.class, args);
    }
}
