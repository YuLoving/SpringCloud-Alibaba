package cn.nj.sentinel.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

/**
 * @author ：zty
 * @date ：Created in 2021/4/2 12:15
 * @description ：  演示 Sentinel 限流规则 类
 */
@RestController
public class SentinelSampleController {


    @GetMapping("/test_flow_rule")
    public String testFlowRule(){
        System.out.println(LocalDateTime.now()+"进来了");
        return "SUCCESS";
    }


}
