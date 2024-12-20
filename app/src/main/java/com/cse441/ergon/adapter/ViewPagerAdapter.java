
package com.cse441.ergon.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.cse441.ergon.fragment.HomePageFragment;
import com.cse441.ergon.fragment.LibraryFragment;
import com.cse441.ergon.fragment.ProfileFragment;

public class ViewPagerAdapter extends FragmentStateAdapter {




    public ViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }


    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new HomePageFragment();
            case 1:
                return new LibraryFragment();
            case 2:
                return new ProfileFragment();
            default:
                return new HomePageFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
