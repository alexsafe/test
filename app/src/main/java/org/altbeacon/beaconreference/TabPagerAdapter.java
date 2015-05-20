package org.altbeacon.beaconreference;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;

import com.astuetz.PagerSlidingTabStrip;

public class TabPagerAdapter extends FragmentStatePagerAdapter
         implements PagerSlidingTabStrip.IconTabProvider {
    // private String[] titles = {"Item 1", "Item 2"};
    private int titles[] = {R.drawable.button_1, R.drawable.button_2, R.drawable.button_3};
    private FragmentManager fragmentM;

    public TabPagerAdapter(FragmentManager fm) {
        super(fm);
        fragmentM=fm;
        Log.d("test", "frags:" + fragmentM.getFragments());
    }

    @Override
    public Fragment getItem(int i) {
        Log.d("test","change fragment:"+i);
        switch (i) {
            case 0:

                Log.d("tag", "getitem0");
                Page1 pg1 = new Page1();
               // fragmentM.beginTransaction().add(R.id.viewpager, pg1, "pg1").commit();
                return pg1;
            case 1:
                Log.d("tag", "getitem1");
                Page2 pg2 = new Page2();
//                Fragment pag3=Page3.createInstance();
//                fragmentM.beginTransaction().remove(pag3).commit();
                //fragmentM.beginTransaction().add(pg2, "pg22").commit();
                return pg2;

            case 2:
                Log.d("tag", "getitem2");
                Page3 pg3 = new Page3();
//                Fragment pag2=Page2.createInstance();

//                fragmentM.beginTransaction().remove(pag2).commit();
                //fragmentM.beginTransaction().add(pg2, "pg22").commit();
                return pg3;
        }
        return null;
    }

    @Override
    public int getCount() {
        return titles.length;
    }

//    @Override
//    public CharSequence getPageTitle(int position) {
//        return titles[position];
//    }

    @Override
    public int getPageIconResId(int position) {
        return titles[position];
    }
}