package com.younantuiqun.onlinechargeaccount.controller;

import com.younantuiqun.onlinechargeaccount.po.Bill;
import com.younantuiqun.onlinechargeaccount.po.OcaChart;
import com.younantuiqun.onlinechargeaccount.po.OcaFlow;
import com.younantuiqun.onlinechargeaccount.service.BillService;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.relational.core.sql.In;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@RestController
public class BillController {
    @Resource
    BillService billService;

    //添加账单
    @RequestMapping("oca_add_bill")
    private void addBill(@RequestBody Bill bill){
        billService.addBill(bill);
    }

    //修改账单
    @RequestMapping("oca_change_bill")
    private void changeBill(@RequestBody Bill bill){
        billService.changeBill(bill);
    }

    //删除账单
    @RequestMapping("oca_delete_bill")
    private void deleteBill(Integer billId){
        billService.deleteBill(billId);
    }

    //根据账单ID查询单个账单,用于点击某个账单项，显示当前账单
    @RequestMapping("oca_selectById_bill")
    private Bill selectByIdBill(Integer billId){
        return billService.selectBillById(billId);
    }

    //根据用户id查询所有账单
    @RequestMapping("oca_select_allBills_byUserId")
    private List<Bill> selectUserAllBills(String userId){
        return billService.selectAllBillsByUserId(userId);
    }

    //根据时间(年月日)与用户id查询账单
    @RequestMapping("oca_select_Bills_byYearMonDay")
    private List<Bill> selectUserBillsByYearMonDay(Date time,String userId){
        return billService.selectBillsByYearMonDay(time,userId);
    }

    /*
    * 2021/5/29 by工头
    * */
    //获取图表模块所需数据
    @RequestMapping("oca_chart")
    private OcaChart getChartData(@Param("userId") String userId){
        return billService.getChartData(userId);
    }

    //根据时间(年月日)与用户id查询账单
//    @RequestMapping("oca_select_Bills_byYearMon")
//    private List<Bill> selectUserBillsByYearMon(Integer year, Integer month,String userId){
//        return billService.selectBillsByYearMon(year,month,userId);
//    }

    //获取流水模块所需数据
    @RequestMapping("oca_select_flowData_byYearMon")
    private OcaFlow selectFlowDataByYearMon(@Param("year") Integer year,@Param("month") Integer month,@Param("userId") String userId){
        return billService.selectBillsByYearMonNew(year,month,userId);
    }
}
