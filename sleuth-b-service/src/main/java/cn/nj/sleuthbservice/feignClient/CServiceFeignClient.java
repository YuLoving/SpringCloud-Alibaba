package cn.nj.sleuthbservice.feignClient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author ：zty
 * @date ：Created in 2021/4/9 10:53
 * @description ：  CServiceFeignClient 通过 OpenFeign 实现了 C 服务的通信客户端，方法名为 methodC。
 */

@FeignClient("c-service")
public interface CServiceFeignClient {

    @GetMapping("/c")
    public String methodC();

}
