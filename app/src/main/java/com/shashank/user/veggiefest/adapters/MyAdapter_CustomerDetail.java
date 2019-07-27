package com.shashank.user.veggiefest.adapters;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.shashank.user.veggiefest.R;
import com.shashank.user.veggiefest.activities.MapsActivity;
import com.shashank.user.veggiefest.activities.Order_NavigationDrawer;
import com.shashank.user.veggiefest.com.fragments.Customer_Profile_Fragment;
import com.shashank.user.veggiefest.model.Pozo_Customer_Deatil;

import java.util.ArrayList;

public class MyAdapter_CustomerDetail extends RecyclerView.Adapter<MyAdapter_CustomerDetail.MyViewHolder>
{
    FragmentActivity fragmentActivity;
    ArrayList<Pozo_Customer_Deatil> arrayList;


    public MyAdapter_CustomerDetail(FragmentActivity fragmentActivity,
                                    ArrayList<Pozo_Customer_Deatil> arrayList ){
        this.fragmentActivity = fragmentActivity;
        this.arrayList = arrayList;


    }

    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
    {
        View view= fragmentActivity.getLayoutInflater().inflate(R.layout.recycler_list_item_customer_detail_fragment,viewGroup,false);
        MyViewHolder holder=new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder myViewHolder, int position)
    {
       Pozo_Customer_Deatil data=arrayList.get(position);

        myViewHolder.textViewName.setText(data.getName());
        myViewHolder.btnLocation.setBackgroundResource(R.drawable.location);  //static
        myViewHolder.btnCall.setBackgroundResource(R.drawable.call);        //static

        myViewHolder.textViewOrder.setText("Order");
        }
    @Override
    public int getItemCount()
    {
        return arrayList.size(); }

    //Inner class
    class MyViewHolder extends RecyclerView.ViewHolder
    {
        TextView textViewName, textViewOrder;
        Button btnCall, btnLocation;

        public MyViewHolder(View itemView)
        {
            super(itemView);

            textViewName= itemView.findViewById(R.id.textViewCutomerNam);
            textViewOrder= itemView.findViewById(R.id.textviewOrder);
            btnCall=itemView.findViewById(R.id.buttonCal);
            btnLocation=itemView.findViewById(R.id.buttonLocat);

            //handle click on user name
                    textViewName.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            fragmentActivity.getSupportFragmentManager()
                           .beginTransaction().replace(R.id.replace_activity,new Customer_Profile_Fragment()).addToBackStack("Customer_Profile_Fragment").commit();
                }
            });
            ///handle click on call button
            btnCall.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {

                    Intent intent = new Intent(Intent.ACTION_DIAL);
                    intent.setData(Uri.parse("tel:8392970703"));
                    fragmentActivity.startActivity(intent);
                }
            });
            // handle click on location button
            btnLocation.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {
                    //Add maps fragmentActivity here
                    Intent intent=new Intent(fragmentActivity,MapsActivity.class);
                    fragmentActivity.startActivity(intent);
                }
            });
            //handle click on seller order
            textViewOrder.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {
                    Intent intent=new Intent(fragmentActivity,Order_NavigationDrawer.class);
                    fragmentActivity.startActivity(intent);
                }
            });
        }
    }


}
