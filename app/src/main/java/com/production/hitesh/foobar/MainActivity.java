package com.production.hitesh.foobar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParseUser;

public class MainActivity extends AppCompatActivity {
    EditText usr;
    EditText pass;
    TextView reg;
    Button login;
    static_values static_values;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Parse.initialize(this);

        ParseInstallation.getCurrentInstallation().saveInBackground();
        final ProgressDialog progressDialog = new ProgressDialog(MainActivity.this,R.style.AppCompatAlertDialogStyle);

        static_values = new static_values();

      ///  usr.getBackground().mutate().setColorFilter(getResources().getColor(R.color.edit), PorterDuff.Mode.SRC_ATOP);
      //  pass.getBackground().mutate().setColorFilter(getResources().getColor(R.color.edit), PorterDuff.Mode.SRC_ATOP);

         final SharedPreferences sp=getSharedPreferences("login",MODE_PRIVATE);

        final SharedPreferences.Editor editor=sp.edit();

        if(sp.contains("username") && sp.contains("password") && sp.contains("name")){

            static_values.username = sp.getString("username","");
            static_values.pass = sp.getString("password","");
            static_values.current_name = sp.getString("name","");
            Log.v("Data",static_values.username+static_values.pass);

            startActivity(new Intent(MainActivity.this,current_user.class));
            finish();   //finish current activity
        }

        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        reg = findViewById(R.id.to_reg);
        usr = findViewById(R.id.Edit_username);
        pass = findViewById(R.id.Edit_pass);
        login = findViewById(R.id.login);

        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, Register.class);
                startActivity(i);

            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
if (usr.getText().toString().equals("") || pass.getText().toString().equals("")){

    Toast.makeText(MainActivity.this,"Fill all fields",Toast.LENGTH_SHORT).show();
}
else {
    progressDialog.setMessage("Please wait");
    progressDialog.setTitle("Signing in");
    progressDialog.show();
    new Thread(new Runnable() {
        @Override
        public void run() {
            try{
            ParseUser.logInInBackground(usr.getText().toString().trim(), pass.getText().toString(), new LogInCallback() {
                @Override
                public void done(ParseUser user, ParseException e) {
                    if (user != null) {

                        editor.putString("username", usr.getText().toString().trim());
                        editor.putString("password", pass.getText().toString());
                        editor.putString("name",user.getString("Name"));
                        editor.commit();

                        Intent i = new Intent(MainActivity.this, current_user.class);
                        startActivity(i);
                        progressDialog.cancel();
                    } else {
                        user.logOut();
progressDialog.cancel();;
                        Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });}
            catch (Exception e){

            }


        }
    }).start();

}
            }
        });


    }

}
