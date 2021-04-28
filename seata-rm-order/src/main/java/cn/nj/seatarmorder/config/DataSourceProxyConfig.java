package cn.nj.seatarmorder.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * @author ：zty
 * @date ：Created in 2021/4/27 17:18
 * @description ：要让 Seata 客户端在处理事务时自动生成反向 SQL，必须额外配置 DataSourceProxy 数据源代理类，
 * DataSourceProxy 是 Seata 提供的 DataSource 代理类，在分布式事务执行过程中，用于自动生成 undo_log 回滚数据，以及自动完成 RM 端分布式事务的提交或回滚操作。
 */
@Configuration
public class DataSourceProxyConfig {


    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.hikari")
    public DataSource druidDataSource() {
        return new HikariDataSource();
    }


}
