package com.younantuiqun.onlinechargeaccount.service;

import com.younantuiqun.onlinechargeaccount.dao.BudgetDao;
import com.younantuiqun.onlinechargeaccount.po.Budget;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class BudgetServiceImpl implements BudgetService{
    @Resource
    BudgetDao budgetDao;

    @Override
    public void addBudget(Budget budget) {
        budgetDao.addNewBudget(budget);
    }

    @Override
    public void editBudget(Budget budget) {
        budgetDao.editBudget(budget);
    }

    @Override
    public List<Budget> selectAllBudgetsByUserId(String userId) {
        return budgetDao.selectAllBudgetsByUserId(userId);
    }

    @Override
    public Budget selectBudgetByMonth(Integer month, String userId) {
        return budgetDao.selectBudgetByMonth(month,userId);
    }
}
