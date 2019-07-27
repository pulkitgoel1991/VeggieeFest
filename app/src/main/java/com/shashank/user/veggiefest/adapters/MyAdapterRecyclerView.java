package com.shashank.user.veggiefest.adapters;

import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.shashank.user.veggiefest.R;
import com.shashank.user.veggiefest.com.fragments.QtyItemSelect_RecyclerFragment;

public class MyAdapterRecyclerView extends RecyclerView.Adapter<MyAdapterRecyclerView.MyViewHolder>
{
    FragmentActivity activity;
    Integer recyclerImages[];
    String recyclerString[];




    public MyAdapterRecyclerView(FragmentActivity activity, Integer[] recyclerImages, String[] recyclerString) {
        this.activity = activity;
        this.recyclerImages = recyclerImages;
        this.recyclerString = recyclerString;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View v=activity.getLayoutInflater().inflate(R.layout.customrecyclerview_order_nd,parent,false);
        MyViewHolder holder=new MyViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position)
    {

        holder.imageView.setImageResource(recyclerImages[position]);
        holder.textView.setText(recyclerString[position]);
    }

    @Override
    public int getItemCount()
    {
        return recyclerImages.length;
    }

    //inner class to hold the id of imageview and textview
    class MyViewHolder extends RecyclerView.ViewHolder
    {
        ImageView imageView;
        TextView textView;
        LinearLayout linearLayout;
        //CardView cardView;

        public MyViewHolder(View itemView)
        {
            super(itemView);
            imageView= (ImageView) itemView.findViewById(R.id.imageview_RecyclerView_OrdeNvgtnDrwr);
            textView= (TextView) itemView.findViewById(R.id.text_RecyclerView_OrdeNvgtnDrwr);
            linearLayout= (LinearLayout) itemView.findViewById(R.id.linearlayout_cardview);
            //cardView=itemView.findViewById(R.id.cardview_order);

            linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (getLayoutPosition() == 0) {
                        activity.getSupportFragmentManager()
                                .beginTransaction().replace(R.id.content_main,new QtyItemSelect_RecyclerFragment())
                                .addToBackStack("Customer_Profile_Fragment").commit();
                        Toast.makeText(activity, "0 possition", Toast.LENGTH_SHORT).show();
                    } else  if (getLayoutPosition() == 1) {
                        Toast.makeText(activity, "1st position", Toast.LENGTH_SHORT).show();
                    } else if (getLayoutPosition() == 2) {
                        Toast.makeText(activity, "2nd position", Toast.LENGTH_SHORT).show();
                    } else  {
                        Toast.makeText(activity, "3rd position", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

}
