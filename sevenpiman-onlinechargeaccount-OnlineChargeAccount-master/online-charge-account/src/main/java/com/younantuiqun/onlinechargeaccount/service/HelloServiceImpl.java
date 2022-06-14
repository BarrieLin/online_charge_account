package com.younantuiqun.onlinechargeaccount.service;

import com.younantuiqun.onlinechargeaccount.dao.HelloDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HelloServiceImpl implements HelloService{


    @Autowired
    private HelloDao helloDao;

    public Integer getTry_1Num() {
        return helloDao.findTry_1Num();
    }
}
