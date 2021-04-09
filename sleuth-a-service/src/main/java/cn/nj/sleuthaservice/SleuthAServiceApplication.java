package cn.nj.sleuthaservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class SleuthAServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(SleuthAServiceApplication.class, args);
    }

}
