package com.younantuiqun.onlinechargeaccount.service;

import com.younantuiqun.onlinechargeaccount.po.OcaUser;

public interface UserService {
    OcaUser userFindById(String userId);
    boolean changeUserMessage(OcaUser ocaUser);
}
