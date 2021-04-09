package cn.nj.sleuthbservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class SleuthBServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(SleuthBServiceApplication.class, args);
    }

}
