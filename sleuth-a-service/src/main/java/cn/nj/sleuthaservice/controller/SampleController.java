package cn.nj.sleuthaservice.controller;

import cn.nj.sleuthaservice.feignClient.BServiceFeignClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ：zty
 * @date ：Created in 2021/4/9 10:58
 * @description ：  SampleController 通过 methodA 方法调用 methodB 的同时，成为响应附加的字符串“-> Service A”，方法映射地址“/a”。
 */
@RestController
public class SampleController {

    @Autowired
    private BServiceFeignClient bServiceFeignClient;


    @GetMapping("/a")
    public String methodA(){
        String result = bServiceFeignClient.methodB();
        result= "-> Service A" + result;
        return result;
    }
}
