package cn.nj.orderservicedubbo.controller;

import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import pojo.Stock;
import service.WarehouseService;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author ：zty
 * @date ：Created in 2021/3/23 15:03
 * @description ：
 */
@RestController
public class OrderController {

    /**
     * @DubboReference 注解 该注解用在 Consumer 端，说明 WarehouseService 是 Dubbo Consumer 接口，Spring 会自动生成 WarehouseService 接口的代理实现类，并隐藏远程通信细节，
     */
    @DubboReference
    private WarehouseService warehouseService;


    /**
     * 创建订单业务逻辑
     * @param skuId 商品类别编号
     * @param salesQuantity 销售数量
     * @return
     */
    @GetMapping("/create_order")
    public Map createOrder(Long skuId , Long salesQuantity){
        Map result = new LinkedHashMap();
        //查询商品库存，像调用本地方法一样完成业务逻辑。
        Stock stock = warehouseService.getStock(skuId);
        System.out.println(stock);
        if(salesQuantity <= stock.getQuantity()){
            //创建订单相关代码，此处省略
            //CODE=SUCCESS代表订单创建成功
            result.put("code" , "SUCCESS");
            result.put("skuId", skuId);
            result.put("message", "订单创建成功");
        }else{
            //code=NOT_ENOUGN_STOCK代表库存不足
            result.put("code", "NOT_ENOUGH_STOCK");
            result.put("skuId", skuId);
            result.put("message", "商品库存数量不足");
        }
        return result;
    }

}
