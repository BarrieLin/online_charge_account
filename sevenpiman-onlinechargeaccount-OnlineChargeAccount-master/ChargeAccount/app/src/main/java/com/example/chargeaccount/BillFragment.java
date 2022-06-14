package com.example.chargeaccount;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.chargeaccount.adapter.BillPagerAdapter;
import com.google.android.material.tabs.TabLayout;

@SuppressLint("ValidFragment")
public class BillFragment extends Fragment {
    private TabLayout mTablayout;
    private ViewPager mViewPager;
    private TabLayout.Tab bill_income,bill_pay;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView=inflater.inflate(R.layout.fragment_bill,container,false);
        initViews(rootView);
        initEvents(rootView);

        return rootView;
    }
    private void initEvents(View view) {
        mTablayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab == mTablayout.getTabAt(0)) {
                    mViewPager.setCurrentItem(0);
                } else if (tab == mTablayout.getTabAt(1)) {
                    mViewPager.setCurrentItem(1);
                }

            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void initViews(View view) {
        mTablayout = (TabLayout) view.findViewById(R.id.tablayout_bill);
        mViewPager = (ViewPager) view.findViewById(R.id.vp_bill);

        mViewPager.setAdapter(new BillPagerAdapter(getChildFragmentManager()));
        mTablayout.setupWithViewPager(mViewPager);

        //初始化tab
        bill_pay = mTablayout.getTabAt(0); //支出
        bill_income = mTablayout.getTabAt(1);     //收入
        //初始化
        mViewPager.setCurrentItem(0);
    }

}
