package cn.nj.seatatmmall.entity;

import com.alibaba.fastjson.JSON;
import org.springframework.cloud.openfeign.FeignClient;

import java.io.Serializable;

/**
 * @author ：zty
 * @date ：Created in 2021/4/28 15:46
 * @description ：
 */
public class Storage implements Serializable {

    private static final long serialVersionUID = 508807596140347050L;


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
