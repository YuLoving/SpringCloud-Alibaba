package cn.nj.sleuthaservice.feignClient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author ：zty
 * @date ：Created in 2021/4/9 10:57
 * @description ： BServiceFeignClient通过OpenFeign实现了B服务的通信客户端，方法名为methodB
 */

@FeignClient("b-service")
public interface BServiceFeignClient {

    @GetMapping("/b")
    public String methodB();
}
