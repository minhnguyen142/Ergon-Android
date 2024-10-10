package com.example.project;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.project.History_book.HistoryFragment;

public class ProfileFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        LinearLayout linearHistory = view.findViewById(R.id.linear_History);
        linearHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Gọi HistoryFragment
                HistoryFragment historyFragment = new HistoryFragment();
                FragmentManager fragmentManager = getParentFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.history, historyFragment); // R.id.fragment_container là nơi bạn chứa fragment
                fragmentTransaction.addToBackStack(null); // Thêm fragment vào back stack
                fragmentTransaction.commit();
            }
        });

        return view; // Trả về view
    }
}