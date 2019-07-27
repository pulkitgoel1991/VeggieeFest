package com.shashank.user.veggiefest.com.fragments;


import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.shashank.user.veggiefest.adapters.MyAdapterRecyclerView;
import com.shashank.user.veggiefest.adapters.MyAdapterViewPager;
import com.shashank.user.veggiefest.R;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import me.relex.circleindicator.CircleIndicator;


public class Order_By_Category_Fragment extends Fragment
{

    //view pager && circle indiactor
    ViewPager viewPager;
    CircleIndicator circleIndicator;
    View view;

    int currentPage=0;

    RecyclerView recyclerView;
    Integer recyclerImages[]={R.drawable.v1,R.drawable.v2,R.drawable.v3,R.drawable.v4};

    String recyclerString[];

    Integer pic[]={R.drawable.v1,R.drawable.v3,R.drawable.v2,R.drawable.v4};

    ArrayList<Integer> picArray=new ArrayList<Integer>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
         view=inflater.inflate(R.layout.fragment_order__by__category_, container, false);

        //recycler view id

        recyclerView= (RecyclerView) view.findViewById(R.id.recyclerview_orderND);

        recyclerString=getResources().getStringArray(R.array.Category_Array);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        MyAdapterRecyclerView adapterRecyclerView=new MyAdapterRecyclerView(getActivity(),recyclerImages,recyclerString);

        recyclerView.setAdapter(adapterRecyclerView);
        showViewPagerImage();

        return view;
    }

    //method for view pager
    public void showViewPagerImage()
    {

        for(int i =0; i<pic.length; i++)
            picArray.add(pic[i]);

        viewPager= (ViewPager) view.findViewById(R.id.viewpager);
        circleIndicator= (CircleIndicator) view.findViewById(R.id.circleindicator);

        viewPager.setAdapter(new MyAdapterViewPager(getActivity(),picArray));

        circleIndicator.setViewPager(viewPager);


        final Handler handler=new Handler();

        final Runnable update=new Runnable()
        {
            @Override
            public void run()
            {
                if(currentPage==pic.length)
                {
                    currentPage=0;
                }
                viewPager.setCurrentItem(currentPage++,true);
            }
        };

        Timer swipeTimer=new Timer();

        swipeTimer.schedule(new TimerTask()
        {
            @Override
            public void run()
            {
                handler.post(update);

            }
        },3000,3000);
    }



}
