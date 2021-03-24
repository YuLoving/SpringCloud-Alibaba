package cn.nj.consumerservice.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * @author ：zty
 * @date ：Created in 2021/3/22 17:25
 * @description ：
 */
@Configuration
public class RestTemplateConfig {

    /**
     * //在应用启动时自动执行restTemplate()方法创建RestTemplate对象，其BeanId为restTemplate。
     * @return  RestTemplate
     */
    @Bean
    @LoadBalanced //使RestTemplate对象自动支持Ribbon负载均衡
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }
}
