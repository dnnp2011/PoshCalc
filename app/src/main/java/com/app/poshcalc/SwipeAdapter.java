package com.app.poshcalc;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class SwipeAdapter extends FragmentStatePagerAdapter {
    public SwipeAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        switch (i) {
            case 0:
                return MainActivity.legendFragment;
            case 1:
                return MainActivity.mainFragment;
            case 2:
                return MainActivity.settingsFragment;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 3;
    }

    public interface FragmentLifecycle {
        public void onPauseFragment();
        public void onResumeFragment();
    }
}
