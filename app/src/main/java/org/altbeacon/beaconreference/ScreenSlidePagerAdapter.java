package org.altbeacon.beaconreference;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;

/**
 * Created by SOMA on 27/04/15.
 */
public class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
    private static final int NUM_PAGES = 3;
    public ScreenSlidePagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Log.d("test", "getItem:" + position);
        return ScreenSlidePageFragment.create(position);
    }

    @Override
    public int getCount() {
        return NUM_PAGES;
    }

//        @Override
//        public Object instantiateItem(ViewGroup view, int position) {
//            ScreenSlidePageFragment fragment = ScreenSlidePageFragment.create(position);
//            if (fragment != null) {
//                return fragment;
//            }
//            else {
//                return super.instantiateItem(view, position);
//            }
//        }
}