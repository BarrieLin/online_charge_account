package com.younantuiqun.onlinechargeaccount.service;

import com.younantuiqun.onlinechargeaccount.po.Budget;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BudgetService {
    void addBudget(Budget budget);
    void editBudget(Budget budget);
    List<Budget> selectAllBudgetsByUserId(String userId);
    Budget selectBudgetByMonth(Integer month,String userId);
}
