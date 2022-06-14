package com.younantuiqun.onlinechargeaccount.controller;


import com.younantuiqun.onlinechargeaccount.service.HelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
    @Autowired
    private HelloService helloService;

    @RequestMapping("testOne")
    public String testOne(){
        return "account system first controller test.\n"+helloService.getTry_1Num();
    }
}
