package cn.nj.warehouseservice.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;

/**
 * @author ：zty
 * @date ：Created in 2021/4/8 9:39
 * @description ： @Configuration 说明当前 Bean 是一个配置 Bean。是 Spring Boot 自带的 Java Config 注解。
 *         而 @RefreshScope 则用于监听，当 Nacos 推送新的配置后，由这个注解负责接收并为属性重新赋值。
 */
@Configuration
@RefreshScope
public class CustomConfig {

    @Value("${custom.flag}")
    private String flag;


    @Value("${custom.database}")
    private String database;

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getDatabase() {
        return database;
    }

    public void setDatabase(String database) {
        this.database = database;
    }
}
