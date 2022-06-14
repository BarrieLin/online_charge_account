package com.younantuiqun.onlinechargeaccount.service;

import com.younantuiqun.onlinechargeaccount.dao.LoginAndRegisterDao;
import com.younantuiqun.onlinechargeaccount.po.EnterStatus;
import com.younantuiqun.onlinechargeaccount.po.OcaUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LoginAndRegisterServiceImpl implements LoginAndRegisterService {

    @Autowired
    LoginAndRegisterDao loginAndRegisterDao;

    @Override
    public EnterStatus judgeStatus(OcaUser ocaUser) {
        System.out.println(ocaUser);
        EnterStatus enterStatus = new EnterStatus();
        if(ocaUser.getUserId()==null)
            enterStatus.setCode(EnterStatus.invalid);
        else {
            Integer exist;
            exist = loginAndRegisterDao.findUserExistOrNotByUserId(ocaUser);
            if(exist==1){
                OcaUser ocaUser1 = loginAndRegisterDao.getUserByUserId(ocaUser);
                if(ocaUser.getPassword().equals(ocaUser1.getPassword()))
                    enterStatus.setCode(EnterStatus.pass);
                else enterStatus.setCode(EnterStatus.wrongPass);
            }
            else{
                enterStatus.setCode(EnterStatus.unnamed);
            }
        }
        return enterStatus;
    }

    @Override
    @Transactional
    public EnterStatus register(OcaUser ocaUser) {
        System.out.println(ocaUser);
        Integer exist;
        EnterStatus enterStatus = new EnterStatus();
        exist = loginAndRegisterDao.findUserExistOrNotByUserId(ocaUser);
        if(exist==1){
            enterStatus.setCode(EnterStatus.named);
        }
        else{
            if(ocaUser.getPassword()==null)enterStatus.setCode(EnterStatus.invalid);
            else{
                loginAndRegisterDao.addNewAccount(ocaUser);
                enterStatus.setCode(EnterStatus.pass);
            }
        }

        return enterStatus;
    }


}
