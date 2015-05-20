package org.altbeacon.beaconreference;


import android.app.ActionBar;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.pdf.PdfDocument;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.widget.Toast;

import com.astuetz.PagerSlidingTabStrip;


public class MenuClass extends FragmentActivity implements Page2.TextClicked,Communicator{//implements ROXIMITYEngineListener {
    private static final String TAG = "beaconDemo";
    String rangeupdate = "not";
    /*private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d(TAG, "onreceive:" + intent);
            if (intent.getAction().equals(ROXConsts.MESSAGE_FIRED)) {
                MessageParcel messageParcel = (MessageParcel) intent.getParcelableExtra(ROXConsts.EXTRA_MESSAGE_PARCEL);
                handleMessageFired(messageParcel);
            } else if (intent.getAction().equals(ROXConsts.BEACON_RANGE_UPDATE)) {
                String rangeJson = intent.getStringExtra(ROXConsts.EXTRA_RANGE_DATA);
                handleBeaconRangeUpdate(rangeJson);
            } else if (intent.getAction().equals(ROXConsts.WEBHOOK_POSTED)) {
                String webhookJson = intent.getStringExtra(ROXConsts.EXTRA_BROADCAST_JSON);
                try {
                    JSONObject webHookObj = new JSONObject(webhookJson);
                    Log.d(TAG, "onreceive webhook:" + webhookJson);
                    doNotification(webHookObj);
                    //Page3 pg3=new Page3();
                    //pg3.modifyText(webhookJson);
                    //Page3 pg3 = Page3.newInstance();
                    //pg3.modifyText("o alta muie");
//                    .passDataToFragment("Hi from FragmentActivity");
                    //Page3.instantiate(getApplicationContext(),"page3").mod
                } catch (Exception e) {
                    Log.d(TAG, "exeception json :" + e);
                }
//                Intent intent11 = new Intent(MenuClass.this, DialogActivity.class);
//                intent11.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                startActivity(intent11);
            }
        }
    };*/
    ViewPager Tab;
    TabPagerAdapter TabAdapter;
    ActionBar actionBar;
    SharedPreferences sharedpreferences;
    String TabFragmentB;

    @Override
    public void sendText(double text){
        // Get Fragment B
        Page3 frag = Page3.createInstance();
        frag.updateText(text);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab_test);

        // Get the ViewPager and set it's PagerAdapter so that it can display items
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);

        viewPager.setAdapter(new TabPagerAdapter(getSupportFragmentManager()));

        // Give the PagerSlidingTabStrip the ViewPager
        PagerSlidingTabStrip tabsStrip = (PagerSlidingTabStrip) findViewById(R.id.tabs);
        // Attach the view pager to the tab strip

        tabsStrip.setViewPager(viewPager);


        tabsStrip.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            // This method will be invoked when a new page becomes selected.
            @Override
            public void onPageSelected(int position) {
//                Toast.makeText(MenuClass.this,        "Selected page position: " + position, Toast.LENGTH_SHORT).show();
            }

            // This method will be invoked when the current page is scrolled
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                // Code goes here
            }

            // Called when the scroll state changes:
            // SCROLL_STATE_IDLE, SCROLL_STATE_DRAGGING, SCROLL_STATE_SETTLING
            @Override
            public void onPageScrollStateChanged(int state) {
                // Code goes here
            }
        });

/*
        HashMap<String, Object> engineOptions = new HashMap<String, Object>();
        engineOptions.put(ROXConsts.ENGINE_OPTIONS_START_LOCATION_DEACTIVATED, false);
        sharedpreferences = this.getApplicationContext().getSharedPreferences("beacons", 0);
        SharedPreferences.Editor editor = getApplicationContext().getSharedPreferences("beacons", Context.MODE_PRIVATE).edit();
        editor.clear();
        editor.commit();
        try {
            ROXIMITYEngine.startEngineWithOptions(this.getApplicationContext(), R.drawable.ic_launcher, engineOptions, this, null);
            createBroadcastRecievers();

        } catch (Exception e) {
            Log.e(TAG, "Unable to start ROXIMITY Engine");
            e.printStackTrace();
        }
        */
    }

    public String getTabFragmentB() {
        return TabFragmentB;
    }

    public void setTabFragmentB(String t) {
        TabFragmentB = t;
    }

    @Override
    public void onBackPressed() {

        Intent setIntent = new Intent(getApplicationContext(), SelectClient.class);
        setIntent.addCategory(Intent.CATEGORY_HOME);
        setIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(setIntent);
        finish();
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onresume");
        //Log.i(TAG, "ROXIMITY Engine running: " + ROXIMITYEngine.isROXIMITYEngineRunning());
//        String ns = Context.NOTIFICATION_SERVICE;
//        NotificationManager nMgr = (NotificationManager) getApplicationContext().getSystemService(ns);
//        nMgr.cancel(NOTIF_ID);

    }

    @Override
    public void respond(String data) {
        Page3 pg3=new Page3();
        pg3.changeText(data);
    }
}
