package com.shashank.user.veggiefest.adapters;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.shashank.user.veggiefest.R;

public class MyAdapterRecyclerView_OrderHistory extends RecyclerView.Adapter<MyAdapterRecyclerView_OrderHistory.MyViewHolder> {
    Activity activity;
    String data[];

    public MyAdapterRecyclerView_OrderHistory(Activity activity, String[] data)
    {
        this.activity = activity;
        this.data = data;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
    {
        View view= activity.getLayoutInflater().inflate(R.layout.recyclerview_orderhistory_items,viewGroup,false);
        MyViewHolder holder=new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int position)
    {
            myViewHolder.textViewOrder.setText(data[position]);
    }

    @Override
    public int getItemCount() {
        return data.length;
    }
    //inner classs

    public class MyViewHolder extends RecyclerView.ViewHolder
    {
        TextView textViewOrder;
        public MyViewHolder(@NonNull View itemView)
        {
            super(itemView);
            textViewOrder= (TextView) itemView.findViewById(R.id.textview_Order);
            }
    }
}
