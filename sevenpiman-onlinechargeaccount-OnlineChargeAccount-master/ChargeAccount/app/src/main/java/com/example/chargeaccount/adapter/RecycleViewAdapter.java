package com.example.chargeaccount.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.chargeaccount.ChangeBillIncomeActivity;
import com.example.chargeaccount.ChangeBillPayActivity;
import com.example.chargeaccount.R;
import com.example.chargeaccount.entity.OcaBill;

import java.util.ArrayList;

public class RecycleViewAdapter extends RecyclerView.Adapter<RecycleViewAdapter.MyHolder> {
    private ArrayList<OcaBill> mlist;//列表数据
    private Context context;


    public RecycleViewAdapter(Context context, ArrayList<OcaBill> list) {
        mlist=list;
    }


    //创建ViewHolder并返回，后续item布局里控件都是从ViewHolder中取出
    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //将我们自定义的item布局R.layout.item_one转换为View
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.flow_list, parent, false);
        //将view传递给我们自定义的ViewHolder
        MyHolder holder = new MyHolder(view);
        //返回这个MyHolder实体
        return holder;
    }

    //通过方法提供的ViewHolder，将数据绑定到ViewHolder中
    @Override
    public void onBindViewHolder(MyHolder holder, final int position) {

        if (mlist.get(position) != null){
            final OcaBill bill=mlist.get(position);//获取账单对象
            //餐饮（支出）
            if (bill.getCheckType() == OcaBill.catering){
                holder.tv_type.setText("餐饮");
            }
            //交通
            else if (bill.getCheckType() == OcaBill.transportation){
                holder.tv_type.setText("交通");
            }
            //购物
            else if (bill.getCheckType() == OcaBill.shop){holder.tv_type.setText("购物");}
            //医疗
            else if (bill.getCheckType() == OcaBill.medical){holder.tv_type.setText("医疗");}
            //娱乐
            else if (bill.getCheckType() == OcaBill.entertainment){holder.tv_type.setText("娱乐");}
            //学习
            else if (bill.getCheckType() == OcaBill.learning){holder.tv_type.setText("学习");}
            //金融
            else if (bill.getCheckType() == OcaBill.finance){holder.tv_type.setText("金融");}
            //转账
            else if (bill.getCheckType() == OcaBill.transfer){holder.tv_type.setText("转账");}
            //-------------------收入支出分割线--------------------
            //生活费（收入）
            else if (bill.getCheckType() == OcaBill.living_cost){holder.tv_type.setText("生活费");}
            //工资
            else if (bill.getCheckType() == OcaBill.salary){holder.tv_type.setText("工资");}
            //红包
            else if (bill.getCheckType() == OcaBill.red_packet){holder.tv_type.setText("红包");}
            //基金股票
            else if (bill.getCheckType() == OcaBill.equity_funds){holder.tv_type.setText("股票基金");}
            holder.tv_number.setText(bill.getPayMoney() + "");
            holder.tv_date.setText(bill.getTime().split("T")[0]);
//        holder.tv_type.setText(bill.getCheck_type());
//        holder.tv_number.setText(bill.getNumber());
//        holder.tv_date.setText(bill.getDdd());
            if (bill.getCheckStatus() != null){
                if (bill.getCheckStatus()==1){
                    holder.iamge_type.setImageResource(R.drawable.pay_flow);
                    holder.tv_number.setTextColor(Color.parseColor("#EE1909"));
                }else {
                    holder.iamge_type.setImageResource(R.drawable.income_flow);
                    holder.tv_number.setTextColor(Color.parseColor("#36E03C"));
                }
            }

            //每个账单点击事件
            holder.itemView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
//                    if(bill.getCheckStatus() == 1){
//                        Intent intent = new Intent(view.getContext(), ChangeBillPayActivity.class);
//                        Bundle bundle = new Bundle();
//                        bundle.putInt("billId",bill.getBillId());
//                        bundle.putString("userId",bill.getUserId());
//                        bundle.putFloat("payMoney",bill.getPayMoney());
//                        bundle.putString("time",bill.getTime());
//                        bundle.putInt("checkType",bill.getCheckType());
//                        bundle.putString("remark",bill.getRemark());
//                        bundle.putInt("checkStatus",bill.getCheckStatus());
//                        intent.putExtras(bundle);
//                        view.getContext().startActivity(intent);
//    //                    startActivityForResult(intent,1);
//                    }
//                    else {
//                        Intent intent = new Intent(view.getContext(), ChangeBillIncomeActivity.class);
//                        Bundle bundle = new Bundle();
//                        bundle.putInt("billId",bill.getBillId());
//                        bundle.putString("userId",bill.getUserId());
//                        bundle.putFloat("payMoney",bill.getPayMoney());
//                        bundle.putString("time",bill.getTime());
//                        bundle.putInt("checkType",bill.getCheckType());
//                        bundle.putString("remark",bill.getRemark());
//                        bundle.putInt("checkStatus",bill.getCheckStatus());
//                        intent.putExtras(bundle);
//                        view.getContext().startActivity(intent);
//                    }
                    //此处回传点击监听事件
                    if(onItemClickListener!=null){
                        onItemClickListener.OnItemClick(view, bill);
                    }
                    if (longClickLisenter != null) {
                        longClickLisenter.LongClickLisenter(position);
                    }
                }

            });

        }


    }

    public void refresh(ArrayList<OcaBill> list){
        mlist.clear();
        mlist.addAll(list);
        notifyDataSetChanged();
    }

    //获取数据源总的条数
    @Override
    public int getItemCount() {
        return mlist.size();
    }

    /**
     * 自定义的ViewHolder
     */
    class MyHolder extends RecyclerView.ViewHolder {

        TextView tv_type,tv_date,tv_number;
        ImageView iamge_type;

        public MyHolder(View itemView) {
            super(itemView);
            tv_type=itemView.findViewById(R.id.tv_type);
            tv_date=itemView.findViewById(R.id.tv_date);
            tv_number=itemView.findViewById(R.id.tv_number);
            iamge_type=itemView.findViewById(R.id.image_type);
        }
    }


    /**
     * 设置item的监听事件的接口
     */
    public interface OnItemClickListener {
        public void OnItemClick(View view, OcaBill bill);
    }

    //需要外部访问，所以需要设置set方法，方便调用
    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }


    public interface LongClickLisenter {
        void LongClickLisenter(int position);
    }

    private LongClickLisenter longClickLisenter;

    public void setLongClickLisenter(LongClickLisenter longClickLisenter) {
        this.longClickLisenter = longClickLisenter;
    }


    public void del(int i) {
        mlist.remove(i);
        notifyDataSetChanged();
    }
}
