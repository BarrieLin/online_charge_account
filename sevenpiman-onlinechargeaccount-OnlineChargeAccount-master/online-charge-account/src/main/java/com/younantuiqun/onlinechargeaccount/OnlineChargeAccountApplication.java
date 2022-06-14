package com.younantuiqun.onlinechargeaccount;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.younantuiqun.onlinechargeaccount.dao")
public class OnlineChargeAccountApplication {

    public static void main(String[] args) {
        SpringApplication.run(OnlineChargeAccountApplication.class, args);
    }

}
