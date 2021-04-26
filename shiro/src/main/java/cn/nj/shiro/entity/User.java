package cn.nj.shiro.entity;

import com.alibaba.fastjson.JSON;
import lombok.Data;

import java.io.Serializable;

/**
 * @author ：zty
 * @date ：Created in 2021/4/26 14:14
 * @description ： 用户信息实体
 */
@Data
public class User implements Serializable {


    private static final long serialVersionUID = -1535686291052420064L;

    /**
     * 主键
     */
    private Integer id;

    /**
     * 账户
     */
    private String account;

    /**
     * 密码
     */
    private String password;

    /**
     * 姓名
     */
    private String username;

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
