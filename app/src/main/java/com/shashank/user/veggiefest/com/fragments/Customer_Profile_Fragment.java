package com.shashank.user.veggiefest.com.fragments;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.shashank.user.veggiefest.adapters.MyAdapterRecyclerView_OrderHistory;
import com.shashank.user.veggiefest.R;

public class Customer_Profile_Fragment extends Fragment
{
    ImageView imageView;
    ImageButton imageButtn;
    TextView customerMobno, customerAltMobno,textViewMAp;
    RecyclerView recyclerView;

    String data[];

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_customer__profile_, container, false);

        recyclerView= (RecyclerView) view.findViewById(R.id.recyclerview_oderHistory);
        customerMobno= (TextView) view.findViewById(R.id.textViewMobNum);
        customerAltMobno= (TextView) view.findViewById(R.id.textViewAltNum);
        textViewMAp= (TextView) view.findViewById(R.id.textMap);
        imageView= (ImageView) view.findViewById(R.id.imgvw);
        imageButtn= (ImageButton) view.findViewById(R.id.imgaeButton);

        data=getResources().getStringArray(R.array.coustomers_orders);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        MyAdapterRecyclerView_OrderHistory adapter=new MyAdapterRecyclerView_OrderHistory(getActivity(),data);
        recyclerView.setAdapter(adapter);


        //Listener on imageBUtton  for set the image on imageView
        imageButtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

                Intent intent = new Intent();
                intent.setType("image/*"); // access all image
                intent.setAction(Intent.ACTION_GET_CONTENT);  //get image
                startActivityForResult(intent, 0);
            }
        });
        return  view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        try {
            Uri image =data.getData();  // Uri class is always  used  store class path
            imageView.setImageURI(image);

        } catch (Exception e) {
            e.printStackTrace();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
