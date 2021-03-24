package cn.nj.orderservice.config;

import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

/**
 * @author ：zty
 * @date ：Created in 2021/3/23 10:29
 * @description ：OpenFeign 默认使用 Java 自带的 URLConnection 对象创建 HTTP 请求，但接入生产时，
 * 如果能将底层通信组件更换为 Apache HttpClient、OKHttp 这样的专用通信组件，
 * 基于这些组件自带的连接池，可以更好地对 HTTP 连接对象进行重用与管理。作为 OpenFeign 目前默认支持 Apache HttpClient 与 OKHttp 两款产品。我以OKHttp配置方式为例，为你展现配置方法。
 */
@Configuration
public class okHttpConfig {


    @Bean
    public OkHttpClient okHttpClient(){
        OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
                //读取超时时间
                .readTimeout(10, TimeUnit.SECONDS)
                //连接超时时间
                .connectTimeout(10, TimeUnit.SECONDS)
                //写超时时间
                .writeTimeout(10, TimeUnit.SECONDS)
                //设置连接池
                .connectionPool(new ConnectionPool())
                .build();
        return okHttpClient;

    }





}
