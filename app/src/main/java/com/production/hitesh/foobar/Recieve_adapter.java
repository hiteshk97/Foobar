package com.production.hitesh.foobar;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by hitesh on 4/1/18.
 */

public class Recieve_adapter extends ArrayAdapter<Data> {


    public Recieve_adapter(@NonNull Context context, @NonNull List<Data> objects) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View chatView = convertView;
        if (chatView==null){
           chatView= LayoutInflater.from(getContext()).inflate(R.layout.message_recieve,parent,false);
        }
        Data currentData = getItem(position);

        TextView name=chatView.findViewById(R.id.text_message_name);
        name.setText(currentData.getFriend_name());


        TextView message=chatView.findViewById(R.id.text_message_body);
        message.setText(currentData.getMessage());

        TextView time =chatView.findViewById(R.id.text_message_time);
        time.setText(currentData.getTime());

        CircleImageView profile = chatView.findViewById(R.id.image_message_profile);
        profile.setImageBitmap(currentData.getProfile());

return chatView;


    }
}
