package cn.nj.shiro.entity;

import com.alibaba.fastjson.JSON;
import lombok.Data;

import java.io.Serializable;

/**
 * @author ：zty
 * @date ：Created in 2021/4/26 14:18
 * @description ： 角色实体
 */
@Data
public class Role implements Serializable {


    private static final long serialVersionUID = 6015910793906824443L;


    /**
     * 主键
     */
    private Integer id;

    /**
     * 角色
     */
    private String role;

    /**
     * 角色描述
     */
    private String desc;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
