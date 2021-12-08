package com.example.startapplication.ui.main;

import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.startapplication.BusinessMainActivity;
import com.example.startapplication.ListformarActivity;
import com.example.startapplication.LoginActivity;
import com.example.startapplication.R;
import com.example.startapplication.UserMainActivity;
import com.example.startapplication.classes.Account;
import com.example.startapplication.classes.ListAdapter2;
import com.example.startapplication.databinding.ActivityBusinessMainBinding;
import com.example.startapplication.databinding.FragmentBusinessMainBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * A placeholder fragment containing a simple view.
 */
public class PlaceholderFragment extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";

    private PageViewModel pageViewModel;
    private FragmentBusinessMainBinding binding;
    private static final String PREFS_NAME = "LOGIN";
    private static final String DATA_TAG = "KEY";
    private String key;
    private DatabaseReference doorRef = FirebaseDatabase.getInstance().getReference("Doors");
    private DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("Accounts");
    private List<String> doorName=new ArrayList();
    private int counter=0;
    private int maxnumber=99999;


    public static PlaceholderFragment newInstance(int index) {
        PlaceholderFragment fragment = new PlaceholderFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, index);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pageViewModel = new ViewModelProvider(this).get(PageViewModel.class);
        int index = 1;
        if (getArguments() != null) {
            index = getArguments().getInt(ARG_SECTION_NUMBER);
        }
        pageViewModel.setIndex(index);
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        doorName.add("Door_1");
        binding = FragmentBusinessMainBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textView6;
        final ImageView imageView=binding.imageView16;
         imageView.setImageResource(R.drawable.ic_baseline_lock_24);
        final ImageView imageView1 = binding.imageView12;
        final ListView listView=binding.ForemanListView;
        final Button button =binding.button17;
        BusinessMainActivity activity = (BusinessMainActivity) getActivity();
        key = activity.getKey();
        final ArrayList<String> list= new ArrayList<>();
        pageViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {

                if (Objects.equals(s, "1")) {

                    doorRef.child(key).child(doorName.get(0)).child("list").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            Set<String> set = new HashSet<>();
                            Iterator<DataSnapshot> i = dataSnapshot.getChildren().iterator();

                            while (i.hasNext()) {
                                String key=i.next().getKey();
                                set.add((dataSnapshot.child(key)).getValue(String.class));
                            }

                            list.clear();
                            list.addAll(set);
                            ArrayAdapter arrayAdapter = new ArrayAdapter(getContext(),R.layout.listview_business_door, R.id.name_listview_door,  list);
                            listView.setAdapter(arrayAdapter);
                        }
                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

                    imageView1.setVisibility(View.INVISIBLE);
                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                                doorRef.child(key).child(doorName.get(0)).child("status").setValue(true);
                                counter++;
                                doorRef.child(key).child(doorName.get(0)).child("counter").setValue(counter);
                                doorRef.child(key).child(doorName.get(0)).child("list").push().setValue("unknown:"+counter);
                        }
                    });

                    doorRef.child(key).child(doorName.get(0)).child("status").setValue(false);
                    doorRef.child(key).child(doorName.get(0)).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            // This method is called once with the initial value and again
                            // whenever data at this location is updated.
                            boolean value = dataSnapshot.child("status").getValue(boolean.class);
                            counter = dataSnapshot.child("counter").getValue(Integer.class);
                            textView.setText(counter + "/"+maxnumber);
                            Log.d(TAG, "Value is: " + value);
                            if (value) {
                                imageView.setImageResource(R.drawable.ic_baseline_lock_open_24);
                                new android.os.Handler().postDelayed(
                                        new Runnable() {
                                            public void run() {
                                                imageView.setImageResource(R.drawable.ic_baseline_lock_24);
                                                doorRef.child(key).child(doorName.get(0)).child("status").setValue(false);
                                            }
                                        },
                                        5000);
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError error) {
                            // Failed to read value
                            Log.w(TAG, "Failed to read value.", error.toException());
                        }
                    });

                    myRef.child(key).addValueEventListener(new ValueEventListener() {
                        @SuppressLint("SetTextI18n")
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                            Account account=snapshot.getValue(Account.class);
                            if(account!=null) {
                                maxnumber=account.getNumberOfPeople();
                                textView.setText(counter + "/" + maxnumber);
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                    if(maxnumber<=counter)
                    {
                        Toast.makeText(activity, "Full", Toast.LENGTH_SHORT).show();
                        button.setEnabled(false);
                    }

                }
                else {
                    imageView1.setVisibility(View.VISIBLE);
                   textView.setVisibility(View.INVISIBLE);
                    imageView.setVisibility(View.INVISIBLE);
                    button.setVisibility(View.INVISIBLE);
                }

            }
        });


        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}