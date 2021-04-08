package cn.nj.mynacos;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @author ：zty
 * @date ：Created in 2021/4/6 12:01
 * @description ：
 */
@SpringBootTest(classes = MyNacosApplication.class)
@RunWith(SpringRunner.class)
public class NacosTest {

    @Autowired
    private DiscoveryClient discoveryClient;




    @Test
    public void nacos()  {
        List<ServiceInstance> instanceList = discoveryClient.getInstances("drp-project-service-store-pc");
        System.out.println("当前服务对应的实例个数:"+instanceList.size());
        instanceList.forEach(a->{
            System.out.println("实例地址:"+a.getHost());
            System.out.println("实例端口:"+a.getPort());
            System.out.println("url:"+a.getUri());
            System.out.println("ServiceId:"+a.getServiceId());
            System.out.println("Scheme:"+a.getScheme());
            System.out.println("Metadata:"+a.getMetadata());
        });
    }



}
