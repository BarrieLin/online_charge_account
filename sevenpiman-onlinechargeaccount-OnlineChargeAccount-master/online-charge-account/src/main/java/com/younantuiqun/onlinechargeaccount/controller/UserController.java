package com.younantuiqun.onlinechargeaccount.controller;

import com.younantuiqun.onlinechargeaccount.po.OcaUser;
import com.younantuiqun.onlinechargeaccount.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

@Controller
public class UserController {
    @Resource
    UserService userService;

    @RequestMapping("/show_user_msg")
    @ResponseBody
    private OcaUser showUserMsg(String userId){
        return userService.userFindById(userId);
    }

    @RequestMapping("/change_user_msg")
    @ResponseBody
    private boolean changeUserMsg(@RequestBody OcaUser ocaUser){
        return userService.changeUserMessage(ocaUser);
    }

}
