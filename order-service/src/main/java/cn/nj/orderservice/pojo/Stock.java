package cn.nj.orderservice.pojo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author ：zty
 * @date ：Created in 2021/3/22 18:12
 * @description ： 库存商品对象
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Stock {
    /**
     * 商品品类编号
     */
    private Long skuId;
    /**
     * 商品与品类名称
     */
    private String title;
    /**
     * 库存数量
     */
    private Integer quantity;
    /**
     * 单位
     */
    private String unit;

}
