package cn.nj.warehouseservice.controller;

import cn.nj.warehouseservice.config.CustomConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ：zty
 * @date ：Created in 2021/4/7 18:17
 * @description ：
 */
@RestController
public class TestController {

    @Autowired
    private CustomConfig customConfig;

    @GetMapping("/test")
    public String test(){
        return "flag:" + customConfig.getFlag() + "<br/> database:" + customConfig.getDatabase();
    }

}
