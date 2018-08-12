package com.production.hitesh.foobar;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by hitesh on 3/29/18.
 */

public class Chat_adapter extends ArrayAdapter<Data> {
    int i;


    public Chat_adapter(@NonNull Context context, @NonNull List<Data> objects,int i) {
        super(context, 0, objects);
        this.i=i;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View chatView = convertView;
        if (chatView==null){

            chatView = LayoutInflater.from(getContext()).inflate(R.layout.message_send,parent,false);



        }
        ConstraintLayout send =  chatView.findViewById(R.id.send);
        ConstraintLayout recieve = chatView.findViewById(R.id.recieve);
        Data currentData = getItem(position);
        TextView name=chatView.findViewById(R.id.text_message_name);



        TextView message_recieve=chatView.findViewById(R.id.text_body);

        TextView time_recieve =chatView.findViewById(R.id.text_time);


        CircleImageView profile = chatView.findViewById(R.id.image_message_profile);
        TextView  message=chatView.findViewById(R.id.text_message_body);


        TextView  time = chatView.findViewById(R.id.text_message_time);



        if (i==1){
            message.setText(currentData.getMessage());
            time.setText(currentData.getTime());
          recieve.setVisibility(View.GONE);



        message_recieve.setVisibility(View.INVISIBLE);
        time_recieve.setVisibility(View.INVISIBLE);
        name.setVisibility(View.INVISIBLE);
        profile.setVisibility(View.INVISIBLE);



        }
        else if (i==0){
            send.setVisibility(View.GONE);
            name.setText(currentData.getFriend_name());
            message_recieve.setText(currentData.getMessage());
            time_recieve.setText(currentData.getTime());
            profile.setImageBitmap(currentData.getProfile());

            message.setVisibility(View.INVISIBLE);
            time.setVisibility(View.INVISIBLE);





        }

        return chatView;
    }
}
