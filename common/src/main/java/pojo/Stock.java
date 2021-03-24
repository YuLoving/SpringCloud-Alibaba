package pojo;

import com.alibaba.fastjson.JSON;

import java.io.Serializable;

/**
 * @author ：zty
 * @date ：Created in 2021/3/23 12:20
 * @description ： Dubbo 在对象传输过程中使用了 JDK 序列化，对象必须实现 Serializable 接口。
 */

public class Stock implements Serializable {

    private static final long serialVersionUID = -3140900019003494326L;

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
    /**
     * 描述信息
     */
    private String description;

    //带参构造函数
    public Stock(Long skuId, String title, Integer quantity, String unit) {
        this.skuId = skuId;
        this.title = title;
        this.quantity = quantity;
        this.unit = unit;
    }

    public Long getSkuId() {
        return skuId;
    }

    public void setSkuId(Long skuId) {
        this.skuId = skuId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
