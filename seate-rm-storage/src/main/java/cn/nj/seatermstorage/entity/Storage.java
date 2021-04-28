package cn.nj.seatermstorage.entity;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

/**
 * @author ：zty
 * @date ：Created in 2021/4/28 14:54
 * @description ：
 */
@TableName(value = "storage")
public class Storage {

    @TableId(value = "goods_id")
    private int id;

    private int quantity;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
