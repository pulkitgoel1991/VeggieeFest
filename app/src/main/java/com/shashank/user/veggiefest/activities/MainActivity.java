package com.shashank.user.veggiefest.activities;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.shashank.user.veggiefest.R;

public class MainActivity extends AppCompatActivity
{
    Animation animation;
    ImageView splashimage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        splashimage= (ImageView) findViewById(R.id.splashimage);
        animation = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.splashanimation);
        //hide status bar
        View decoderView=getWindow().getDecorView();
        int uiOptions=View.SYSTEM_UI_FLAG_FULLSCREEN;

        decoderView.setSystemUiVisibility(uiOptions);

        //hide toolbar(ActionBar)
        //getSupportActionBar().hide();
        splashimage.setAnimation(animation);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run()
            {
                Intent intent=new Intent(MainActivity.this,BlankActivity.class);
                startActivity(intent);
                finish();

            }
        },5000);


    }
}
