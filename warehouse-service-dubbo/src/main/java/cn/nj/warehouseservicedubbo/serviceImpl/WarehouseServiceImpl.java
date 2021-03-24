package cn.nj.warehouseservicedubbo.serviceImpl;


import org.apache.dubbo.config.annotation.DubboService;
import pojo.Stock;
import service.WarehouseService;

import java.util.HashMap;
import java.util.Map;

/**
 * @author ：zty
 * @date ：Created in 2021/3/23 12:28
 * @description ： @DubboService 是 Provider 注解，说明该类所有方法都是服务提供者，加入该注解会自动将类与方法的信息在 Nacos中注册为 Provider。
 */
@DubboService
public class WarehouseServiceImpl implements WarehouseService {
    @Override
    public Stock getStock(Long skuId) {
        Map result = new HashMap();
        Stock stock = null;
        if(skuId == 1101L){
            //模拟有库存商品
            stock = new Stock(1101L, "Apple iPhone 11 128GB 紫色", 32, "台");
            stock.setDescription("Apple 11 紫色版对应商品描述");
        }else if(skuId == 1102L){
            //模拟无库存商品
            stock = new Stock(1102L, "Apple iPhone 11 256GB 白色", 0, "台");
            stock.setDescription("Apple 11 白色版对应商品描述");
        }else{
            //演示案例，暂不考虑无对应skuId的情况
        }
        return stock;
    }

}
