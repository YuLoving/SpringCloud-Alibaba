package cn.nj.sleuthbservice.controller;

import cn.nj.sleuthbservice.feignClient.CServiceFeignClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ：zty
 * @date ：Created in 2021/4/9 10:54
 * @description ： SampleController 通过 methodB 方法调用 methodC 的同时为响应附加的字符串“-> Service B”，方法映射地址“/b”。
 */
@RestController
public class SampleController {

    @Autowired
    private CServiceFeignClient cServiceFeignClient;

    @GetMapping("/b")
    public String methodB(){
        String result = cServiceFeignClient.methodC();
        result=" -> Service B" + result;
        return result;
    }


}
