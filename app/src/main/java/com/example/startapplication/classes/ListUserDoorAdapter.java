package com.example.startapplication.classes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.startapplication.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ListUserDoorAdapter extends ArrayAdapter<DoorUser> {

    public ListUserDoorAdapter(Context context, List<DoorUser> doorUsers){

        super(context, R.layout.list_user_door,doorUsers);
    }

    //
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        DoorUser doorUser =getItem(position);

        if (convertView==null){
            convertView= LayoutInflater.from(getContext()).inflate(R.layout.list_user_door,parent,false);
        }
        TextView userName= convertView.findViewById(R.id.tv_name_door);
        TextView time = convertView.findViewById(R.id.tv_time_door);

        userName.setText(doorUser.getName());
        time.setText(doorUser.getTime());
        return convertView;
    }
}
