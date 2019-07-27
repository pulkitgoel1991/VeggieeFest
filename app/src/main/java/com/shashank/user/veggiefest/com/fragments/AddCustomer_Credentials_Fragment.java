package com.shashank.user.veggiefest.com.fragments;


import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.shashank.user.veggiefest.R;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
public class AddCustomer_Credentials_Fragment extends Fragment
{
    private EditText editText_Name, editText_Mobilenuber, editText_Email, editText_Aadhar;
    private TextView textViewAdrress;
    private double lat;
    private double longi;

    private Button btnSave;
    LocationManager lm;

    private RequestQueue queue;
    private String setUrl="https://thermoscopic-signal.000webhostapp.com/setdata_add_customer_credentials.php";

    private ProgressDialog pd;
    boolean gpsStatus;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_customer__credentials_, container, false);
        // Getting Id's
        editText_Name = view.findViewById(R.id.editTextName_add);
        editText_Mobilenuber = view.findViewById(R.id.editTextMobileNumber_add);
        editText_Email = view.findViewById(R.id.editTextEmail_add);
        editText_Aadhar = view.findViewById(R.id.editTextAadhar_add);
        textViewAdrress = view.findViewById(R.id.textviewAddress_add);
        btnSave = view.findViewById(R.id.buttonSave);

        //progress Dialog Instance
        pd = new ProgressDialog(getActivity());
        pd.setTitle("Searching location");
        pd.setCanceledOnTouchOutside(false);


        //Create Volley Instance
        queue = Volley.newRequestQueue(getActivity()); //data ko server pr leke jana

        //runtime permission
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION}, 0);
            return view;
        }
        lm = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        //Check GPS enabled or not
        gpsStatus = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
        if(gpsStatus == true)
        {
            pd.show();
        }else {

            final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
            alertDialogBuilder.setTitle("GPS is settings");
            alertDialogBuilder.setMessage("GPS is not enabled.Do you want to go to settings menu?");
            alertDialogBuilder.setPositiveButton("Settings", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //open settings for GPS enabled/disabled
                    Intent intent1 = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    startActivity(intent1);
                    pd.show();
                }
            });
            alertDialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        }

        lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                lat = location.getLatitude();
                longi= location.getLongitude();
                //get actual address From latitute and longitute using Geocoder class
                try {
                Geocoder geocoder = new Geocoder(getActivity());


                    List<Address> list = geocoder.getFromLocation(lat, longi, 1);

                   // String country = list.get(0).getCountryName();
                    //String loaclity = list.get(0).getLocality();
                   // String pinCode = list.get(0).getPostalCode();
                    String area = list.get(0).getAddressLine(0);

                   //textViewAdrress.setText("\n" + country + "\n" + loaclity + "\n" + pinCode + "\n" + area);
                    textViewAdrress.setText(area);
                    pd.dismiss();

                } catch (Exception ee) { }
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) { }

            @Override
            public void onProviderEnabled(String provider) { }

            @Override
            public void onProviderDisabled(String provider) { }
        });
        // Listener on btnSave for send data on server
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                try {
                    final String nam = editText_Name.getText().toString();
                    final String mob = editText_Mobilenuber.getText().toString();
                    final String eml = editText_Email.getText().toString();
                    final String adr= editText_Aadhar.getText().toString();
                    final String adrs= textViewAdrress.getText().toString();

                    // clear all entries
                    editText_Name.setText("");
                    editText_Mobilenuber.setText("");
                    editText_Email.setText("");
                    editText_Aadhar.setText("");

                    StringRequest request=new StringRequest(Request.Method.POST, setUrl, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response)
                        {
                                //Log.d("response",response);
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error)
                        {
                           // Log.d("Error", String.valueOf(error));
                        }
                    })
                    {
                     //init block
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError
                        {
                            Map<String,String> map=new HashMap<String,String>();
                            map.put("name", nam);
                            map.put("mobileno", mob);
                            map.put("email", eml);
                            map.put("aadharno", adr);
                            map.put("address", adrs);
                            map.put("latitute", String.valueOf(lat));
                            map.put("longitute", String.valueOf(longi));
                            return map;
                        }
                    };
                    //Validations of credentilas
                    if (nam.equals("") && mob.equals("") && eml.equals("") && adr.equals(""))  {

                        editText_Name.setError("Enter your Name");
                        editText_Mobilenuber.setError("Enter mobile no");
                        editText_Email.setError("Enter E-mail");
                        editText_Aadhar.setError("Enter Aadhar Number");
                        return;
                    }

                    if (mob.length() < 10 )
                    {
                        editText_Mobilenuber.setError("Mob no. must be 10 digits");
                       return;
                    }
                    if (adr.length() < 12)
                    {
                        editText_Aadhar.setError("Aadhar no. must be 12 digits");
                        return;
                    }

                    queue.add(request);


                    Toast.makeText(getActivity(), "Data Saved", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        return view;
    }
}
