package com.shashank.user.veggiefest.com.fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.shashank.user.veggiefest.R;


public class Forget_Password_Fragment extends Fragment {

    private EditText edt_email;
    private Button btn_Submit;

    FirebaseAuth auth;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_forget__password_, container, false);
        edt_email=v.findViewById(R.id.editText_Enter_user_Email);


        btn_Submit=v.findViewById(R.id.button_Submit_forget);

        auth=FirebaseAuth.getInstance();
        //Listener on Button click
        btn_Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                try {
                    String mail=edt_email.getText().toString();
                    //validation on btn click
                    if (mail.equals("")) {
                        edt_email.setError("Enter Email_id");
                        return;
                    }
                    auth.getInstance().sendPasswordResetEmail(mail)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        // Log.d(TAG, "Email sent.");
                                        Toast.makeText(getContext(), "Email sent", Toast.LENGTH_SHORT).show();
                                        //go to login fragment
                                        getFragmentManager().beginTransaction()
                                                .replace(R.id.replace_activity,new LoginFragment()).commit();

                                    } else {
                                        Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });


        return v;
    }

}
