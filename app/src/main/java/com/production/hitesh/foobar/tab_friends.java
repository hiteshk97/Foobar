package com.production.hitesh.foobar;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.github.clans.fab.FloatingActionButton;
import com.parse.FindCallback;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by hitesh on 2/27/18.
 */

public class tab_friends extends android.support.v4.app.Fragment {
    EditText get_usr;
    ArrayList<Data> friend_list;
    SwipeRefreshLayout refresh;
    Adapter friend_adapter;
    ParseFile img_file;
FloatingActionButton fab1;
    Parcelable state;
    SwipeRefreshLayout refreshLayout;
    Bitmap profile;
    ListView friend_view;
    static_values static_values;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view =  inflater.inflate(R.layout.tab_friend,container,false);

        static_values = new static_values();
        friend_list  = new ArrayList<>();
        friend_view = view.findViewById(R.id.friend_list);

        refreshLayout = view.findViewById(R.id.friend_refesh);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                get_friends();
            }
        });

        get_friends();















fab1 = view.findViewById(R.id.fab_find);
fab1.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        Intent i = new Intent(getContext(),search_friend.class);
        startActivity(i);
    }
});





        return view;

    }
    public void get_friends(){
        friend_list.clear();
        ParseQuery<ParseObject> get_friend = new ParseQuery<ParseObject>(static_values.username);
        get_friend.whereEqualTo("accepted",true);
        get_friend.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                //state=friend_view.onSaveInstanceState();


                if (e==null) {
                    for (final ParseObject object : objects) {
                        state=friend_view.onSaveInstanceState();
                        ParseQuery<ParseUser> get_usr_img = ParseUser.getQuery();
                        get_usr_img.whereEqualTo("username",object.getString("friend_username"));
                        get_usr_img.findInBackground(new FindCallback<ParseUser>() {
                            @Override
                            public void done(List<ParseUser> objects, ParseException ex) {
                                if (ex==null){
                                    state=friend_view.onSaveInstanceState();
                                    for (ParseUser img : objects){
                                        state=friend_view.onSaveInstanceState();

                                        img_file = (ParseFile) img.get("profile");
                                        img_file.getDataInBackground(new GetDataCallback() {
                                            @Override
                                            public void done(byte[] data, ParseException ei) {
                                                if (ei==null){
                                                    profile = BitmapFactory.decodeByteArray(data, 0, data.length);
                                                    friend_list.add(new Data(object.getString("friend_username"),object.getString("friend_name"),profile));

                                                    friend_adapter = new friend_adapter(getContext(),friend_list);
                                                    friend_view.setAdapter((ListAdapter) friend_adapter);
                                                    friend_view.onRestoreInstanceState(state);

                                                }

                                            }
                                        });

                                    }

                                }

                            }
                        });
                        refreshLayout.setRefreshing(false);


                    }





                    friend_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            TextView text = (TextView) view.findViewById(R.id.friend_name);
                            String clicked = text.getText().toString();
                            TextView usrnme = view.findViewById(R.id.friend_username);
                            static_values.to_whome_username = usrnme.getText().toString().trim();

                            CircleImageView profile_view = view.findViewById(R.id.ProfileView);
                            profile_view.buildDrawingCache();
                            static_values.profile=profile_view.getDrawingCache();

                            static_values.to_whom=clicked;
                            Intent chat = new Intent(getContext(),chat.class);
                            chat.putExtra("clicked",clicked);
                            startActivity(chat);
                        }
                    });

                }

            }
        });


    }


}
