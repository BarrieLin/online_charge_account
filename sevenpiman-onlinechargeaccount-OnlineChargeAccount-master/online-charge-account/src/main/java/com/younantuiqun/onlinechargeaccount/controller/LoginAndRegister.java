package com.younantuiqun.onlinechargeaccount.controller;

import com.younantuiqun.onlinechargeaccount.po.EnterStatus;
import com.younantuiqun.onlinechargeaccount.po.OcaUser;
import com.younantuiqun.onlinechargeaccount.service.LoginAndRegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginAndRegister {
    @Autowired
    LoginAndRegisterService loginAndRegisterService;

    @RequestMapping("oca_login")
    public EnterStatus enterJudge(@RequestBody OcaUser ocaUser){
        return loginAndRegisterService.judgeStatus(ocaUser);
    }
    
    @RequestMapping("oca_register")
    public EnterStatus registerJudge(@RequestBody OcaUser ocaUser){
        return loginAndRegisterService.register(ocaUser);
    }
}
