package cn.nj.sentinel.Aspect;

import com.alibaba.csp.sentinel.annotation.aspectj.SentinelResourceAspect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author ：zty
 * @date ：Created in 2021/4/8 15:36
 * @description ： 声明 SentinelResourceAspect，SentinelResourceAspect就是 Sentinel 提供的切面类，用于进行熔断的前置检查。
 */
@Configuration
public class SentinelResourceConfig {

    @Bean
    public SentinelResourceAspect sentinelResourceAspect(){
        return  new SentinelResourceAspect();
    }
}
