package org.altbeacon.beaconreference;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;


import org.altbeacon.beacon.Beacon;
import org.altbeacon.beacon.BeaconConsumer;
import org.altbeacon.beacon.BeaconManager;
import org.altbeacon.beacon.RangeNotifier;
import org.altbeacon.beacon.Region;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Collection;
import java.util.HashMap;
import java.util.Random;

public class Page2 extends Fragment implements BeaconConsumer {
    private static final String TAG = "beaconDemo";
    public Context context;
    ConnectionDetector cd;
    BeaconConsumer bc;
    Communicator comm;
    private BeaconManager beaconManager = null;
    TextView beaconId,status_text,beacon_text;
    TextClicked mCallback;
    private BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // ignore the notification here
            // and block propagation
            Log.d("test","pg2 receive: context , intent:"+context+" "+intent);
            abortBroadcast();
        }
    };
    public static Page2 createInstance() {
        return new Page2();
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        beaconManager = BeaconManager.getInstanceForApplication(getActivity());
        beaconManager.bind(this);

//        if (!BuildConfig.DEBUG) {
//            verifyBluetooth();
//        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Log.d(TAG,"page2create");

//        context=Page2.this.getActivity().getApplicationContext();

        // beaconManager.bind(this);
        MenuClass activity = (MenuClass) getActivity();
        //String myDataFromActivity = activity.getMyData();

        View android = inflater.inflate(R.layout.fragment_layout, container, false);


//        String myTag = getTag();
//        Log.d(TAG,"fragmentB tag:"+myTag);
//        ((MenuClass)getActivity()).setTabFragmentB(myTag);

        return android;
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        beaconId=(TextView)view.findViewById(R.id.beacon_id);
        status_text=(TextView)view.findViewById(R.id.status_text);
        beacon_text=(TextView)view.findViewById(R.id.beacon_text);
        //tvDetails = (TextView) view.findViewById(R.id.ranging_details);
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d("test","page2 stop");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d("test","page2 pause");
        getActivity().unregisterReceiver(mReceiver);
        if (beaconManager.isBound(this)) beaconManager.setBackgroundMode(true);
    }
    @Override
    public void onResume() {
        super.onResume();
        IntentFilter ifi = new IntentFilter("be.hcpl.android.beaconexample.NOTIFY_FOR_BEACON");
        ifi.setPriority(10);
        getActivity().registerReceiver(mReceiver, ifi);
        if (beaconManager.isBound(this)) beaconManager.setBackgroundMode(false);
    }


    public interface TextClicked{
        public void sendText(double text);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        Log.d("test","page2 attach");
        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            mCallback = (TextClicked) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement TextClicked");
        }
    }

    @Override
    public void onDetach(){
        super.onDetach();
        Log.d("test","page2 detach");
        getActivity().unregisterReceiver(mReceiver);
        if (beaconManager.isBound(this)) beaconManager.setBackgroundMode(true);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("test","page2 destroy");
        // unbind if needed
        if (beaconManager.isBound(this)) beaconManager.unbind(this);
    }

    @Override
    public Context getApplicationContext() {
        return getActivity().getApplicationContext();
    }

    @Override
    public void unbindService(ServiceConnection serviceConnection) {
        getActivity().unbindService(serviceConnection);
    }

    @Override
    public boolean bindService(Intent intent, ServiceConnection serviceConnection, int i) {
        return getActivity().bindService(intent, serviceConnection, i);
    }


