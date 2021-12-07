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
import com.example.startapplication.classes.Account;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
//Created by yosef
public class ListAdapter2 extends ArrayAdapter<Account> {

    public ListAdapter2(Context context, ArrayList<Account>userArrayList){

        super(context, R.layout.list_item,userArrayList);
    }

   //
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        Account account =getItem(position);

        if (convertView==null){
            convertView= LayoutInflater.from(getContext()).inflate(R.layout.list_item,parent,false);
        }
        ImageView imageView=convertView.findViewById(R.id.profilc_pic);
        TextView userName= convertView.findViewById(R.id.personName);
        TextView lastMsg= convertView.findViewById(R.id.lastMessage);
        TextView time = convertView.findViewById(R.id.msgtime);

        Picasso.get().load(account.getProfileUri()).into(imageView);
      //  imageView.setImageResource(account.getProfileUri());
        userName.setText(account.getName());
        lastMsg.setText(account.getEmail());
        time.setText(account.getId());

        return convertView;
    }
}
