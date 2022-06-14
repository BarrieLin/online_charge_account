package com.example.chargeaccount.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.chargeaccount.entity.OcaBill;
import com.example.chargeaccount.entity.OcaFlow;

//发送信息的类监听
public class MainViewModel extends ViewModel {


    public MutableLiveData<Integer> isPush;
//    public MutableLiveData<OcaFlow> ocaFlowMutableLiveData;
//    public MutableLiveData<OcaBill> ocaBillMutableLiveData;



    public MainViewModel(){
        isPush=new MutableLiveData<>();
//        ocaFlowMutableLiveData=new MutableLiveData<>();
//        ocaBillMutableLiveData = new MutableLiveData<>();
    }
}
