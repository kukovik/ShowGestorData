package com.gestordevices.showgestordata;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.widget.TextView;

import com.gestordevices.structs.PenInfoPkt;

public class MainActivity extends AppCompatActivity {

    private final static String TAG = MainActivity.class.getSimpleName();

    private final static String ACTION_DATA_AVAILABLE =
            "com.gestordevices.dspsdump.ACTION_DATA_AVAILABLE";
    private final static String DATA_BYTES =
            "com.gestordevices.dspsdump.DATA_BYTES";
    private final static String DATA_PKT =
            "com.gestordevices.dspsdump.DATA_PKT";
    private TextView mDataField;
    private final BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            final String action = intent.getAction();

            if (ACTION_DATA_AVAILABLE.equals(action)) {
//                displayData(intent.getByteArrayExtra(DATA_BYTES));
                displayData((PenInfoPkt) intent.getParcelableExtra(DATA_PKT));
            }
        }
    };

    private static IntentFilter makeIntentFilter() {
        final IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ACTION_DATA_AVAILABLE);
        return intentFilter;
    }

    private void displayData(byte[] data) {
        if (data != null && data.length > 0) {
            final StringBuilder stringBuilder = new StringBuilder(data.length);
            for (byte byteChar : data) {
                stringBuilder.append(String.format("%02X ", byteChar));
            }
            mDataField.setText(stringBuilder.toString());
        } else {
            mDataField.setText("No data");
        }
    }

    private void displayData(PenInfoPkt pkt) {
        if (pkt != null) {
            if (mDataField.getLineCount() >= 100) {
                mDataField.getEditableText().delete(
                        0,
                        mDataField.getLayout().getLineEnd(100 - mDataField.getLineCount())
                );
            }
            mDataField.append(pkt.toString() + "\n");
        } else {
            mDataField.setText("No data");
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDataField = (TextView) findViewById(com.gestordevices.showgestordata.R.id.data_value);
        mDataField.setMovementMethod(new ScrollingMovementMethod());
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "register BroadcastReceiver");
        registerReceiver(mBroadcastReceiver, makeIntentFilter());
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(TAG, "unregister BroadcastReceiver");
        unregisterReceiver(mBroadcastReceiver);
    }


}
