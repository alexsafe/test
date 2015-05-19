/*
 * Copyright 2012 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.altbeacon.beaconreference;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

/**
 * A fragment representing a single step in a wizard. The fragment shows a dummy title indicating
 * the page number, along with some dummy text.
 */
public class ScreenSlidePageFragment extends Fragment {
    /**
     * The argument key for the page number this fragment represents.
     */
    public static final String ARG_PAGE = "page";
    ImageView image;
    /**
     * The fragment's page number, which is set to the argument value for {@link #ARG_PAGE}.
     */
    private int mPageNumber;

    public ScreenSlidePageFragment() {
    }

    /**
     * Factory method for this fragment class. Constructs a new fragment for the given page number.
     */
    public static ScreenSlidePageFragment create(int pageNumber) {
        ScreenSlidePageFragment fragment = new ScreenSlidePageFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, pageNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //mPageNumber = getArguments().getInt(ARG_PAGE);
        mPageNumber = getArguments() != null ? getArguments().getInt(ARG_PAGE) : 1;

        //Toast.makeText(ScreenSlidePageFragment.this.getActivity().getApplicationContext(), "on create page: " + mPageNumber, Toast.LENGTH_SHORT).show();
        // When changing pages, reset the action bar actions since they are dependent
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout containing a title and body text.
        ViewGroup rootView = (ViewGroup) inflater
                .inflate(R.layout.fragment_screen_slide_page, container, false);
        image = (ImageView) rootView.findViewById(R.id.imageviewPg1);
        Log.d("test", "pageslide mPagenumber:" + mPageNumber);
        // Set the title view to show the page number.
        // ((TextView) rootView.findViewById(android.R.id.text1)).setText("mu");
        switch (mPageNumber) {
            case 0:
                //((ImageView)rootView.findViewById(R.id.viewpager).setBackgroundResource(R.drawable.ic_launcher));
                //(rootView.findViewById(R.id.viewpager)).setBackgroundResource(R.drawable.ic_launcher);
                image.setBackgroundResource(R.drawable.images1);
                break;
            case 1:
                //((ImageView)rootView.findViewById(R.id.viewpager).setBackgroundResource(R.drawable.ic_launcher));
                image.setBackgroundResource(R.drawable.images2);
                break;
            case 2:
                //((ImageView)rootView.findViewById(R.id.viewpager).setBackgroundResource(R.drawable.ic_launcher));
                image.setBackgroundResource(R.drawable.images3);
                break;
            default:
                break;

        }
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    /**
     * Returns the page number represented by this fragment object.
     */
    public int getPageNumber() {
        return mPageNumber;
    }
}
