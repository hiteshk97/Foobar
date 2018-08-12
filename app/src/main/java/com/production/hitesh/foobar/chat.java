package com.production.hitesh.foobar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.io.ByteArrayOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class chat extends AppCompatActivity {
    EditText get_message;
    Button send;
    String message = new String();
    ArrayList<Data> chat_list;
    ArrayAdapter chat_adapter;
    ArrayAdapter recieve_adapter;
    Bitmap profile;
    ArrayList<Data> recieve_lsit;
    ListView chat_view;
    String profile_string = new String();
    Parcelable state;
    SharedPreferences sharedPreferences;
    String time_server = new String();
    String time_phone =new String();
    DataBase_Helper dataBase_helper;
    String clicked;
SharedPreferences.Editor editor;
    Runnable runnable;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final ParseObject message_db = new ParseObject("message");

        Intent i =getIntent();
        clicked = i.getExtras().getString("clicked");
        getSupportActionBar().setTitle(clicked);
        chat_view = findViewById(R.id.chat_list);
        chat_list = new ArrayList<>();
        recieve_lsit= new ArrayList<>();



        sharedPreferences=getSharedPreferences("profile",MODE_PRIVATE);

       editor = sharedPreferences.edit();

        final Handler handler=new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                try{
                    //Bhan ka loda

                    msg_get();

                }catch (Exception e){
                    //catch Exception

                }finally
                    {
                     handler.postDelayed(this,2000);
                    }



            }
        };



        //To retrieve old messages

        dataBase_helper = new DataBase_Helper(this);
        Cursor r = dataBase_helper.getTable_name();
        if (r.getCount()==0){}

        else {
            while (r.moveToNext()) {
                if (r.getString(0).equals(new static_values().to_whome_username + "_" + clicked)){
                    Cursor result = dataBase_helper.getData(new static_values().to_whome_username + "_" + clicked, 0);
                    while (result.moveToNext()) {
                        if (result.getString(0).equals(new static_values().username)) {

                                if (chat_list.isEmpty()) {

                                    chat_list.add(new Data(result.getString(1), result.getString(3)));

                                    chat_adapter = new Chat_adapter(chat.this, chat_list, 1);
                                    chat_view.setAdapter((ListAdapter) chat_adapter);
                                }
                                else{
                                    chat_list.add(new Data(result.getString(1), result.getString(3)));
                                    chat_adapter.notifyDataSetChanged();
                                    chat_view.invalidate();

                                }



                        }
                        else{
                            if (recieve_lsit.isEmpty()) {

                                recieve_lsit.add(new Data(clicked, new static_values().profile, result.getString(3), result.getString(1)));
                                chat_adapter = new Chat_adapter(chat.this, recieve_lsit, 0);
                                chat_view.setAdapter(chat_adapter);

                            }else{
                                recieve_lsit.add(new Data(clicked, new static_values().profile, result.getString(3), result.getString(1)));
                                chat_adapter.notifyDataSetChanged();
                                chat_view.invalidate();
                            }






                        }


                    }

                }
                else{


                    get_message = findViewById(R.id.chatbox);

                    send = findViewById(R.id.send);
                    send.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (get_message.getText().toString().equals("")) {
                                Toast.makeText(chat.this, "Can't send empty message !", Toast.LENGTH_SHORT).show();
                            } else {
                                message = get_message.getText().toString().trim();
                                DateFormat df = new SimpleDateFormat("HH:mm ");
                                df.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
                                time_server = df.format(new Date());
                                Calendar calendar = Calendar.getInstance();
                                if (android.text.format.DateFormat.is24HourFormat(chat.this)) {
                                    Log.v("this", time_server);
                                    df = new SimpleDateFormat("HH:mm ");
                                    time_phone = df.format(calendar.getTime());

                                } else {
                                    df = new SimpleDateFormat("hh:mm a");
                                    time_phone = df.format(calendar.getTime());


                                }
                                if (chat_list.isEmpty()){


                                    chat_list.add(new Data(message, time_phone));
                                    chat_adapter = new Chat_adapter(chat.this, chat_list, 1);
                                    chat_view.setAdapter((ListAdapter) chat_adapter);
                                }
                                   else{
                                    chat_list.add(new Data(message, time_phone));
                                    chat_adapter.notifyDataSetChanged();
                                    chat_view.invalidate();


                                }


                                //inserting into sqlite db
                                dataBase_helper = new DataBase_Helper(chat.this, new static_values().to_whome_username + "_" + clicked, new static_values().version);
                                dataBase_helper.inserData(new static_values().username, message, new static_values().to_whome_username, time_phone, new static_values().to_whome_username + "_" + clicked);

                                //Storing profile photos
                                profile = new static_values().profile;

                                if (sharedPreferences.contains(new static_values().to_whome_username + "_" + clicked)) {

                                } else {
                                    profile_string = encodeToBase64(profile);
                                    editor.putString(new static_values().to_whome_username + "_" + clicked, profile_string);
                                    editor.commit();

                                }
                                final ParseObject message_db = new ParseObject("message");

                                message_db.put("to", new static_values().to_whome_username);
                                message_db.put("from", new static_values().username);
                                message_db.put("message", message);
                                message_db.put("time", time_server);
                                message_db.saveInBackground();
                                runnable.run();

                                get_message.setText("");
                                runnable.run();
                            }


                        }
                    });
                    state = chat_view.onSaveInstanceState();

                }




            }

    }



        runnable.run();


    }






    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // todo: goto back activity from here

               /* Intent intent = new Intent(CurrentActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);*/
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }






    public static String encodeToBase64(Bitmap image) {
        Bitmap immage = image;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        immage.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] b = baos.toByteArray();
        String imageEncoded = Base64.encodeToString(b, Base64.DEFAULT);


        return imageEncoded;
    }







    public void msg_get(){
        ParseQuery<ParseObject> get_msg = new ParseQuery<ParseObject>("message");
        get_msg.whereEqualTo("from",new static_values().to_whome_username);
        get_msg.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                for (ParseObject object:objects) {
                    if(e==null) {
                        if (object.getString("message").equals(null)) {
                        }
                        else {
                            if (recieve_lsit.isEmpty()) {


                                recieve_lsit.add(new Data(clicked, new static_values().profile, object.getString("time"), object.getString("message")));
                                chat_adapter = new Chat_adapter(chat.this, recieve_lsit, 0);
                                chat_view.setAdapter(chat_adapter);
                            }else{
                                recieve_lsit.add(new Data(clicked, new static_values().profile, object.getString("time"), object.getString("message")));
                                chat_adapter.notifyDataSetChanged();
                                chat_view.invalidate();

                            }
                                //recieveView.invalidate();





                            dataBase_helper = new DataBase_Helper(chat.this, new static_values().to_whome_username + "_" + clicked, new static_values().version);
                            dataBase_helper.inserData(object.getString("from"), object.getString("message"), object.getString("to"), object.getString("time"), new static_values().to_whome_username + "_" + clicked);

                            object.deleteInBackground();
                        /*chat_list.add(new Data(clicked, new static_values().profile, object.getString("time"), object.getString("message")));
                        chat_adapter = new Chat_adapter(chat.this, chat_list, 0);
                        chat_view.setAdapter((ListAdapter) chat_adapter);
                        // object.deleteInBackground();
                        chat_view.onRestoreInstanceState(state);*/
                        }
                    }

                }
            }
        });



    }



}
