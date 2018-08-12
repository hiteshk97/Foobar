package com.production.hitesh.foobar;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by hitesh on 3/31/18.
 */

public class tab_chat_adapter extends ArrayAdapter<Data> {
    Context ctx;

    public tab_chat_adapter(@NonNull Context context, @NonNull List<Data> objects) {

        super(context, 0, objects);
        ctx=context;


    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View chatView = convertView;
        if (chatView==null){
            chatView= LayoutInflater.from(getContext()).inflate(R.layout.chat_row,parent,false);
        }
        Data current=getItem(position);
        TextView name = chatView.findViewById(R.id.chat_name);
        name.setText(current.getFriend_name().toString());

        TextView lastmessage = chatView.findViewById(R.id.last_message);
        lastmessage.setText(current.getLast_message().toString());

        CircleImageView profile = chatView.findViewById(R.id.chat_View);
        profile.setImageBitmap(current.getProfile());

        ImageView send_rec = chatView.findViewById(R.id.send_rec);
        send_rec.setImageBitmap(current.getSend_rec());

        TextView username = chatView.findViewById(R.id.chat_username);
        username.setText(current.getUsername().toString());

        return chatView;
    }
}
