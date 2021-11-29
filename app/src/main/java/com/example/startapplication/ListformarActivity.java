package com.example.startapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ListAdapter;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;


import com.example.startapplication.databinding.ActivityListformarBinding;
import com.example.startapplication.databinding.ActivityUserBinding;

import java.util.ArrayList;
//Created by yosef
public class ListformarActivity extends AppCompatActivity {

    ActivityListformarBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityListformarBinding.inflate(getLayoutInflater());
        setContentView(R.layout.activity_listformar);
        setContentView(binding.getRoot());
        int[] imageId={R.drawable.profile_img,R.drawable.app_icon,R.drawable.app_icon,R.drawable.app_icon,R.drawable.app_icon,R.drawable.app_icon};
        String[] name ={"hsam","ahmd","abdllh","mroan","kadm","yosef"};
        String[] lastMessage={"Heye","supp","let`s catchup","Dinner tonight?","Gotta ago","i`m in aeeting"};
        String[] lastmsgTime ={"8:45 pm","9:00 am","7:34 pm","6:32 am","5:56 am","5:00 am"};
        String[] phoneNo ={"0535831032","0545781092","0504831932","0526191085","0595584103","052561333"};
        String[] country ={"Israel","India","Russia","Germany","Canada","United States"};

        ArrayList<User> userArrayList = new ArrayList<>();

        for(int i = 0;i< imageId.length;i++){

            User user = new User(name[i],lastMessage[i],lastmsgTime[i],phoneNo[i],country[i],imageId[i]);
            userArrayList.add(user);

        }

//update
        ListAdapter2 listAdapter = new ListAdapter2(ListformarActivity.this,userArrayList);

        binding.listview.setAdapter(listAdapter);
        binding.listview.setClickable(true);
        binding.listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent i = new Intent(ListformarActivity.this,UserActivity.class);
                i.putExtra("name",name[position]);
                i.putExtra("phone",phoneNo[position]);
                i.putExtra("country",country[position]);
                i.putExtra("imageid",imageId[position]);
                startActivity(i);

            }
        });
    }
}