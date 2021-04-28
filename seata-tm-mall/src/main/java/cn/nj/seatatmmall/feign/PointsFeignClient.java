package cn.nj.seatatmmall.feign;

import cn.nj.seatatmmall.entity.Points;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * @author ：zty
 * @date ：Created in 2021/4/28 15:49
 * @description ：
 */
@FeignClient("rm-points")
public interface PointsFeignClient {

    @PostMapping("/add_points")
    public String add(Points points);



}
