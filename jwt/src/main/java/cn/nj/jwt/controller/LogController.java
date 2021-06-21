package cn.nj.jwt.controller;

import cn.nj.jwt.pojo.MyTest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ：zty
 * @date ：Created in 2021/6/21 10:31
 * @description ：
 */
@RestController()
@RequestMapping("/log/")
public class LogController {


    @GetMapping("send")
    public void send(@RequestBody MyTest test){
        System.err.println(test.toString());
    }
}
