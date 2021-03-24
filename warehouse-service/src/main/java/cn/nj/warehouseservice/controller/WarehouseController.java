package cn.nj.warehouseservice.controller;

import cn.nj.warehouseservice.pojo.Stock;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @author ：zty
 * @date ：Created in 2021/3/22 18:15
 * @description ： 仓储服务控制器
 */
@RestController
public class WarehouseController {

    /**
     * 查询对应 skuId 的库存状况
     * @param skuId skuId
     * @return Stock 库存对象
     */
    @GetMapping("/stock")
    public Stock getStock(Long skuId){
        Map result = new HashMap();
        Stock stock = null;
        if(skuId == 1101L){
            //模拟有库存商品
            stock = new Stock(1101L, "Apple iPhone 11 128GB 紫色", 32, "台");
            stock.setDescription("Apple 11 紫色版对应商品描述");
        }else if(skuId == 1102L){
            //模拟无库存商品
            stock = new Stock(1101L, "Apple iPhone 11 256GB 白色", 0, "台");
            stock.setDescription("Apple 11 白色版对应商品描述");
        }else{
            //演示案例，暂不考虑无对应 skuId 的情况
        }
        return stock;
    }



}
