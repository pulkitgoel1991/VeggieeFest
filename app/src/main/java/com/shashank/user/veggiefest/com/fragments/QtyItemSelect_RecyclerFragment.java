package com.shashank.user.veggiefest.com.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.shashank.user.veggiefest.adapters.MyAdapterQtySelect;
import com.shashank.user.veggiefest.R;

public class QtyItemSelect_RecyclerFragment extends Fragment
{

    Integer image[]=
                    {R.drawable.camera,R.drawable.fbook,R.drawable.vegetableimg,R.drawable.googlepic,
                    R.drawable.camera,R.drawable.fbook,R.drawable.vegetableimg,R.drawable.googlepic};
    String vegname[];
    RecyclerView recyclerViewQtyFrg;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {


        View view=inflater.inflate(R.layout.fragment_qty_item_select__recycler, container, false);
        //Add toolBar
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();

        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);

        recyclerViewQtyFrg= (RecyclerView) view.findViewById(R.id.recycler_itemselect);

        vegname=getResources().getStringArray(R.array.Seasonal_Vegetable);

        MyAdapterQtySelect adapterQtySelect=new MyAdapterQtySelect(getActivity(),image,vegname);
        recyclerViewQtyFrg.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerViewQtyFrg.setAdapter(adapterQtySelect);
        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // add menu on fragment
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.select_qty_menu_option,menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //Handle click on items
        int id=item.getItemId();
        if (id == R.id.cart) {
           /* getFragmentManager()
                    .beginTransaction()
                    .addToBackStack("mainfragment")
                    .replace(R.id.replace_activity,new checkout_fragment_recyclerview())
                    .commit();*/
        }
        if(id==R.id.addmoreitem)

        {
            Toast.makeText(getActivity(), "add more item", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

}
