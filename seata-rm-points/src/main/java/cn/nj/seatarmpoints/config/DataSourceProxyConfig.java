package cn.nj.seatarmpoints.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * @author ：zty
 * @date ：Created in 2021/4/28 11:33
 * @description ：
 */
@Configuration
public class DataSourceProxyConfig {


    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.hikari")
    public DataSource dataSource(){
        return new HikariDataSource();
    }
}



