package service;

import pojo.Stock;

/**
 * @author ：zty
 * @date ：Created in 2021/3/23 15:16
 * @description ：
 */
//Provider接口
public interface WarehouseService {

    //查询库存
    public Stock getStock(Long skuId);
}
