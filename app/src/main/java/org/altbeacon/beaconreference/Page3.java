package org.altbeacon.beaconreference;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.altbeacon.beacon.Beacon;
import org.altbeacon.beacon.BeaconConsumer;
import org.altbeacon.beacon.BeaconManager;
import org.altbeacon.beacon.RangeNotifier;
import org.altbeacon.beacon.Region;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Collection;

/**
 * Created by SOMA on 20/04/15.
 */
public class Page3 extends Fragment implements BeaconConsumer {
    private static final String TAG = "beaconDemo";
    private static final String STRING_VALUE = "stringValue";
    public Context context;
    View android;
    TextView rangeTxt;
    ImageView proximityImage;
    ConnectionDetector cd;
    private String activityAssignedValue = "";
    private BeaconManager beaconManager = null;
    private BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // ignore the notification here
            // and block propagation
            abortBroadcast();
        }
    };

    public static Page3 createInstance() {
        return new Page3();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        beaconManager = BeaconManager.getInstanceForApplication(getActivity());
        beaconManager.bind(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d("test", "page3 onCreateView");
        android = inflater.inflate(R.layout.fragment3_layout, container, false);
        Log.d("test", "onCreateView page3 :" + rangeTxt);
        //modifyText("muie");
        return android;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        //tvDetails = (TextView) view.findViewById(R.id.ranging_details);
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d("test", "page3 pause");
        if (beaconManager.isBound(this)) beaconManager.setBackgroundMode(true);
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d("test", "page3 resume");
        IntentFilter ifi = new IntentFilter("be.hcpl.android.beaconexample.NOTIFY_FOR_BEACON");
        ifi.setPriority(10);
        getActivity().registerReceiver(mReceiver, ifi);
        if (beaconManager.isBound(this)) beaconManager.setBackgroundMode(false);
        rangeTxt = (TextView) android.findViewById(R.id.rangeTxt);
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        rangeTxt = (TextView) getActivity().findViewById(R.id.rangeTxt);
        proximityImage = (ImageView) getActivity().findViewById(R.id.proximityImage);
        rangeTxt.setText("Waiting for range ...");
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("test", "page3 destroy");
        // unbind if needed
        if (beaconManager.isBound(this)) beaconManager.unbind(this);
    }

    public void updateText(double text) {
        // Here you have it
        Log.d("test", "update text page3 :" + text);
        Log.d("test", "android page3 :" + rangeTxt);
        if (android != null) {
            rangeTxt = (TextView) android.findViewById(R.id.rangeTxt);
        }
        Log.d("test", "update text page3 :" + rangeTxt);
        if (rangeTxt != null)
            rangeTxt.setText("Estimated distance to the beacon " + String.format("%.2f", text) + " m");
        //setDistanceUi(text);
    }


    @Override
    public Context getApplicationContext() {
        return getActivity().getApplicationContext();
    }

    @Override
    public void unbindService(ServiceConnection serviceConnection) {
        FragmentActivity activ = getActivity();
        if (activ != null) {
            activ.unbindService(serviceConnection);
        }
//        getActivity().unbindService(serviceConnection);
    }

    @Override
    public boolean bindService(Intent intent, ServiceConnection serviceConnection, int i) {
        return getActivity().bindService(intent, serviceConnection, i);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (beaconManager != null)
            if (beaconManager.isBound(this)) beaconManager.unbind(this);
        Log.d("test", "page3 attach");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d("test", "page3 onDetach");
    }

    public void changeText(String data) {
        Log.d("test", "page3 data:" + data);
        Log.d("test", "page3 changetext:" + rangeTxt);

//        rangeTxt.setText(data);
    }

    @Override
    public void onBeaconServiceConnect() {
        beaconManager.setRangeNotifier(new RangeNotifier() {
            @Override
            public void didRangeBeaconsInRegion(final Collection<Beacon> beacons, Region region) {
                if (beacons.size() > 0) {
                    getActivity().runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
//                    EditText editText = (EditText)Page2.this.findViewById(R.id.rangingText);
                            Beacon firstBeacon = beacons.iterator().next();
//                            Log.d("tag", "page3 data:" + firstBeacon.describeContents());
//                            Log.d("tag", "page3 data:" + firstBeacon.getBluetoothName());
//                            Log.d("tag", "page3 data:" + firstBeacon.getBluetoothAddress());
//                            Log.d("tag", "page3 data:" + firstBeacon.getBeaconTypeCode());
//                            Log.d("tag", "page3 data:" + firstBeacon.getDataFields());
//                            Log.d("tag", "page3 data:" + firstBeacon.getIdentifiers());
//                            Log.d("tag", "page3 data:" + firstBeacon.getManufacturer());
//                            Log.d("tag", "page3 data:" + firstBeacon.getTxPower());
                            setDistanceUi(firstBeacon.getDistance());
                        }
                    });
                    // logToDisplay("The first beacon "+firstBeacon.toString()+" is about "+firstBeacon.getDistance()+" meters away.");
                }
            }

        });

        try {
            beaconManager.startRangingBeaconsInRegion(new Region("myRangingUniqueId", null, null, null));
        } catch (RemoteException e) {
        }
    }
    /*
    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d(TAG, "onreceive:" + intent);
            if (intent.getAction().equals(ROXConsts.MESSAGE_FIRED)) {
                MessageParcel messageParcel = (MessageParcel) intent.getParcelableExtra(ROXConsts.EXTRA_MESSAGE_PARCEL);
                //handleMessageFired(messageParcel);
            } else if (intent.getAction().equals(ROXConsts.BEACON_RANGE_UPDATE)) {
                String rangeJson = intent.getStringExtra(ROXConsts.EXTRA_RANGE_DATA);
                handleBeaconRangeUpdate(rangeJson);
            } else if (intent.getAction().equals(ROXConsts.WEBHOOK_POSTED)) {
                String webhookJson = intent.getStringExtra(ROXConsts.EXTRA_BROADCAST_JSON);
                try {
                    JSONObject webHookObj = new JSONObject(webhookJson);
                    Log.d(TAG, "onreceive webhook:" + webhookJson);
                    //doNotification(webHookObj);
                    //Page3 pg3=new Page3();
                    //pg3.modifyText(webhookJson);
                    //Page3 pg3 = Page3.newInstance();
                    //pg3.modifyText("o alta muie");
                    //Page3.instantiate(getApplicationContext(),"page3").mod
                } catch (Exception e) {
                    Log.d(TAG, "exeception json :" + e);
                }
//                Intent intent11 = new Intent(MenuClass.this, DialogActivity.class);
//                intent11.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                startActivity(intent11);
            }
        }
    };
    */


