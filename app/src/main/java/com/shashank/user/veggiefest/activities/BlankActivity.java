package com.shashank.user.veggiefest.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.shashank.user.veggiefest.R;
import com.shashank.user.veggiefest.com.fragments.LoginFragment;

public class BlankActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blank);
        //Add a fragment
        getSupportFragmentManager().beginTransaction().add(R.id.replace_activity,new LoginFragment()).commit();
    }
}
