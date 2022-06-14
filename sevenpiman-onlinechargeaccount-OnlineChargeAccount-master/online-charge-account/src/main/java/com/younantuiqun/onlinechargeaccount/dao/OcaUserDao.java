package com.younantuiqun.onlinechargeaccount.dao;

import com.younantuiqun.onlinechargeaccount.po.OcaUser;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface OcaUserDao {
    OcaUser selectUserById(@Param("userId")String userId);
    void changeUserInfo(OcaUser ocaUser);
}