//    @Override
//    public void onSaveInstanceState(Bundle outState) {
//        super.onSaveInstanceState(outState);
//        outState.putString(STRING_VALUE, activityAssignedValue);
//    }


    public void setDistanceUi(double dist) {
        Log.d("test", "page3 setDistanceUi:" + dist);
        rangeTxt.setText("Estimated distance to the beacon " + String.format("%.2f", dist) + " m");
        if (dist < 0.60) {

            proximityImage.setImageResource(R.drawable.immediate_proximity);
        } else if (dist < 3) {
//            rangeTxt.setText("Estimated distance to the beacon < 3m ");
            proximityImage.setImageResource(R.drawable.near_proximity);
        } else if (dist < 40) {
//            rangeTxt.setText("Estimated distance to the beacon >3m<40m ");
            proximityImage.setImageResource(R.drawable.far_proximity);
        }
//        switch (dist) {
//
//            case "IMMEDIATE":
//                rangeTxt.setText("Estimated distance to the beacon < 0.6m ");
//                proximityImage.setImageResource(R.drawable.immediate_proximity);
//                break;
//            case "NEAR":
//                rangeTxt.setText("Estimated distance to the beacon < 3m ");
//                proximityImage.setImageResource(R.drawable.near_proximity);
//                break;
//            case "FAR":
//                rangeTxt.setText("Estimated distance to the beacon >3m<40m ");
//                proximityImage.setImageResource(R.drawable.far_proximity);
//                break;
//        }


    }


//    private void createBroadcastRecievers() {
//        IntentFilter intentFilter = new IntentFilter();
//        intentFilter.addAction(ROXConsts.MESSAGE_FIRED);
//        intentFilter.addAction(ROXConsts.BEACON_RANGE_UPDATE);
//        intentFilter.addAction(ROXConsts.WEBHOOK_POSTED);
//
//        LocalBroadcastManager.getInstance(context).registerReceiver(broadcastReceiver, intentFilter);
//    }

    public void handleWebhookPosted(String webhookJson) {
        Log.d(TAG, "Webhook posted: " + webhookJson);
        try {
            JSONObject hook = new JSONObject(webhookJson);
            JSONObject broadHook = hook.getJSONObject("com.roximity.broadcast");
            //doNotification(broadHook);

        } catch (Throwable t) {
            Log.e(TAG, "Could not parse malformed JSON: \"" + webhookJson + "\"");
        }
    }

    public void handleBeaconRangeUpdate(String rangeUpdate) {
        if (checkConn()) {
            Log.i(TAG, "Received a beacon range update page 3:" + rangeUpdate);
            try {
                JSONArray beacon = new JSONArray(rangeUpdate);
                JSONObject beaconObj = beacon.getJSONObject(0);
                String beaconProximity = beaconObj.getString("proximity");
//                setDistanceUi(beaconProximity);
//          beaconId.setText(beaconIdText);
//            status_text.setText("is in turned on and in range");
            } catch (Exception e) {
                Log.d(TAG, "Exception handleBeaconRangeUpdate page 2:" + e);
            }
        }
    }

    public boolean checkConn() {
        Log.d("test", "check internet connection");
        cd = new ConnectionDetector(context);
        if (!cd.isConnectingToInternet()) {
//            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Page2.this.getActivity());
//            alertDialogBuilder.setTitle("Internet Connection Error");
//            alertDialogBuilder.setMessage("Please connect to working Internet connection").setCancelable(false).setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                public void onClick(DialogInterface dialog, int id) {
//                    //Page2.this.getActivity().finish();
//                }
//            });
//            AlertDialog alertDialog = alertDialogBuilder.create();
//            alertDialog.show();
            return false;
        }
        return true;
    }

}