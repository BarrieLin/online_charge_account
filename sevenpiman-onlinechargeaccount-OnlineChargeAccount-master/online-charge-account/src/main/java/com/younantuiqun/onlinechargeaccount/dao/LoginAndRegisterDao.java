package com.younantuiqun.onlinechargeaccount.dao;

import com.younantuiqun.onlinechargeaccount.po.OcaUser;
import org.springframework.stereotype.Repository;

@Repository
public interface LoginAndRegisterDao {
    Integer findUserExistOrNotByUserId(OcaUser ocaUser);
    OcaUser getUserByUserId(OcaUser ocaUser);
    void addNewAccount(OcaUser ocaUser);
}
