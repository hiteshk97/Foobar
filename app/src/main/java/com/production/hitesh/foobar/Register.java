package com.production.hitesh.foobar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.parse.SignUpCallback;

import java.io.ByteArrayOutputStream;

import de.hdodenhof.circleimageview.CircleImageView;

public class Register extends AppCompatActivity {
    EditText usr;
    EditText email;
    EditText pass;
byte[] BYTE=null;

Boolean image_changed = true;
Bitmap image2;
    Button reg;
    EditText name;
    CircleImageView select_pic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Parse.initialize(this);
        final ProgressDialog progressDialog = new ProgressDialog(Register.this,R.style.AppCompatAlertDialogStyle);


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
getSupportActionBar().setElevation(0);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Sign Up");



        select_pic = findViewById(R.id.profile_image);
      select_pic.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {

              Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
              i.putExtra("crop","true");
              i.putExtra("aspectX",1);;
              i.putExtra("aspectY",1);
              i.putExtra("outputX",200);
              i.putExtra("outputY",200);
              i.putExtra("return-data",true);
              startActivityForResult(i,2);

          }
      });



        name = findViewById(R.id.name);
        usr = findViewById(R.id.Edit_username_reg);
        email = findViewById(R.id.Edit_email_reg);
        pass = findViewById(R.id.Edit_pass_reg);
        reg = findViewById(R.id.register);
        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(name.getText().toString().equals("") || email.getText().toString().equals("") || usr.getText().toString().equals("") || pass.getText().toString().equals("") ){
                    Toast.makeText(Register.this,"Fill all fields !",Toast.LENGTH_SHORT).show();
                }
                else{
                    if (image_changed){
                        Toast.makeText(Register.this,"Select Profile Picture !",Toast.LENGTH_SHORT).show();
                    }
                    else{
                progressDialog.setMessage("Please Wait");
                progressDialog.setTitle("Signing Up");

                progressDialog.show();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            final ParseUser user = new ParseUser();
                            user.put("Name",name.getText().toString());
                            user.setEmail(email.getText().toString());
                            user.setUsername(usr.getText().toString());
                            user.setPassword(pass.getText().toString());





                                ParseFile profile = new ParseFile("profile.jpeg", BYTE);
                                Log.v("BYTE Length :", String.valueOf(BYTE.length));


                                user.put("profile", profile);
                                profile.saveInBackground(new SaveCallback() {
                                    @Override
                                    public void done(final ParseException e_save) {
                                        if (e_save == null) {
                                            user.signUpInBackground(new SignUpCallback() {
                                                @Override
                                                public void done(ParseException e_sign) {

                                                    if (e_save == null && e_sign == null) {
                                                        ParseObject database = new ParseObject(usr.getText().toString());
                                                        database.put("friend_username", "");
                                                        database.put("friend_name","");
                                                        database.put("accepted", false);
                                                        database.saveInBackground();


                                                        Intent i = new Intent(Register.this, current_user.class);
                                                        startActivity(i);
                                                        //Register Successful
                                                        //You may choose what to do or display here
                                                    } else {

                                                        Toast.makeText(Register.this, e_sign.getMessage(), Toast.LENGTH_SHORT).show();
                                                        progressDialog.cancel();
                                                    }
                                                }
                                            });


                                        } else {

                                            Toast.makeText(Register.this, e_save.getMessage(), Toast.LENGTH_SHORT).show();
                                            progressDialog.cancel();
                                        }
                                    }
                                });




                        }catch (Exception e){

                        }
                    }
                }).start();}}

            }});}

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==2 && resultCode==RESULT_OK && data!=null){
            Bundle extras = data.getExtras();
            Bitmap image = extras.getParcelable("data");
                      ByteArrayOutputStream bytearrayoutputstream = new ByteArrayOutputStream();
           image.compress(Bitmap.CompressFormat.JPEG,40,bytearrayoutputstream);
           BYTE = bytearrayoutputstream.toByteArray();
         image2 = BitmapFactory.decodeByteArray(BYTE,0,BYTE.length);
         image_changed=false;
           select_pic.setImageBitmap(image2);



        }
    }
}
