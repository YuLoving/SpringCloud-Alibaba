package cn.nj.seatatmmall.controller;

import cn.nj.seatatmmall.service.MallService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ：zty
 * @date ：Created in 2021/4/28 16:03
 * @description ：
 */
@RestController
public class MallController {

    @Autowired
    private MallService mallService;


    @GetMapping("/sale")
    public String sale(){
        return mallService.sale();
    }

}
