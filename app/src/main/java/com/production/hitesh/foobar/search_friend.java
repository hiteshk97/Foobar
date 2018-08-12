package com.production.hitesh.foobar;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

public class search_friend extends AppCompatActivity {
    ImageButton finish;
    TextView text;
    EditText get_usr;
    ArrayList<Data> friend_list;
    Adapter friend_adapter;

Bitmap profile;
    ParseFile file;

boolean usr_found=false;
    ListView friend_search;
    static_values static_values;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_friend);
getSupportActionBar().hide();
finish = findViewById(R.id.back);
finish.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        finish();
    }
});
text = findViewById(R.id.text);
get_usr = findViewById(R.id.searchEditText);



 static_values = new static_values();
        friend_list  = new ArrayList<>();
        friend_search = findViewById(R.id.friend_search);




get_usr.addTextChangedListener(new TextWatcher() {
    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {



    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        if (get_usr.getText().toString().equals("")) {
            friend_list.clear();
            friend_adapter = new friend_adapter(search_friend.this, friend_list);
            friend_search.setAdapter((ListAdapter) friend_adapter);



        }

        else {
            friend_list.clear();

            final ParseQuery<ParseObject> query_name = ParseQuery.getQuery("User");

            final ParseQuery<ParseUser> query_usr = ParseUser.getQuery();
            query_usr.whereStartsWith("username", get_usr.getText().toString().trim());




            query_usr.findInBackground(new FindCallback<ParseUser>() {
                @Override
                public void done(List<ParseUser> objects, ParseException e) {

                    if (get_usr.getText().toString().equals("")) {
                        friend_list.clear();
                        friend_adapter = new friend_adapter(search_friend.this, friend_list);
                        friend_search.setAdapter((ListAdapter) friend_adapter);


                    } else {
                        if (e == null) {


                            for (final ParseUser object : objects) {
                                file = (ParseFile) object.get("profile");
                                file.getDataInBackground(new GetDataCallback() {
                                    @Override
                                    public void done(byte[] data, ParseException e) {
                                        if (e == null) {
                                            profile = BitmapFactory.decodeByteArray(data, 0, data.length);
                                            friend_list.add(new Data(object.getUsername(), object.getString("Name"), profile));


                                            friend_adapter = new friend_adapter(search_friend.this, friend_list);
                                            friend_search.setAdapter((ListAdapter) friend_adapter);



                                        }



                                    }

                                });



                            }
                            friend_list.clear();

                            friend_search.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                                    TextView username = view.findViewById(R.id.friend_username);
                                    TextView current_name = view.findViewById(R.id.friend_name);

                                    String sendTo_user = username.getText().toString().trim();
                                    String senTo_name=current_name.getText().toString().trim();
                                    Log.v("Request : ",sendTo_user + " " + current_name );



                                    ParseObject my_object = new ParseObject(new static_values().username);
                                    my_object.put("friend_username",sendTo_user);
                                    my_object.put("friend_name",senTo_name);
                                    my_object.put("accepted",true);
                                    my_object.saveInBackground();

                                    ParseObject other_obj=new ParseObject(sendTo_user);
                                    other_obj.put("friend_username",new static_values().username);
                                    other_obj.put("friend_name",new static_values().current_name);
                                    other_obj.put("accepted",false);
                                    other_obj.saveInBackground();

                                    Toast.makeText(search_friend.this,"Request sent to : "+sendTo_user,Toast.LENGTH_SHORT).show();



                                }
                            });






                    /*friend_adapter = new friend_adapter(search_friend.this, friend_list);
                    friend_search.setAdapter((ListAdapter) friend_adapter);*/


                        }

                    }
                }
            });

        }
    }
    @Override
    public void afterTextChanged(Editable editable) {


    }
});
    }


   public void getName(){
       friend_list.clear();

       Log.v("called ? " , " yes");
        ParseQuery<ParseUser> query_name = ParseUser.getQuery();
       query_name.whereStartsWith("Name",get_usr.getText().toString().trim());
       query_name.findInBackground(new FindCallback<ParseUser>() {

           @Override
           public void done(List<ParseUser> objects, ParseException e) {

               if (e==null){
                   friend_list.clear();


                   for (ParseUser object : objects){
                       file = (ParseFile) object.get("profile");
                       if (file!=null){
                       file.getDataInBackground(new GetDataCallback() {
                           @Override
                           public void done(byte[] data, ParseException e) {
                               if (e==null){
                                profile = BitmapFactory.decodeByteArray(data,0,data.length);
                                   friend_adapter = new friend_adapter(search_friend.this,friend_list);
                                   friend_search.setAdapter((ListAdapter) friend_adapter);


                           }



                           }
                       });
                           //friend_list.add(new Data(object.getUsername(),object.getString("Name"),profile));

                       }

                       //friend_list.add(new Data(object.getUsername(),object.getString("Name")));

                   }



                   friend_search.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                       @Override
                       public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                           TextView username = view.findViewById(R.id.friend_username);
                           TextView current_name = view.findViewById(R.id.friend_name);

                           String sendTo_user = username.getText().toString().trim();
                           String senTo_name=current_name.getText().toString().trim();
                           Log.v("Request : ",sendTo_user + " " + current_name );



                          ParseObject my_object = new ParseObject(new static_values().username);
                          my_object.put("friend_username",sendTo_user);
                          my_object.put("friend_name",senTo_name);
                          my_object.put("accepted",true);
                          my_object.saveInBackground();

                          ParseObject other_obj=new ParseObject(sendTo_user);
                          other_obj.put("friend_username",new static_values().username);
                          other_obj.put("friend_name",new static_values().current_name);
                          other_obj.put("accepted",false);
                          other_obj.saveInBackground();

                           Toast.makeText(search_friend.this,"Request sent to : "+sendTo_user,Toast.LENGTH_SHORT).show();



                       }
                   });


               }

               Log.v("Size : " , String.valueOf(friend_list.size()));
           }
       });



   }


}
