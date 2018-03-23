package com.example.doquo.ble_1150_1015.BroadCast;

import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.doquo.ble_1150_1015.Utils.Utils;

/**
 * Created by doquo on 03/23/2018.
 * Cập nhật trạng thái bluetooth
 */

public class BroadcastReceiver_BTState extends BroadcastReceiver{
    Context actContext;
    public BroadcastReceiver_BTState(Context context){
        this.actContext = context;
    }
    @Override
    public void onReceive(Context context, Intent intent) {
        final String action = intent.getAction();
        if(action.equals(BluetoothAdapter.ACTION_STATE_CHANGED)){
            final int state = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE,BluetoothAdapter.ERROR);
            switch (state){
                case BluetoothAdapter.STATE_OFF://=10
                    Utils.Toast(actContext,"Bluetooth đã tắt");
                    break;
                case BluetoothAdapter.STATE_TURNING_OFF://=13
                    Utils.Toast(actContext,"Bluetooth đang tắt");
                    break;
                case BluetoothAdapter.STATE_ON://=12
                    Utils.Toast(actContext,"Bluetooth đã bật");
                    break;
                case BluetoothAdapter.STATE_TURNING_ON://=11
                    Utils.Toast(actContext,"Bluetooth đang bật");
                    break;
            }
        }
    }
}
