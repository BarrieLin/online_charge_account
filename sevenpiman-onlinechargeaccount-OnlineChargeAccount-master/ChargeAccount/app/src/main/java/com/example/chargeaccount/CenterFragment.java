package com.example.chargeaccount;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.example.chargeaccount.entity.OcaUser;
import com.example.chargeaccount.viewmodel.MainViewModel;
import com.google.gson.Gson;

import java.io.IOException;

import jp.wasabeef.glide.transformations.BlurTransformation;
import jp.wasabeef.glide.transformations.CropCircleTransformation;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static com.example.chargeaccount.temp.TempData.ip;
import static com.example.chargeaccount.temp.TempData.ocaUser;

public class CenterFragment extends Fragment {
    protected View rootView;
    ImageView blurImageView;
    ImageView avatarImageView;
    private Toolbar toolbar;
    private ActionBar actionBar;
    private TextView textView_credit;
    private LinearLayout userMsg;
    private MainViewModel viewModel;
    private TextView nickname;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        rootView=inflater.inflate(R.layout.fragment_center,container,false);
        init();
        initViewModel();
        onClick();

        return rootView;
    }

    private void init() {
        userMsg = rootView.findViewById(R.id.user_msg);
        nickname = rootView.findViewById(R.id.user_val);
        nickname.setText(ocaUser.getNickname());
    }

    private void initViewModel() {
        viewModel=new ViewModelProvider(requireActivity(),new ViewModelProvider.NewInstanceFactory()).get(MainViewModel.class);
        viewModel.isPush.observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                if (integer.equals(R.id.rb_center)){
                    init();
                }
            }
        });
    }


    private void onClick() {
        userMsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(requireActivity(),UserMsgActivity.class);
//                Bundle bundle = new Bundle();
//                bundle.putString("userId",bill.getUserId());
//                bundle.putFloat("payMoney",bill.getPayMoney());
//                bundle.putString("time",bill.getTime());
//                bundle.putInt("checkType",bill.getCheckType());
//                bundle.putString("remark",bill.getRemark());
//                bundle.putInt("checkStatus",bill.getCheckStatus());
                startActivityForResult(i,1);
            }
        });
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //实现个人中心头部磨砂布局
        blurImageView= rootView.findViewById(R.id.iv_blur);
        avatarImageView = rootView.findViewById(R.id.iv_avatar);

        Glide.with(getActivity()).load(R.drawable.pic).bitmapTransform(new BlurTransformation(getActivity(), 25), new CenterCrop(getActivity())).into(blurImageView);
        Glide.with(getActivity()).load(R.drawable.pic).bitmapTransform(new CropCircleTransformation(getActivity())).into(avatarImageView);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 1:
                if (resultCode == Activity.RESULT_OK) {
                    int data_return = data.getIntExtra("data_return",0);
                    viewModel.isPush.postValue(data_return);
                }
                break;
            default:
                ;
        }
    }


}
