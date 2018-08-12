package com.production.hitesh.foobar;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Adapter;
import android.widget.ListAdapter;
import android.widget.ListView;
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

public class Requests extends AppCompatActivity {
    ArrayList<Request_Data> request_list;
    Adapter request_adapter;
    Bitmap profile;
    Runnable runnable;
    Parcelable state;
    ParseFile image_file;
    ListView request_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_requests);
        request_list = new ArrayList<>();
        request_view = findViewById(R.id.request_list);


        fetch_request();




            }





            public void fetch_request(){

                ParseQuery<ParseObject> get_request = new ParseQuery<ParseObject>(static_values.username);
                get_request.whereEqualTo("accepted",false);
                get_request.findInBackground(new FindCallback<ParseObject>() {
                    @Override
                    public void done(List<ParseObject> objects, ParseException e) {
                        state=request_view.onSaveInstanceState();
                        request_list.clear();
                        if (e==null){
                            for (final ParseObject object : objects){


                                //to get profile picture from user database
                                ParseQuery<ParseUser> get_usr_img = ParseUser.getQuery();
                                get_usr_img.whereEqualTo("username",object.getString("friend_username"));
                                get_usr_img.findInBackground(new FindCallback<ParseUser>() {
                                    @Override
                                    public void done(List<ParseUser> objects, ParseException ex) {

                                        if (ex==null){
                                            for (ParseUser image : objects){
                                                image_file = (ParseFile) image.get("profile");
                                                image_file.getDataInBackground(new GetDataCallback() {
                                                    @Override
                                                    public void done(byte[] data, ParseException ei) {
                                                        if (ei==null){
                                                            profile = BitmapFactory.decodeByteArray(data, 0, data.length);
                                                            request_list.add(new Request_Data(object.getString("friend_username"),object.getString("friend_name"),profile));
                                                            request_adapter = new Request_Adapter(Requests.this,request_list);
                                                            request_view.setAdapter((ListAdapter) request_adapter);
                                                            request_view.onRestoreInstanceState(state);


                                                        }

                                                    }
                                                });



                                            }






                                        }

                                    }
                                });



                            }




                        }
                        else{
                            Toast.makeText(Requests.this,e.getMessage(),Toast.LENGTH_SHORT).show();


                        }
                    }
                });







            }





}
