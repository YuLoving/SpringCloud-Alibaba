package cn.nj.seatarmpoints.controller;

import cn.nj.seatarmpoints.entity.points;
import cn.nj.seatarmpoints.service.pointService;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @author ：zty
 * @date ：Created in 2021/4/28 12:00
 * @description ：
 */
@RestController
public class PointsController {



    @Autowired
    private pointService pointsService;


    @PostMapping("/add_points")
    public String addPoints(@RequestBody  points points) {
        Map<String,String> result = new HashMap<>();
        pointsService.update(points);
        result.put("code", "0");
        result.put("message", "add points success");
        return JSON.toJSONString(result);
    }

}
