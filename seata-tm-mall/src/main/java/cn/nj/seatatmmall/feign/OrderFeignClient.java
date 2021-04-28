package cn.nj.seatatmmall.feign;

import cn.nj.seatatmmall.entity.Order;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * @author ：zty
 * @date ：Created in 2021/4/28 15:36
 * @description ：
 */
@FeignClient("rm-order")
public interface OrderFeignClient {


    @PostMapping("/create_order")
    public String create(Order order);


}
