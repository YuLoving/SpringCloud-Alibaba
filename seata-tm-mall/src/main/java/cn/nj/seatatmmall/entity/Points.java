package cn.nj.seatatmmall.entity;

import com.alibaba.fastjson.JSON;

import java.io.Serializable;

/**
 * @author ：zty
 * @date ：Created in 2021/4/28 15:45
 * @description ：
 */
public class Points implements Serializable {


    private static final long serialVersionUID = -4162342561373672752L;
    private int id;

    private int points;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
