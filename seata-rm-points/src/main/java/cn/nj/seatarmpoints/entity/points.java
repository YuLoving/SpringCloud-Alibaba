package cn.nj.seatarmpoints.entity;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

/**
 * @author ：zty
 * @date ：Created in 2021/4/28 11:14
 * @description ：
 */
@TableName(value = "points")
public class points {

    @TableId("member_id")
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
