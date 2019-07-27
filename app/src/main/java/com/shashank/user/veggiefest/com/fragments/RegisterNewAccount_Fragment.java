package com.shashank.user.veggiefest.com.fragments;


import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.shashank.user.veggiefest.R;

import java.io.ByteArrayOutputStream;
import java.io.File;

import static android.app.Activity.RESULT_OK;


public class RegisterNewAccount_Fragment extends Fragment {

    private  ImageView imageView;
    private EditText editName,editAadharCard,editPhone,editUserId,editPassword;
    private Button btnRegister;

    private static final int PICK_IMAGE_REQUEST = 0;
    Bitmap photo;
    File finalFile;
    Uri tempUri;

    FirebaseAuth auth;
    // database var
    FirebaseDatabase database;
    DatabaseReference reference;
    // for upload picture
    DatabaseReference dbReference;
    StorageReference stReference;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_register_new_account_, container, false);

        imageView=view.findViewById(R.id.imageview_rgstrnwacct);
        editName=view.findViewById(R.id.editTextName);
        editAadharCard=view.findViewById(R.id.editTextAadhar);
        editPhone=view.findViewById(R.id.editTextphone);
        editUserId= view.findViewById(R.id.editTextUserId);
        editPassword=view.findViewById(R.id.editTextpassword);
        btnRegister=view.findViewById(R.id.buttonRegister);

        ///
        auth=FirebaseAuth.getInstance();

        database=FirebaseDatabase.getInstance();
        reference=database.getReference("User_Credentials");
        //for upload pictre;
        dbReference=FirebaseDatabase.getInstance().getReference("Pictures");
        stReference=FirebaseStorage.getInstance().getReference();

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                try {
                    String name=  editName.getText().toString();
                    String addharcard=  editAadharCard.getText().toString();
                    String phone=  editPhone.getText().toString();

                    String userid=  editUserId.getText().toString().trim();
                    String pasword=  editPassword.getText().toString().trim();

                    //Validation on btn register
                    if (name.equals("") && addharcard.equals("") && phone.equals("") && userid.equals("") && pasword.equals("")) {
                        editName.setError("Enter Your Name");
                        editAadharCard.setError("Enter Addhar Number");
                        editPhone.setError("Enter Mobile Number");
                        editUserId.setError("Enter Your Email Id");
                        editPassword.setError("Enter Your Password");
                        return;
                    }
                    if (addharcard.length() < 12) {
                        editAadharCard.setError("Addhar No. Should be 12 Digits");
                        return;
                    }
                    if (phone.length() < 10) {
                        editPhone.setError("Mob no should be 10 digits");
                        return;
                    }
                    auth.createUserWithEmailAndPassword(userid,pasword).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult)
                        {
                            if (authResult != null) {
                                Toast.makeText(getContext(), "User registered successful", Toast.LENGTH_SHORT).show();
                                // back to login fragment
                               getFragmentManager().beginTransaction().replace(R.id.replace_activity,new LoginFragment()).commit();
                            } else {
                                Toast.makeText(getContext(), "Try again", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getContext(), ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                    //creating pozo class object
                    MyPozo pozo=new MyPozo();

                    pozo.setName(name);
                    pozo.setAddharcard(addharcard);
                    pozo.setMobile(phone);

                    reference.setValue(pozo);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        //Listener on image view
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);//android.provider.MediaStore.ACtion_name_capture
                startActivityForResult(intent,0);

            }
        });
        return view;
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (requestCode == 0 && requestCode == RESULT_OK) {

            photo = (Bitmap) data.getExtras().get("data");
            imageView.setImageBitmap(photo);
            // CALL THIS METHOD TO GET THE URI FROM THE BITMAP
            tempUri = getImageUri(getActivity(), photo);

            // CALL THIS METHOD TO GET THE ACTUAL PATH
            // File finalFile = new File(getRealPathFromURI(tempUri));
            uploadPicrure();

        } else {
            Toast.makeText(getContext(), "something went to wrong", Toast.LENGTH_SHORT).show();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    ///////////////////////////////////////
    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    public String getRealPathFromURI(Uri uri) {
        Cursor cursor = getActivity().getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
        return cursor.getString(idx);
    }


    //Inneer class // Pozo class
    class MyPozo {
        String name;
        String addharcard;
        String mobile;

        public String getName() {
            return name;
        }
        public void setName(String namee) {
            this.name = namee;
        }
        public String getAddharcard() {
            return addharcard;
        }
        public void setAddharcard(String addharcard) {
            this.addharcard = addharcard;
        }
        public String getMobile() {
            return mobile;
        }
        public void setMobile(String mobile) {
            this.mobile = mobile;
        }
    }

    /// upload pics
    public  void uploadPicrure()
    {
        try
        {
            final ProgressDialog pd=new ProgressDialog(getContext());
            pd.show();

            StorageReference ref=stReference.child("Pictures/"+
                    System.currentTimeMillis()+"."+
                    getFileExtension(tempUri));

            ref.putFile(tempUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>()
            {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot)
                {
                    if (taskSnapshot != null)
                    {
                        String url=stReference.getDownloadUrl().toString();
                        UploadPicture picture=new UploadPicture();  //pojo classs
                        picture.setUrl(url);

                        String id= dbReference.push().getKey();
                        dbReference.child(id).setValue(picture);
                        pd.dismiss();
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e)
                {
                    Toast.makeText(getActivity(), ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(UploadTask.TaskSnapshot taskSnapshot)
                {
                    double msg=100*taskSnapshot.getBytesTransferred()/taskSnapshot.getTotalByteCount();
                    pd.setMessage("upload "+msg+"%");
                }
            });
            //pd.dismiss();

        }catch (Exception ee)
        { }

    }
    //get Extensions of files
    public String getFileExtension( Uri uri)
    {
        ContentResolver cR=getActivity().getContentResolver();
        MimeTypeMap mime=MimeTypeMap.getSingleton(); //recognize file type means read file extension

        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

    /////Upload picture
    class UploadPicture
    {
        String Url;
        public String getUrl() {
            return Url;
        }

        public void setUrl(String url) {
            Url = url;

        }
    }
}
