package com.production.hitesh.foobar;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by hitesh on 2/27/18.
 */

public class tab_chat extends android.support.v4.app.Fragment {

    DataBase_Helper dataBase_helper;
    Bitmap profile;
    String last_message=new String();
    String s = new String();
    SharedPreferences sharedPreferences;
    ListView recent_chat;
    Bitmap send_rec;
    ArrayList<Data> chat_list;
    ArrayAdapter chat_adapter;

    Runnable runnable;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
         View view = inflater.inflate(R.layout.tab_chat,container,false);

        dataBase_helper = new DataBase_Helper(getContext());

        recent_chat = view.findViewById(R.id.recent_chat);
        chat_list = new ArrayList<>();

        final Handler handler = new Handler();
        runnable=new Runnable() {
            @Override
            public void run() {
                try {

                        chat_list.clear();
                        Cursor cursor = dataBase_helper.getTable_name();
                        if (cursor.getCount() == 0) {
                            //Nothing
                        } else {
                            sharedPreferences = getActivity().getSharedPreferences("profile", Context.MODE_PRIVATE);


                            while (cursor.moveToNext()) {
                                s = cursor.getString(0);
                                if (s.equals("android_metadata")) {
                                    //System.out.println("Get Metadata");
                                    continue;
                                } else {
                                    String username = s.substring(0, s.indexOf('_'));
                                    String image_string = sharedPreferences.getString(s, "");
                                    profile = decodeToBase64(image_string);
                                    String name = s.substring(s.indexOf('_') + 1);

                                    //getting last message
                                    Cursor last_msg = dataBase_helper.getData(s, 1);

                                    last_message = last_msg.getString(1);
                                    Log.v("last msg", "called");
                                    if (last_msg.getString(0).equals(new static_values().username)) {
                                        send_rec = BitmapFactory.decodeResource(getResources(), R.drawable.ic_done_black_18dp);

                                        chat_list.add(new Data(name, profile, last_message, send_rec, username));


                                    } else {
                                        send_rec = BitmapFactory.decodeResource(getResources(), R.drawable.ic_trending_flat_black_18dp);
                                        chat_list.add(new Data(name, profile, last_message, send_rec, username));


                                    }


                                    Collections.reverse(chat_list);
                                    chat_adapter = new tab_chat_adapter(getContext(), chat_list);
                                    recent_chat.setAdapter(chat_adapter);
                                    recent_chat.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                        @Override
                                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                            TextView text = (TextView) view.findViewById(R.id.chat_name);
                                            String clicked = text.getText().toString();
                                            TextView chat_username = view.findViewById(R.id.chat_username);
                                            static_values.to_whome_username = chat_username.getText().toString().trim();

                                            CircleImageView profile_view = view.findViewById(R.id.chat_View);
                                            profile_view.buildDrawingCache();
                                            static_values.profile = profile_view.getDrawingCache();

                                            static_values.to_whom = clicked;
                                            Intent chat = new Intent(getContext(), chat.class);
                                            chat.putExtra("clicked", clicked);
                                            startActivity(chat);
                                        }
                                    });


                                }
                            }

                    }

                }
                catch (Exception e){
                    Toast.makeText(getActivity(), (CharSequence) e,Toast.LENGTH_SHORT).show();
                }
                finally {
                    handler.postDelayed(this,1000);
                }
            }
        };
        runnable.run();




        return view;


    }

    public static Bitmap decodeToBase64(String input) {
        byte[] decodedByte = Base64.decode(input, 0);
        return BitmapFactory.decodeByteArray(decodedByte, 0, decodedByte.length);
    }
}
