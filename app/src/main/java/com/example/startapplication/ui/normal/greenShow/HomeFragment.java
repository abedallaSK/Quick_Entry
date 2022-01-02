package com.example.startapplication.ui.normal.greenShow;

import static android.content.ContentValues.TAG;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.startapplication.UserMainActivity;
import com.example.startapplication.classes.Account;
import com.example.startapplication.databinding.FragmentHomeBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private FragmentHomeBinding binding;
    private DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("Accounts");
    private static final String PREFS_NAME = "LOGIN";
    private static final String DATA_TAG = "KEY";
    private String key="Cash";

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                UserMainActivity activity = (UserMainActivity) getActivity();
                String key = activity.getKey();

                myRef.child(key).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        // This method is called once with the initial value and again
                        // whenever data at this location is updated.
                        Account value = dataSnapshot.getValue(Account.class);
                        if (value != null) {
                            if (value.getCheckGreen() == 1) {
                                final ImageView imageView = binding.photoView;
                                binding.btGreenCard.setVisibility(View.INVISIBLE);
                                Picasso.get().load(value.getGreenUri()).into(imageView);
                            }
                            if (value.getCheckGreen() == 2) {
                                binding.btGreenCard.setVisibility(View.VISIBLE);
                                binding.photoView.setVisibility(View.INVISIBLE);
                            }
                            if (value.getCheckGreen() == 0) {
                                final ImageView imageView = binding.photoView;
                                binding.btGreenCard.setVisibility(View.VISIBLE);
                                Picasso.get().load(value.getGreenUri()).into(imageView);
                            }

                            Log.d(TAG, "Value is: " + value);
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