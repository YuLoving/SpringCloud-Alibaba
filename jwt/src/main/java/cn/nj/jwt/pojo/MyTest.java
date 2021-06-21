package cn.nj.jwt.pojo;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;

import java.time.LocalDateTime;

/**
 * @author ：zty
 * @date ：Created in 2021/6/21 10:52
 * @description ：
 */
public class MyTest {

    private Long id;

    private String name;

    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime time;

    private Integer age;

    private String address;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
