package com.younantuiqun.onlinechargeaccount.service;

import com.younantuiqun.onlinechargeaccount.po.Bill;
import com.younantuiqun.onlinechargeaccount.po.OcaChart;
import com.younantuiqun.onlinechargeaccount.po.OcaFlow;

import java.util.Date;
import java.util.List;

public interface BillService {
    /*
     *   记账模块
     * */
    void addBill(Bill bill);
    void changeBill(Bill bill);
    void deleteBill(Integer billId);
    Bill selectBillById(Integer billId);
    /*
     *   流水模块
     * */
    List<Bill> selectAllBillsByUserId(String userId);
    List<Bill> selectBillsByYearMonDay(Date time, String userId);
    List<Bill> selectBillsByYearMon(Integer year, Integer month, String userId);

    OcaFlow selectBillsByYearMonNew(Integer year, Integer month, String userId);

    /*
     *    图表模块
     * */
    //获取图表模块所需数据
    OcaChart getChartData(String userId);


}
