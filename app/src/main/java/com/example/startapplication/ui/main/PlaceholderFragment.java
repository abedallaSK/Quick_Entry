package com.example.startapplication.ui.main;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.startapplication.LoginActivity;
import com.example.startapplication.R;
import com.example.startapplication.databinding.ActivityBusinessMainBinding;
import com.example.startapplication.databinding.FragmentBusinessMainBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class PlaceholderFragment extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";

    private PageViewModel pageViewModel;
    private FragmentBusinessMainBinding binding;
    private static final String PREFS_NAME = "LOGIN";
    private static final String DATA_TAG = "KEY";
    private String email="222";
    private DatabaseReference doorRef = FirebaseDatabase.getInstance().getReference("Doors");
    private List<String> doorName=new ArrayList();
    private int counter;


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
        final ListView listView = binding.ForemanListView;
        pageViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String[]>() {
            @Override
            public void onChanged(@Nullable String[] s) {


                doorRef.child(email).child(doorName.get(0)).child("status").setValue(false);
                doorRef.child(email).child(doorName.get(0)).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        // This method is called once with the initial value and again
                        // whenever data at this location is updated.
                        boolean value = dataSnapshot.child("status").getValue(boolean.class);
                        counter = dataSnapshot.child("counter").getValue(Integer.class);
                        textView.setText(counter+"");
                        Log.d(TAG, "Value is: " + value);
                        if (value) {
                            imageView.setImageResource(R.drawable.ic_baseline_lock_open_24);
                            new android.os.Handler().postDelayed(
                                    new Runnable() {
                                        public void run() {
                                            imageView.setImageResource(R.drawable.ic_baseline_lock_24);
                                            doorRef.child(email).child(doorName.get(0)).child("status").setValue(false);
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