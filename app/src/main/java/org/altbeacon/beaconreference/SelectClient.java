package org.altbeacon.beaconreference;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

/**
 * Created by SOMA on 20/04/15.
 */
public class SelectClient extends Activity {
    ConnectionDetector cd;
    AlertDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //((BeaconReferenceApplication) this.getApplicationContext()).setMonitoringActivity(null);
        setContentView(R.layout.select_client_layout);
        if (checkConn()) {
            Spinner spinner = (Spinner) findViewById(R.id.spinner);
            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                    this, R.array.clients_array, android.R.layout.simple_spinner_item);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(adapter);
            spinner.setOnItemSelectedListener(new MyOnItemSelectedListener());

            ImageView myButton = (ImageView) findViewById(R.id.imageButton);
            myButton.setOnClickListener(new Button.OnClickListener() {
                public void onClick(View v) {

                    Spinner sp = (Spinner) findViewById(R.id.spinner);
                    String spinnerString = null;
                    spinnerString = sp.getSelectedItem().toString();
                    int nPos = sp.getSelectedItemPosition();


//                Toast.makeText(getApplicationContext(), "getSelectedItem=" + spinnerString,
//                        Toast.LENGTH_LONG).show();
//                Toast.makeText(getApplicationContext(), "getSelectedItemPosition=" + nPos,
//                        Toast.LENGTH_LONG).show();

                    startService(new Intent(getApplicationContext(), BeaconService.class));

//                Intent intent = new Intent(getApplicationContext(), MenuClass.class);
//                startActivity(intent);
                    moveTaskToBack(true);
                }
            });
        }
    }

    public boolean checkConn() {
        Log.d("test", "check internet connection");
        cd = new ConnectionDetector(getApplicationContext());
        if (!cd.isConnectingToInternet()) {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setTitle("Internet Connection Error");
            alertDialogBuilder.setMessage("Please connect to working Internet connection").setCancelable(false).setPositiveButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    SelectClient.this.finish();
                }
            });
            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
            return false;
        }
        return true;
    }
}