//    @Override
//    public void onBeaconServiceConnect() {
//
//        // configure a range notifier
//        beaconManager.setRangeNotifier(new RangeNotifier() {
//            @Override
//            public void didRangeBeaconsInRegion(final Collection<Beacon> beacons, Region region) {
//
//                // log beacons info once received
//                getActivity().runOnUiThread(new Runnable() {
//
//                    @Override
//                    public void run() {
//                        Log.d("test","found "+beacons.size()+" beacons");
////                        tvDetails.setText(String.format("found %s beacons", beacons.size()));
//                    }
//                });
//                if (beacons.size() > 0) {
//                    Log.i(BoundExampleFragment.class.getSimpleName(), "Bound fragment is handling discovered beacons now");
//                }
//            }
//        });
//
//        try {
//            // and start ranging for the given region. This region has a uuid specificed so will
//            // only react on beacons with this uuid, the 2 other fields are minor and major version
//            // to be more specific if desired
////            beaconManager.startRangingBeaconsInRegion(new Region("",Identifier.parse(""), null, null));
//            beaconManager.startRangingBeaconsInRegion(new Region("",null,null,null));
//        } catch (RemoteException e) {
//            Log.e(BoundExampleFragment.class.getSimpleName(), "Failed to start ranging", e);
//        }
//    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        comm=(Communicator) getActivity();
    }


    @Override
    public void onBeaconServiceConnect() {
        beaconManager.setRangeNotifier(new RangeNotifier() {
            @Override
            public void didRangeBeaconsInRegion(final Collection<Beacon> beacons, Region region) {
                if (beacons.size() > 0) {
//                    EditText editText = (EditText)Page2.this.findViewById(R.id.rangingText);

                    getActivity().runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            Beacon firstBeacon = beacons.iterator().next();
//                            Log.d("tag","page2 data:"+firstBeacon.describeContents());
//                            Log.d("tag","page2 data:"+firstBeacon.getBluetoothName());
//                            Log.d("tag","page2 data:"+firstBeacon.getBluetoothAddress());
//                            Log.d("tag","page2 data:"+firstBeacon.getBeaconTypeCode());
//                            Log.d("tag","page2 data:"+firstBeacon.getDataFields());
//                            Log.d("tag","page2 data:"+firstBeacon.getIdentifiers());
//                            Log.d("tag","page2 data:"+firstBeacon.getManufacturer());
//                            Log.d("tag","page2 data:"+firstBeacon.getTxPower());
                            mCallback.sendText(firstBeacon.getDistance());
                            comm.respond("muie");
//                            Log.d("test","found "+beacons.size()+" beacons");
//                        tvDetails.setText(String.format("found %s beacons", beacons.size()));
                            beacon_text.setText("The beacon");
                            beaconId.setText(firstBeacon.getBeaconTypeCode()+" ");
                            status_text.setText("is turned on and in range");
                        }
                    });

                   // logToDisplay("The first beacon "+firstBeacon.toString()+" is about "+firstBeacon.getDistance()+" meters away.");
                 }
            }

        });

        try {
            beaconManager.startRangingBeaconsInRegion(new Region("myRangingUniqueId", null, null, null));
        } catch (RemoteException e) {   }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if (requestCode == REQUEST_ENABLE_BT) {
//            if (resultCode == Activity.RESULT_OK) {
//                verifyBluetooth();
//            } else {
////                final Intent intent = new Intent(getApplicationContext(), MainActivity.class);
////                startActivity(intent);
//                //finish();
//            }
//        } else {
//
//        }
        super.onActivityResult(requestCode, resultCode, data);
    }
//    private void logToDisplay(final String line) {
//        runOnUiThread(new Runnable() {
//            public void run() {
//                EditText editText = (EditText)Page2.this
//                        .findViewById(R.id.rangingText);
//                editText.append(line+"\n");
//            }
//        });
//    }

//    public void b_updateText(String t){
//        Log.d(TAG,"update the fucking shit cu: "+t);
//    }
/*
    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d(TAG, "onreceive page 2:" + intent);
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
    @Override
    public void onROXIMITYEngineStarted() {
        Log.i(TAG, "ROXIMITY Engine has started");
    }

    @Override
    public void onROXIMITYEngineStopped() {
        Log.w(TAG, "ROXIMITY Engine has stopped");
    }

    private void createBroadcastRecievers() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ROXConsts.MESSAGE_FIRED);
        intentFilter.addAction(ROXConsts.BEACON_RANGE_UPDATE);
        intentFilter.addAction(ROXConsts.WEBHOOK_POSTED);

        LocalBroadcastManager.getInstance(context).registerReceiver(broadcastReceiver, intentFilter);
    }

    public void handleMessageFired(MessageParcel messageParcel) {

    }
*/
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
        Log.i(TAG, "Received a beacon range update page 2:" + rangeUpdate);
        if (checkConn()) {
            try {
                JSONArray beacon = new JSONArray(rangeUpdate);
                JSONObject beaconObj = beacon.getJSONObject(0);
                String beaconIdText = beaconObj.getString("id");
                String[] splited = beaconIdText.split("-");
                String[] id = splited[4].split("_");
                beaconIdText = id[1] + "/" + id[2];
                beacon_text.setText("The beacon");
                beaconId.setText(beaconIdText);
                status_text.setText("is turned on and in range");
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