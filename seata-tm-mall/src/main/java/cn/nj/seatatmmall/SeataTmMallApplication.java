package cn.nj.seatatmmall;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class SeataTmMallApplication {

    public static void main(String[] args) {
        SpringApplication.run(SeataTmMallApplication.class, args);
    }

}
