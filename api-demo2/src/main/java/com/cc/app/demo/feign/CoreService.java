package com.cc.app.demo.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "api-core")
public interface CoreService {

    @PostMapping("/order/id")
    Integer orderId();
}
