package com.younantuiqun.onlinechargeaccount.service;

import com.younantuiqun.onlinechargeaccount.dao.OcaUserDao;
import com.younantuiqun.onlinechargeaccount.po.OcaUser;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class UserServiceImpl implements UserService{
    @Resource
    OcaUserDao ocaUserDao;
    @Override
    public OcaUser userFindById(String userId) {
        return ocaUserDao.selectUserById(userId);
    }

    @Override
    public boolean changeUserMessage(OcaUser ocaUser) {
        if (ocaUser.getUserId() != null || !ocaUser.getUserId().equals("")){
            ocaUserDao.changeUserInfo(ocaUser);
            return true;
        }
        return false;
    }
}
