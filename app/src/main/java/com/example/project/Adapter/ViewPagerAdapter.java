package com.example.project.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.project.fragment.HomePage;
import com.example.project.fragment.Library;
import com.example.project.fragment.Profile;
import com.example.project.fragment.History;

public class ViewPagerAdapter extends FragmentStateAdapter {

    public ViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new HomePage();
            case 1:
                return new Library();
            case 2:
                return new Profile();
            case 3:
                return new History();
            default:
                return new HomePage();
        }
    }

    @Override
    public int getItemCount() {
        return 4; // Adjusted to handle 4 fragments
    }
}
