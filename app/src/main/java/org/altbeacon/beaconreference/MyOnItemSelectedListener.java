package org.altbeacon.beaconreference;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import android.widget.AdapterView;
import android.widget.Toast;

/**
 * Created by SOMA on 20/04/15.
 */
public  class MyOnItemSelectedListener implements AdapterView.OnItemSelectedListener {

    public void onItemSelected(AdapterView<?> parent,
                               View view, int pos, long id) {
        //Toast.makeText(parent.getContext(), "Item is " +     parent.getItemAtPosition(pos).toString(), Toast.LENGTH_LONG).show();


    }

    public void onNothingSelected(AdapterView parent) {
        // Do nothing.
    }
}
