package com.shashank.user.veggiefest.others;

import android.app.Application;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.util.Base64;
import android.util.Log;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by Ducat on 9/15/2018.
 */

public class MyKey extends Application
{
    @Override
    public void onCreate()
    {
        super.onCreate();

        try {
            PackageInfo info = getPackageManager()
                    .getPackageInfo(
                    "com.shashank.user.veggiefest",
                    PackageManager.GET_SIGNATURES);


            for (Signature signature : info.signatures)
            {
                MessageDigest md = MessageDigest
                        .getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:",
                        Base64.encodeToString(md.digest(),
                        Base64.DEFAULT));
            }
        }
        catch (PackageManager.NameNotFoundException e) {

        }
        catch (NoSuchAlgorithmException e) {

        }
    }
}
