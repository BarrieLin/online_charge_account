package com.younantuiqun.onlinechargeaccount;

import com.younantuiqun.onlinechargeaccount.dao.LoginAndRegisterDao;
import com.younantuiqun.onlinechargeaccount.po.OcaUser;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class OnlineChargeAccountApplicationTests {

    @Autowired
    private LoginAndRegisterDao loginAndRegisterDao;

    @Test
    void contextLoads() {
        OcaUser ocaUser = new OcaUser();
        ocaUser.setNickname("1234");
        System.out.println(loginAndRegisterDao.findUserExistOrNotByUserId(ocaUser));
        System.out.println(loginAndRegisterDao.getUserByUserId(ocaUser));
    }

}
