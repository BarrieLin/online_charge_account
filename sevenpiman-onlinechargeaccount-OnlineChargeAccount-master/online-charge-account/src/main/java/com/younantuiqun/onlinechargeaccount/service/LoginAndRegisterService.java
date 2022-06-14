package com.younantuiqun.onlinechargeaccount.service;

import com.younantuiqun.onlinechargeaccount.po.EnterStatus;
import com.younantuiqun.onlinechargeaccount.po.OcaUser;

public interface LoginAndRegisterService {
    EnterStatus judgeStatus(OcaUser ocaUser);
    EnterStatus register(OcaUser ocaUser);
}
