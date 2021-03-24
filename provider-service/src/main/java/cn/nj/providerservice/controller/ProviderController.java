package cn.nj.providerservice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ：zty
 * @date ：Created in 2021/3/22 17:17
 * @description ：
 */
@RestController
public class ProviderController {

    @GetMapping("/provider/msg")
    public String sendMessage(){
        return "This is the message from provider service!";
    }
}
