package cn.nj.seatarmorder.controller;

import cn.nj.seatarmorder.entity.Order;
import cn.nj.seatarmorder.service.OrderService;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @author ：zty
 * @date ：Created in 2021/4/27 17:15
 * @description ：
 */
@RestController
public class OrderController {


    @Autowired
    private OrderService orderService;

    @PostMapping("/create_order")
    public String createOrder(@RequestBody  Order order) {
        Map<String,String> result = new HashMap<>();
        orderService.createOrder(order);
        result.put("code", "0");
        result.put("message", "create order success");
        return JSON.toJSONString(result);
    }





}