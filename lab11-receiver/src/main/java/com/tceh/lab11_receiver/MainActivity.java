package com.tceh.lab11_receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView mLevel;
    BatteryStatusReceiver mReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mLevel = (TextView) findViewById(R.id.level);
    }

    public class BatteryStatusReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
            int scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
            float batteryPct = Math.round(level / (float)scale * 100);
            mLevel.setText("Уровень заряда " + batteryPct + "%");
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mReceiver == null) {
            mReceiver = new BatteryStatusReceiver();
        }
        IntentFilter filter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        registerReceiver(mReceiver, filter);
    }
    @Override
    protected void onStop() {
        super.onStop();
        if (mReceiver != null) {
            unregisterReceiver(mReceiver);
            mReceiver = null;
        }
    }
}
