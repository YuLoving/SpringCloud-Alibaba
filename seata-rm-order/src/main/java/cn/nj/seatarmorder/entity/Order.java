package cn.nj.seatarmorder.entity;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

/**
 * @author ：zty
 * @date ：Created in 2021/4/27 16:56
 * @description ：
 */
@TableName(value = "order")
public class Order {

    /**
     * order_id
     */
    @TableId(value = "order_id")
    private Integer id;

    /**
     * 会员编号
     */
    private Integer memberId;
    /**
     * 商品编号
     */
    private Integer goodsId;
    /**
     * 新增积分
     */
    private Integer points;
    /**
     * 销售数量
     */
    private Integer quantity;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getMemberId() {
        return memberId;
    }

    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
    }

    public Integer getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Integer goodsId) {
        this.goodsId = goodsId;
    }

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
