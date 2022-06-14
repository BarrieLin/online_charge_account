package com.younantuiqun.onlinechargeaccount.dao;

import com.younantuiqun.onlinechargeaccount.po.Bill;
import com.younantuiqun.onlinechargeaccount.po.MonSumOIncome;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface BillDao {
    //添加账单
    void addNewBill(Bill bill);
    //修改账单
    void changeBill(Bill bill);
    //删除账单
    void deleteBillById(Integer billId);
    //根据账单ID查询单个账单,用于点击某个账单项，显示当前账单
    Bill selectBillById(Integer billId);
    //根据用户id查询所有账单
    List<Bill> selectAllBillsByUserId(String userId);
    //根据时间(年)与用户id查询账单
    List<Bill> selectBillsByYear(@Param("year") Integer year,@Param("userId") String userId);

    //根据时间(年和月)与用户id查询账单
    List<Bill> selectBillsByYearAndMonth(@Param("year") Integer year,@Param("month") Integer month,@Param("userId") String userId);
    //根据时间(年月日)与用户id查询账单
    List<Bill> selectBillsByYearMonthDay(@Param("time")Date time, String userId);

    //根据花销类型（0餐饮、1交通、2旅游等）和用户id查询本月账单收入或支出总和
    Float selectSumMonthBillsByType(@Param("userId")String userId, @Param("checkType")Integer checkType);
    //根据账单类型（0收入、1支出）和用户id，还有年和月查询月账单收入或支出总和
    Float selectSumMonthBillsByTypeAndTime(@Param("userId")String userId, @Param("checkStatus")Integer checkStatus,@Param("year") Integer year,@Param("month") Integer month);
    //根据账单类型（0收入、1支出）和用户id查询至今为止账单收入或支出总和
    Float selectSumBillsByStatus(@Param("userId")String userId, @Param("checkStatus")Integer checkStatus);
    //根据用户id和账单类型（0收入、1支出）查询本年每个月的收入、支出总和账单
    List<MonSumOIncome> selectCurrYearBillsByUserId(@Param("userId")String userId, @Param("checkStatus")Integer checkStatus);

}
