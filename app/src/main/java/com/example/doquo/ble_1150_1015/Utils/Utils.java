package com.example.doquo.ble_1150_1015.Utils;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.Intent;
import android.view.Gravity;
import android.widget.Toast;

import com.example.doquo.ble_1150_1015.MainActivity;


/**
 * Created by doquo on 03/23/2018.
 */

public class Utils {
    public static void Toast(Context context,String str){
        Toast toast = Toast.makeText(context, str, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER,0,0);
        toast.show();
    }
    //Kiem tra Bluetooth da mo chua
    public static boolean checkBlutooth(BluetoothAdapter bluetoothAdapter){
        if(bluetoothAdapter == null || !bluetoothAdapter.isEnabled()){
            return false;
        }else
            return true;
    }
    //Gui yeu cau mo Bluetooth
    public static void requestUserBluetooth(Activity activity) {
        Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
        activity.startActivityForResult(enableBtIntent, MainActivity.REQUEST_ENABLE_BT);
    }
}
