package com.example.chargeaccount.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.chargeaccount.BillIncomFragment;
import com.example.chargeaccount.BillPayFragment;

public class BillPagerAdapter extends FragmentPagerAdapter {
    private String[] mTitles={"支出","收入"};
    public BillPagerAdapter(FragmentManager fm){
        super(fm);
    }
    @NonNull
    @Override
    public Fragment getItem(int position) {
        if (position==0){
            //0 支出界面
            return new BillPayFragment();
        }
        if (position==1){
            //1 收入界面
            return new BillIncomFragment();
        }
        //默认支出界面
        return new BillIncomFragment();
    }

    @Override
    public int getCount() {
        return mTitles.length;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles[position];
    }
}
