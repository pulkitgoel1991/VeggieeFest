package com.shashank.user.veggiefest.adapters;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.shashank.user.veggiefest.R;

/**
 * Created by User on 2/25/2019.
 */

public class MyAdapterQtySelect extends RecyclerView.Adapter<MyAdapterQtySelect.MyViewHolder>
{
    Activity activity;
    Integer image[];
    String vegname[];

    public MyAdapterQtySelect(Activity activity, Integer[] image, String[] vegname)
    {
        this.activity = activity;
        this.image = image;
        this.vegname = vegname;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view=activity.getLayoutInflater().inflate(R.layout.qtyitemselectfrg_custom_recycler,parent,false);
        MyViewHolder holder=new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position)
    {
        holder.imageView.setImageResource(image[position]);
        holder.textViewVegName.setText(vegname[position]);
        holder.btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

            }
        });

        holder.btnSub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

            }
        });
       // holder.textViewCounter.setText();

    }

    @Override
    public int getItemCount() {
        return image.length;
    }


    public  class MyViewHolder extends RecyclerView.ViewHolder
    {
        ImageView imageView;
        TextView textViewVegName,textViewCounter;
        Button btnAdd,btnSub;


        public MyViewHolder(View itemView)
        {
            super(itemView);
            imageView= (ImageView) itemView.findViewById(R.id.imageview_qtyISFrg);
            textViewVegName= (TextView) itemView.findViewById(R.id.textview_qtyISFrg);
            textViewCounter= (TextView) itemView.findViewById(R.id.textview_CounterqtyIsFrg);
            btnAdd= (Button) itemView.findViewById(R.id.buttonadd_qty);
            btnSub= (Button) itemView.findViewById(R.id.buttonsubtract_qty);

        }
    }
}
