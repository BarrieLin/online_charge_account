package com.younantuiqun.onlinechargeaccount.dao;

import com.younantuiqun.onlinechargeaccount.po.Budget;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BudgetDao {
    //添加预算
    void addNewBudget(Budget budget);
    //删除预算
    void editBudget(Budget budget);
    //根据用户id查询所有预算
    List<Budget> selectAllBudgetsByUserId(String userId);
    //根据用户id和时间(月)查询预算
    Budget selectBudgetByMonth(@Param("month") Integer month,@Param("userId") String userId);

}
