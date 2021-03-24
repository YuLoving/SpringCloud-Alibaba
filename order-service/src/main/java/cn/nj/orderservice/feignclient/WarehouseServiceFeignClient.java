package cn.nj.orderservice.feignclient;

import cn.nj.orderservice.pojo.Stock;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author ：zty
 * @date ：Created in 2021/3/23 9:12
 * @description ： OpenFeign 通过“接口+注解”形式描述数据传输逻辑，
 */
@FeignClient("warehouse-service")
public interface WarehouseServiceFeignClient {

    /*
     * @FeignClient 注解说明当前接口为 OpenFeign 通信客户端，参数值 warehouse-service 为服务提供者 ID，
     * 这一项必须与 Nacos 注册 ID 保持一致。在 OpenFeign 发送请求前会自动在 Nacos 查询 warehouse-service 所有可用实例信息，
     * 再通过内置的 Ribbon 负载均衡选择一个实例发起 RESTful 请求，进而保证通信高可用。
     */

    /**
     * 声明的方法结构，接口中定义的方法通常与服务提供者的方法定义保持一致。这里有个非常重要的细节：
     * 用于接收数据的 Stock 对象并不强制要求与提供者端 Stock 对象完全相同，消费者端的 Stock 类可以根据业务需要删减属性，
     * 但属性必须要与提供者响应的 JSON 属性保持一致。距离说明，我们在代码发现消费者端 Stock 的包名与代码与提供者都不尽相同，
     * 而且因为消费者不需要 description 属性便将其删除，其余属性只要保证与服务提供者响应 JSON 保持一致，在 OpenFeign 获取响应后便根据 JSON 属性名自动反序列化到 Stock 对象中。
     * @param skuId  skuId
     * @return  Stock
     */
    @GetMapping("/stock")
    public Stock getStock(@RequestParam("skuId") Long skuId);










}
