package org.altbeacon.beaconreference;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import java.util.List;

/**
 * Created by SOMA on 18/05/15.
 */
public class DialogActivity extends Activity {
    AlertDialog dialog;
    boolean isShowing;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("test", "ishowing:" + isShowing);
        if (isRunning(this)) {
            //setContentView(R.layout.main);
            Context ctx = this;
//        if (dialog != null && dialog.isShowing()) return;
            final AlertDialog.Builder alert = new AlertDialog.Builder(ctx);
//        if (alert != null && alert.isShowing()) return;
            alert.setTitle("Notification");
            alert.setMessage("Example of notification message goes here");
            alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    Intent notifyIntent = new Intent(getApplicationContext(), MenuClass.class);
                    notifyIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(notifyIntent);
                    dialog.cancel();
                    dialog.dismiss();

                    finish();
//                        stopService(new Intent(getApplicationContext(), MyService.class));
                }
            });
            alert.create();
            alert.show();
        }
    }

    public boolean isRunning(Context ctx) {
        ActivityManager activityManager = (ActivityManager) ctx.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> tasks = activityManager.getRunningTasks(Integer.MAX_VALUE);

        for (ActivityManager.RunningTaskInfo task : tasks) {
            if (ctx.getPackageName().equalsIgnoreCase(task.baseActivity.getPackageName()))
                return true;
        }

        return false;
    }

    @Override
    public void onStart() {
        super.onStart();
        isShowing = true;
        Log.d("test","ishowing onResume:"+isShowing);
    }

    @Override
    public void onStop() {
        super.onStop();
        isShowing = false;
        Log.d("test","ishowing onResume:"+isShowing);
    }

    @Override
    public void onResume() {
        super.onResume();
        isShowing = true;

    }

    @Override
    public void onPause() {
        super.onPause();
        isShowing = false;
    }
}
