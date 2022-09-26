package com.cc.app.core.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "api-demo")
public interface DemoFeign {

    @GetMapping("/order/create")
    Integer create(@RequestParam(name = "id") Integer id);
}
