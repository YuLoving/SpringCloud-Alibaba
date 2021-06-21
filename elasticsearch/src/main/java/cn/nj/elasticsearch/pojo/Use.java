package cn.nj.elasticsearch.pojo;

import com.alibaba.fastjson.JSON;

import java.time.LocalDateTime;

/**
 * @author ：zty
 * @date ：Created in 2021/5/27 16:00
 * @description ：
 */

public class Use {

    private Long userId;

    private String name;

    private Integer age;

    private LocalDateTime dateTime=LocalDateTime.now();


    public Use() {
    }

    public Use(Long userId, String name, Integer age) {
        this.userId = userId;
        this.name = name;
        this.age = age;
    }

    public Use(String name, Integer age) {
        this.name = name;
        this.age = age;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
