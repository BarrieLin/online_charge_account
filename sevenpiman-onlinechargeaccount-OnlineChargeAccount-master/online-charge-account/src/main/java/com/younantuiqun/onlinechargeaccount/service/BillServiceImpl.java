package com.younantuiqun.onlinechargeaccount.service;

import com.younantuiqun.onlinechargeaccount.dao.BillDao;
import com.younantuiqun.onlinechargeaccount.dao.BudgetDao;
import com.younantuiqun.onlinechargeaccount.po.Bill;
import com.younantuiqun.onlinechargeaccount.po.MonSumOIncome;
import com.younantuiqun.onlinechargeaccount.po.OcaChart;
import com.younantuiqun.onlinechargeaccount.po.OcaFlow;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class BillServiceImpl implements BillService {

    @Resource
    BillDao billDao;


    /*
     *   记账模块
     * */
    @Override
    public void addBill(Bill bill) {
        billDao.addNewBill(bill);
    }

    @Override
    public void changeBill(Bill bill) {
        billDao.changeBill(bill);
    }

    @Override
    public void deleteBill(Integer billId) {
        billDao.deleteBillById(billId);
    }

    @Override
    public Bill selectBillById(Integer billId) {
        return billDao.selectBillById(billId);
    }

    /*
     *   流水模块
     * */
    @Override
    public List<Bill> selectAllBillsByUserId(String userId) {
        return billDao.selectAllBillsByUserId(userId);
    }

    //未用上
    @Override
    public List<Bill> selectBillsByYearMonDay(Date time, String userId) {
        return billDao.selectBillsByYearMonthDay(time,userId);
    }

    @Override
    public List<Bill> selectBillsByYearMon(Integer year, Integer month, String userId) {
        return billDao.selectBillsByYearAndMonth(year, month, userId);
    }

    @Override
    public OcaFlow selectBillsByYearMonNew(Integer year, Integer month, String userId) {
        OcaFlow ocaFlow = new OcaFlow();
        ocaFlow.setBillList(billDao.selectBillsByYearAndMonth(year, month, userId));
//        ocaFlow.setBudget(budgetDao.selectBudgetByMonth(month ,userId));
        ocaFlow.setMonthIncome(billDao.selectSumMonthBillsByTypeAndTime(userId,0,year,month));
        ocaFlow.setMonthOutcome(billDao.selectSumMonthBillsByTypeAndTime(userId,1,year,month));
        return ocaFlow;
    }

    /*
     *    图表模块
     * */
    @Override
    public OcaChart getChartData(String userId) {
        OcaChart ocaChart = new OcaChart();
        ocaChart.setUserId(userId);
        //收入总和
        if (billDao.selectSumBillsByStatus(userId,0) == null)
            ocaChart.setCurrentIncome((float) 0);
        else
            ocaChart.setCurrentIncome(billDao.selectSumBillsByStatus(userId,0));

        //支出总和
        if (billDao.selectSumBillsByStatus(userId,1) == null)
            ocaChart.setCurrentOutcome((float) 0);
        else
            ocaChart.setCurrentOutcome(billDao.selectSumBillsByStatus(userId,1));

        //本年每个月收入列表，用于显示折线图
        List<MonSumOIncome> monSumIncomes = billDao.selectCurrYearBillsByUserId(userId, 0);
        ocaChart.setListCurrentMonthIncome(monSumIncomes);
        //本年每个月支出总和列表，用于显示折线图
        List<MonSumOIncome> monSumOutncomes = billDao.selectCurrYearBillsByUserId(userId, 1);
        ocaChart.setListCurrentMonthOutcome(monSumOutncomes);

        //本月每个类型支出总和列表，用于显示饼状图
        List<Float> outcomeList = new ArrayList<>();
        for (int i = 0;i < 11;i++){
            if (billDao.selectSumMonthBillsByType(userId,i) == null)
                outcomeList.add((float) 0);
            else
                outcomeList.add(billDao.selectSumMonthBillsByType(userId,i));
        }
        ocaChart.setListCurrMonthTypeOutcome(outcomeList);
        return ocaChart;
    }




}
