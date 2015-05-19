package org.altbeacon.beaconreference;

/**
 * Created by SOMA on 17/04/15.
 */

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

public class Page1 extends Fragment {

    private ViewPager mPager;
    /**
     * The pager adapter, which provides the pages to the view pager widget.
     */
    private PagerAdapter mPagerAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View android = inflater.inflate(R.layout.photos_layout, container, false);
        // ((TextView) android.findViewById(R.id.textView)).setText("Page1");
        mPager = (ViewPager) android.findViewById(R.id.pagerPhotos);
        mPagerAdapter = new ScreenSlidePagerAdapter(getChildFragmentManager());
        mPager.setAdapter(mPagerAdapter);
        mPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                //Toast.makeText(Page1.this.getActivity().getApplicationContext(),"Selected page1 position: " + position, Toast.LENGTH_SHORT).show();
                // When changing pages, reset the action bar actions since they are dependent
                // on which page is currently active. An alternative approach is to have each
                // fragment expose actions itself (rather than the activity exposing actions),
                // but for simplicity, the activity provides the actions in this sample.
            }
        });

//        String TabOfFragmentB = ((MenuClass)getActivity()).getTabFragmentB();
////        String TabOfFragmentB = "pg2";
//        Log.d("tag","fragmentB:"+TabOfFragmentB);
//        Page2 fragmentB = (Page2)getActivity()
//                .getSupportFragmentManager()
//                .findFragmentByTag(TabOfFragmentB);
//        Log.d("tag","fragmentB:"+fragmentB);
//        fragmentB.b_updateText("muie");

        return android;
    }


}