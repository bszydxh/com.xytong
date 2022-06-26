package com.xytong.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class RootFragmentPagerAdapter extends FragmentStateAdapter {
    FragmentManager fragmentManager;

    public RootFragmentPagerAdapter(@NonNull FragmentManager fm,
                                    @NonNull Lifecycle lifecycle) {
        super(fm, lifecycle);
        fragmentManager = fm;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return null;
    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
