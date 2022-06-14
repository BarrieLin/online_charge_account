package com.younantuiqun.onlinechargeaccount.controller;

import com.younantuiqun.onlinechargeaccount.po.Budget;
import com.younantuiqun.onlinechargeaccount.service.BudgetService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
public class BudgetController {
    @Resource
    BudgetService budgetService;
    //添加预算
    @RequestMapping("oca_add_budget")
    private void addBudget(@RequestBody Budget budget){
        budgetService.addBudget(budget);
    }
    //修改预算
    @RequestMapping("oca_edit_budget")
    private void editBudget(@RequestBody Budget budget){
        budgetService.editBudget(budget);
    }
    //根据用户id查询所有预算
    @RequestMapping("oca_select_allBudgets_byUserId")
    private List<Budget> selectAllBudgetsByUserId(String userId){
        return budgetService.selectAllBudgetsByUserId(userId);
    }
    //根据用户id和时间(月)查询预算
    @RequestMapping("oca_select_budget_byMonth")
    private Budget selectBudgetByMonth(Integer month,String userId){
        return budgetService.selectBudgetByMonth(month,userId);
    }
}
