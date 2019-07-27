package com.shashank.user.veggiefest.com.fragments;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.shashank.user.veggiefest.R;

import static com.facebook.FacebookSdk.getApplicationContext;


public class LoginFragment extends Fragment
{
    private EditText editUserName,editPassword;
    private TextView textViewRegisterUser;
    private Button btnLogin;
    private TextView textViewForgetPassword;
    private CheckBox checkBox;
    private FirebaseAuth auth;

    //SharedPreference instance var
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    private  String PREFRENCES_NAME="jadu";


    ///for google signin authentication
    private static final String TAG = "MainActivity";
    private static final int RC_SIGN_IN = 0 ;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private com.google.android.gms.common.SignInButton signInButton;
    private GoogleApiClient mGoogleApiClient;
    private Button signOutButton;

    //for facebook authentication
    private CallbackManager callbackManager;
    private AccessTokenTracker accessTokenTracker;
    private ProfileTracker profileTracker;
    LoginButton loginButton;
    FacebookCallback<LoginResult> callback;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_login, container, false);
        editUserName=view.findViewById(R.id.editText_Username);
        editPassword=view.findViewById(R.id.editText_Password);
        textViewRegisterUser=view.findViewById(R.id.textview_newUser);
        textViewForgetPassword=view.findViewById(R.id.textview_forgetpassword);
        btnLogin=view.findViewById(R.id.user_login);
        checkBox=view.findViewById(R.id.checkBox_saved_user_password);

        //for facebook integration
        loginButton =view.findViewById(R.id.login_button);
        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();

       auth= FirebaseAuth.getInstance();
        //Id of sharedFerence
        preferences=getActivity().getSharedPreferences("tufani",Context.MODE_PRIVATE);
        editor=preferences.edit();
        if (preferences.getString("nam",null) !=null &&
                preferences.getString("pwd",null) !=null)
        {
            editUserName.setText(preferences.getString("nam",null));
            editPassword.setText(preferences.getString("pwd",null));
        }

       accessTokenTracker = new AccessTokenTracker()
        {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken accessToken, AccessToken accessToken1) {

            }
        };

        profileTracker = new ProfileTracker() {
            @Override
            protected void onCurrentProfileChanged(Profile profile, Profile profile1) {
            }
        };
        accessTokenTracker.startTracking();
        profileTracker.startTracking();


        callback = new FacebookCallback<LoginResult>()
        {
            @Override
            public void onSuccess(LoginResult loginResult) {

                AccessToken accessToken = loginResult.getAccessToken();
                Profile profile = Profile.getCurrentProfile();
              //  displayMessage(profile);
            }

            @Override
            public void onCancel() {
            }

            @Override
            public void onError(FacebookException e) {
            }
        };

        loginButton.setReadPermissions("user_friends");
        loginButton.registerCallback(callbackManager,
                callback);
        //get ids of google signin button
        signInButton = view.findViewById(R.id.sign_in_button);
        signOutButton =view.findViewById(R.id.sign_out_button);

            //code for google authentication
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(
                GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        //getString(R.string.default_web_client_id)

        mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
                .enableAutoManage(getActivity() , new
                        GoogleApiClient.OnConnectionFailedListener() {
                            @Override
                            public void onConnectionFailed(
                                    ConnectionResult connectionResult) {

                            }
                        } /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                signInButton.setVisibility(View.GONE);
                signOutButton.setVisibility(View.VISIBLE);

            }
        };
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });
        signOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                        new ResultCallback<Status>() {
                            @Override
                            public void onResult(Status status) {
                                signInButton.setVisibility(View.VISIBLE);
                                signOutButton.setVisibility(View.GONE);
                                /*emailTextView.setText(" ".toString());
                                nameTextView.setText(" ".toString());*/
                            }
                        });
            }
            // ..
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                try {
                    String username=editUserName.getText().toString().trim();
                    String userpass=editPassword.getText().toString().trim();
                    //validation for login button
                if (username.equals("")) {
                    editUserName.setError("Enter user Email id");
                    return;
                }
               if (userpass.equals("")) {
                   editPassword.setError("Enter your password");
                   return;
               }
               auth.signInWithEmailAndPassword(username,userpass).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            if (authResult != null) {
                                Toast.makeText(getContext(), "Login Successfull", Toast.LENGTH_SHORT).show();
                                editUserName.setText("");
                                editPassword.setText("");
                                // call  to CustomerDeatil fragment
                                getFragmentManager().beginTransaction().replace(R.id.replace_activity,new CustomerDetail_Fragment_main()).commit();
                            } else {
                                Toast.makeText(getContext(), "Try Again", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getContext(), ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });

                } catch (Exception e) {
                    e.printStackTrace();
                }

                ////////////////////////////////////////////////////////////////////////////////
                //Save id and password
                if (checkBox.isChecked())
                {
                    editor.putString("nam",editUserName.getText().toString());
                    editor.putString("pwd",editPassword.getText().toString());
                    editor.commit();

                    editUserName.setText("");
                    editPassword.setText("");

                }else
                {
                    editor.clear();
                    editor.commit();
                }
            }
        });
        //listener on textViewRegisterUser
        textViewRegisterUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().addToBackStack("LoginFragment")
                        .replace(R.id.replace_activity,new RegisterNewAccount_Fragment()).commit();

            }
        });
        //Listener on textViewForgetPassword
        textViewForgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().replace(R.id.replace_activity,new Forget_Password_Fragment()).commit();
            }
        });
        return view;
    }
    //end of onCreate
    ///code for google integration
    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = result.getSignInAccount();
                firebaseAuthWithGoogle(account);

                //for facebook
                callbackManager.onActivityResult(requestCode,requestCode,data);
            } else {
            }
        }
    }
    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }
    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
            //for facebook
            accessTokenTracker.stopTracking();
            profileTracker.stopTracking();
        }
    }
    @Override
    public void onPause() {
        super.onPause();
        mGoogleApiClient.stopAutoManage(getActivity());
        mGoogleApiClient.disconnect();
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "signInWithCredential:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "signInWithCredential", task.getException());
                            Toast.makeText(getActivity(), "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
