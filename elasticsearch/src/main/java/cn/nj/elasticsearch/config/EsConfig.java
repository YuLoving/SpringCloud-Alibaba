package cn.nj.elasticsearch.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author ：zty
 * @date ：Created in 2021/5/26 17:42
 * @description ：ES的构建和关闭连接
 */
@Configuration
@Slf4j
public class EsConfig {

 @Bean
    public RestHighLevelClient initEs() {
        log.info("############elasticsearch  init########################");
        //利用RestClient构建ES集群地址
        RestClientBuilder builder = RestClient.builder(new HttpHost("127.0.0.1", 9200, "http")

        );
        return new RestHighLevelClient(builder);

    }
}
