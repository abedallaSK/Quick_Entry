package com.example.startapplication.ui.home_foreman;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.startapplication.Foreman_Main_Activity;
import com.example.startapplication.ListformarActivity;
import com.example.startapplication.UserMainActivity;
import com.example.startapplication.classes.Account;
import com.example.startapplication.classes.ListAdapter2;
import com.example.startapplication.databinding.FragmentHomeForemanBinding;
import com.example.startapplication.databinding.FragmentNotificationsBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class HomeForemanFragment extends Fragment {

    private HomeForemanViewModel homeForemanViewModel;
    private FragmentHomeForemanBinding binding;
    private static final String PREFS_NAME = "LOGIN";
    private DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("Accounts");
    private ArrayList<Account> list_of_Account = new ArrayList<>();
    // private ArrayList<String> list_of_Key = new ArrayList<>();
    private ListAdapter2 listAdapter;
    private int mood=1;
    Spinner spinner;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeForemanViewModel =
                new ViewModelProvider(this).get(HomeForemanViewModel.class);

        binding = FragmentHomeForemanBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        Foreman_Main_Activity activity = ( Foreman_Main_Activity ) getActivity();

        homeForemanViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {


                myRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        Set<Account> set = new HashSet<>();
                        Iterator<DataSnapshot> i = dataSnapshot.getChildren().iterator();

                        while (i.hasNext()) {
                            String key=i.next().getKey();
                            Account account=dataSnapshot.child(key).getValue(Account.class);
                            if(account.getType()==mood)
                                set.add(account);
                        }

                        list_of_Account.clear();
                        list_of_Account.addAll(set);
                        // arrayAdapter.notifyDataSetChanged();
                        listAdapter = new ListAdapter2(activity, list_of_Account);
                        binding.listview.setAdapter(listAdapter);
                        binding.listview.setClickable(true);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

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