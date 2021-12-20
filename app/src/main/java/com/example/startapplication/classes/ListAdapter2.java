package com.example.startapplication.classes;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
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
        ImageView star1=convertView.findViewById(R.id.star1);
        ImageView star2=convertView.findViewById(R.id.star2);
        ImageView star3=convertView.findViewById(R.id.star3);
        TextView userName= convertView.findViewById(R.id.personName);
        TextView lastMsg= convertView.findViewById(R.id.lastMessage);
        TextView time = convertView.findViewById(R.id.msgtime);
        ImageView backround=convertView.findViewById(R.id.imgbackround_item_list);

        Picasso.get().load(account.getProfileUri()).resize(512,512).into(imageView);
      //  imageView.setImageResource(account.getProfileUri());
        userName.setText(account.getName());
        lastMsg.setText(account.getEmail());
        time.setText(account.getId());
        if(account.getType()==1)
        {
            star1.setVisibility(View.VISIBLE);
            star2.setVisibility(View.INVISIBLE);
            star3.setVisibility(View.INVISIBLE);
            if(account.getCheckGreen()==0) backround.setImageResource(R.color.yellow);
            else if (account.getCheckGreen()==1) backround.setImageResource(R.color.green);
            else  backround.setImageResource(R.color.red);
        }
        if(account.getType()==2)
        {
            star1.setVisibility(View.VISIBLE);
            star2.setVisibility(View.VISIBLE);
            star3.setVisibility(View.INVISIBLE);
            backround.setVisibility(View.INVISIBLE);
        }
        if(account.getType()==3)
        {
            star1.setVisibility(View.VISIBLE);
            star2.setVisibility(View.VISIBLE);
            star3.setVisibility(View.VISIBLE);
            backround.setVisibility(View.INVISIBLE);
        }

        return convertView;
    }
}
