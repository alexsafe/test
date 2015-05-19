package org.altbeacon.beaconreference;

import android.app.AlertDialog;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.app.TaskStackBuilder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.WindowManager;

import org.altbeacon.beacon.Beacon;
import org.altbeacon.beacon.BeaconConsumer;
import org.altbeacon.beacon.BeaconManager;
import org.altbeacon.beacon.RangeNotifier;
import org.altbeacon.beacon.Region;
import org.altbeacon.beacon.powersave.BackgroundPowerSaver;
import org.altbeacon.beacon.startup.BootstrapNotifier;
import org.altbeacon.beacon.startup.RegionBootstrap;

import java.util.Collection;

/**
 * Created by SOMA on 14/05/15.
 */
public class BeaconService extends Service implements BootstrapNotifier, BeaconConsumer {

    private static final String TAG = "AndroidProximityReferenceApplication";
    AlertDialog dialog;
    private RegionBootstrap regionBootstrap;
    private BackgroundPowerSaver backgroundPowerSaver;
    private boolean haveDetectedBeaconsSinceBoot = false;
    private MonitoringActivity monitoringActivity = null;
    private BeaconManager beaconManager;

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d("test", "onreceive service:" + intent);
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("tag", "beaconService created");
        beaconManager = BeaconManager.getInstanceForApplication(this);
        beaconManager.bind(this);
        if (beaconManager.isBound(this)) {
            Log.d("test", "beaconService manager is bound");
            beaconManager.setBackgroundMode(false);
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // Let it continue running until it is stopped.
        //Toast.makeText(this, "Service Started", Toast.LENGTH_LONG).show();
        createBroadcastRecievers();
        return START_STICKY;
    }

    @Override
    public void onBeaconServiceConnect() {
        beaconManager.setRangeNotifier(new RangeNotifier() {
            @Override
            public void didRangeBeaconsInRegion(Collection<Beacon> beacons, Region region) {
                if (beacons.size() > 0) {
                    Beacon firstBeacon = beacons.iterator().next();
                    Log.d("tag", "beaconService data:" + firstBeacon.describeContents());
                    Log.d("tag", "beaconService data:" + firstBeacon.getBluetoothName());
                    Log.d("tag", "beaconService data:" + firstBeacon.getBluetoothAddress());
                    Log.d("tag", "beaconService data:" + firstBeacon.getBeaconTypeCode());
                    Log.d("tag", "beaconService data:" + firstBeacon.getDataFields());
                    Log.d("tag", "beaconService data:" + firstBeacon.getIdentifiers());
                    Log.d("tag", "beaconService data:" + firstBeacon.getManufacturer());
                    Log.d("tag", "beaconService data:" + firstBeacon.getTxPower());
                    Log.d("tag", "The first beaconService " + firstBeacon.toString() + " is about " + firstBeacon.getDistance() + " meters away.");
                    beaconManager.setBackgroundMode(false);
                    alertNotify("web");
                    // Log.d("tag",beaconManager.get)
                }
            }

        });

        try {
            beaconManager.startRangingBeaconsInRegion(new Region("myRangingUniqueId", null, null, null));
        } catch (RemoteException e) {
            Log.d("test", "RemoteException e:" + e);
        }
    }

    private void createBroadcastRecievers() {
        IntentFilter intentFilter = new IntentFilter();
        LocalBroadcastManager.getInstance(this).registerReceiver(broadcastReceiver, intentFilter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(broadcastReceiver);
    }

    public void alertNotify(String webHookJson) {
        Log.d("test", "in alert notify:" + dialog);
        Intent trIntent = new Intent("android.intent.action.MAIN");
        trIntent.setClass(getApplicationContext(), DialogActivity.class);
        trIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(trIntent);
/*
        //if (dialog != null && dialog.isShowing()) return;
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setMessage("Example of notification message goes here")
                .setTitle("Notification")
                .setCancelable(false);
        Log.d("test", "in alert notify builder:" + builder);
        builder.setPositiveButton("Ok",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg, int which) {

                        Intent notifyIntent = new Intent(getApplicationContext(), MenuClass.class);
                        notifyIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(notifyIntent);
                        dialog.cancel();
                        dialog.dismiss();
                        dialog = null;
                        stopSelf();
//                        stopService(new Intent(getApplicationContext(), MyService.class));
                    }
                });
        dialog = builder.create();
        dialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
        dialog.show();
*/
    }

    @Override
    public void didEnterRegion(Region arg0) {
        // In this example, this class sends a notification to the user whenever a Beacon
        // matching a Region (defined above) are first seen.
        Log.d("test", "did enter region.");
        if (!haveDetectedBeaconsSinceBoot) {
            Log.d("test", "auto launching MainActivity");

            // The very first time since boot that we detect an beacon, we launch the
            // MainActivity
            Intent intent = new Intent(this, MonitoringActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            // Important:  make sure to add android:launchMode="singleInstance" in the manifest
            // to keep multiple copies of this activity from getting created if the user has
            // already manually launched the app.
            // this.startActivity(intent);
            haveDetectedBeaconsSinceBoot = true;
        } else {
            if (monitoringActivity != null) {
                // If the Monitoring Activity is visible, we log info about the beacons we have
                // seen on its display
                monitoringActivity.logToDisplay("I see a beacon again");
            } else {
                // If we have already seen beacons before, but the monitoring activity is not in
                // the foreground, we send a notification to the user on subsequent detections.
                Log.d("test", "Sending notification.");
                //sendNotification();
            }
        }


    }

    @Override
    public void didExitRegion(Region region) {
        if (monitoringActivity != null) {
            monitoringActivity.logToDisplay("I no longer see a beacon.");
        }
    }

    @Override
    public void didDetermineStateForRegion(int state, Region region) {
        if (monitoringActivity != null) {
            monitoringActivity.logToDisplay("I have just switched from seeing/not seeing beacons: " + state);
        }
    }

    private void sendNotification() {
        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(this)
                        .setContentTitle("Beacon Reference Application")
                        .setContentText("An goddamn beacon is nearby.")
                        .setSmallIcon(R.drawable.ic_launcher);

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addNextIntent(new Intent(this, MonitoringActivity.class));
        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(
                        0,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );
        builder.setContentIntent(resultPendingIntent);
        NotificationManager notificationManager =
                (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(1, builder.build());
    }

    public void setMonitoringActivity(MonitoringActivity activity) {
        this.monitoringActivity = activity;
    }
}
