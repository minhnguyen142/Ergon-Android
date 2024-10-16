package com.example.project.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.project.Fragment.HomepageFragment;
import com.example.project.Fragment.LibraryFragment;
import com.example.project.Fragment.ProfileFragment;

public class ViewPagerAdapter extends FragmentStateAdapter {




    public ViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }


    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new HomepageFragment();
            case 1:
                return new LibraryFragment();
            case 2:
                return new ProfileFragment();
            default:
                return new HomepageFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
