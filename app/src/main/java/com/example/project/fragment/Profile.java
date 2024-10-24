package com.example.project.fragment;

import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.project.ui.History;
import com.example.project.R;

public class Profile extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        LinearLayout linearHistory = view.findViewById(R.id.linear_History);
        linearHistory.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), History.class);
            startActivity(intent);
        });

        return view;
    }
}
