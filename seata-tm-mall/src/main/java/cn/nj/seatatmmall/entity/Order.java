package cn.nj.seatatmmall.entity;

import com.alibaba.fastjson.JSON;

import java.io.Serializable;

/**
 * @author ：zty
 * @date ：Created in 2021/4/28 15:43
 * @description ：
 */
public class Order implements Serializable {
    private static final long serialVersionUID = 4253259167249639310L;

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
