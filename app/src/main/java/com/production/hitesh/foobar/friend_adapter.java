package com.production.hitesh.foobar;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by hitesh on 2/26/18.
 */

public class friend_adapter extends ArrayAdapter<Data> {
    private static final String LOG_TAG = friend_adapter.class.getSimpleName();

    public friend_adapter(@NonNull Context context, @NonNull ArrayList<Data> objects) {
        super(context,0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listView = convertView;
        if (listView==null){
            listView = LayoutInflater.from(getContext()).inflate(R.layout.friend_row,parent,false);

        }
        Data current_data = getItem(position);
        TextView name = listView.findViewById(R.id.friend_name);
        name.setText(current_data.getFriend_name());
        TextView username = listView.findViewById(R.id.friend_username);
        username.setText(current_data.getFriend_username());
        CircleImageView profile = listView.findViewById(R.id.ProfileView);






if (current_data.getProfile()!=null) {
    profile.setImageBitmap(current_data.getProfile());
}

        return listView;


    }

}
