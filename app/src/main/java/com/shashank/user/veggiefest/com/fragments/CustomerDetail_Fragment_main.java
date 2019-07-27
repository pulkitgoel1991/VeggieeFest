package com.shashank.user.veggiefest.com.fragments;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.shashank.user.veggiefest.adapters.MyAdapter_CustomerDetail;
import com.shashank.user.veggiefest.model.Pozo_Customer_Deatil;
import com.shashank.user.veggiefest.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class CustomerDetail_Fragment_main extends Fragment
{
    SearchView searchView;
    RecyclerView recyclerView;
    ArrayList<Pozo_Customer_Deatil> arrayList=new ArrayList<Pozo_Customer_Deatil>();


    RequestQueue queue;
    String getUrl="https://thermoscopic-signal.000webhostapp.com/getdata_add_customer_credentilas.php";

    ProgressDialog pd;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_customer_detail_main,container, false);

        recyclerView= view.findViewById(R.id.recyclerView);
        searchView= view.findViewById(R.id.searchview);

        //Add toolbar in fragment ui as ActionBar
        Toolbar toolbar =view.findViewById(R.id.tooBar);
          ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);

        //get Volley Instance
        queue=Volley.newRequestQueue(getContext());

        pd=new ProgressDialog(getContext());
        pd.setTitle("Wait for data...");
        pd.setCancelable(false);
        pd.show();


       //Creating StringReqquest class obj
        StringRequest request=new StringRequest(Request.Method.POST, getUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response)
            {
                try {
                    JSONObject jsonObject=new JSONObject(response);
                    JSONArray  arr=jsonObject.getJSONArray("result");
                    arrayList.clear();
                    for (int i = 0; i < arr.length(); i++) {  // count length

                       JSONObject obj= arr.getJSONObject(i);

                       Pozo_Customer_Deatil pojo=new Pozo_Customer_Deatil();
                       pojo.setName( obj.getString("name"));

                       pojo.setMobileno( obj.getString("mobileno"));

                       arrayList.add(pojo);

                       //arrayListmobno.add(pojo);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                MyAdapter_CustomerDetail adapter=new MyAdapter_CustomerDetail(getActivity(),arrayList);
                recyclerView.setAdapter(adapter);
                pd.dismiss();

            }
        }, new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
        queue.add(request);

        return view;
    }






    ///By this method we add options menu in case of fragment
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

    }
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // add menu on fragment
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.my_menu,menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //Handle click on items
        int id=item.getItemId();
        if (id == R.id.itemAddUser)
        {
            getFragmentManager()
                    .beginTransaction()
                    .addToBackStack("mainfragment")
                    .replace(R.id.replace_activity,new AddCustomer_Credentials_Fragment())
                    .commit();
        }
        return super.onOptionsItemSelected(item);
    }
}
