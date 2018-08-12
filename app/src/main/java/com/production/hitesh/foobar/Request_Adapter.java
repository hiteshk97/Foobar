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
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by hitesh on 3/23/18.
 */

public class Request_Adapter extends ArrayAdapter<Request_Data>{
    Context ctx;
    List<Request_Data> request;


    public Request_Adapter(@NonNull Context context, @NonNull List<Request_Data> objects) {

        super(context, 0, objects);
        ctx=context;
        request=objects;

    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable final View convertView, @NonNull ViewGroup parent) {
       View listView = convertView;

        if (listView==null){

            listView = LayoutInflater.from(getContext()).inflate(R.layout.request_row,parent,false);
        }
        final Request_Data current_data = getItem(position);

        final TextView username = listView.findViewById(R.id.request_username);
        username.setText(current_data.getUsername());

        final TextView name = listView.findViewById(R.id.request_name);
        name.setText(current_data.getName());

        CircleImageView profile = listView.findViewById(R.id.request_profile);
        profile.setImageBitmap(current_data.getProfie());

        ImageView yes = listView.findViewById(R.id.accept);

        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              String s = username.getText().toString().trim();
                ParseQuery<ParseObject> accept = new ParseQuery<ParseObject>(new static_values().username);
                accept.whereEqualTo("friend_username",s);
                accept.findInBackground(new FindCallback<ParseObject>() {
                    @Override
                    public void done(List<ParseObject> objects, ParseException e) {
                        if (e==null){
                            for (ParseObject object : objects){
                                object.put("accepted",true);
                                object.saveInBackground();
                            }
                            Toast.makeText(ctx,"Request Accepted",Toast.LENGTH_SHORT).show();
                            request.remove(position);
                            notifyDataSetChanged();

                        }
                        else {
                            Toast.makeText(ctx,e.getMessage(),Toast.LENGTH_SHORT).show();
                        }

                    }
                });



            }
        });

        ImageView no = listView.findViewById(R.id.reject);
        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String s = username.getText().toString().trim();
                ParseQuery<ParseObject> reject = new ParseQuery<ParseObject>(new static_values().username);
                reject.whereEqualTo("friend_username",s);
                reject.findInBackground(new FindCallback<ParseObject>() {
                    @Override
                    public void done(List<ParseObject> objects, ParseException e) {
                        if (e==null){
                            for (ParseObject object : objects){
                                object.deleteInBackground();
                                object.saveInBackground();
                            }
                            Toast.makeText(ctx,"Request Cancelled",Toast.LENGTH_SHORT).show();
                            notifyDataSetChanged();
                        }
                        else
                        {
                            Toast.makeText(ctx,e.getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    }
                });


            }
        });
        return listView;

    }


}
