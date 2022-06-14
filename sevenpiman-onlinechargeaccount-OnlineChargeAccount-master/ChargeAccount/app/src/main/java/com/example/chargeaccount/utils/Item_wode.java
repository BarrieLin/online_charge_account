package com.example.chargeaccount.utils;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.chargeaccount.R;

/**
 * 自定义一个布局，通过自定义属性设置它的图标和文字,
 * 然后放到一个LinearLayout布局中就可以实现这个列表界面。
 */
public class Item_wode extends LinearLayout {
    private ImageView imageView;//item的图标
    private TextView textView;//item的文字
    private ImageView bottomview;
    private boolean isbootom=true;//是否显示底部的下划线
    public Item_wode(Context context) {
        this(context,null);
    }
    public Item_wode(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,-1);
    }
    public Item_wode(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //加载布局
        LayoutInflater.from(getContext()).inflate(R.layout.item_wode,this);
        //获取设置属性对象
        TypedArray ta=context.obtainStyledAttributes(attrs,R.styleable.ItemView);
        //获取控件，设置属性值
        isbootom=ta.getBoolean(R.styleable.ItemView_show_bottomline,true);
        bottomview=findViewById(R.id.item_bottom);
        imageView=findViewById(R.id.item_img);
        textView=findViewById(R.id.item_text);
        textView.setText(ta.getString(R.styleable.ItemView_show_text));
        imageView.setBackgroundResource(ta.getResourceId(R.styleable.ItemView_show_leftimg,R.drawable.shezhi));
        //回收属性对象
        ta.recycle();
        initview();
    }
    private void initview(){
        //如果isbootom为true，显示下划线，否则隐藏
        if(isbootom){
            bottomview.setVisibility(View.VISIBLE);
        }else{
            bottomview.setVisibility(View.GONE);
        }
    }
}

