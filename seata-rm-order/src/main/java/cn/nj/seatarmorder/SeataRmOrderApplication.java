package cn.nj.seatarmorder;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("cn.nj.seatarmorder.mapper")
public class SeataRmOrderApplication {

    public static void main(String[] args) {
        SpringApplication.run(SeataRmOrderApplication.class, args);
    }

}
