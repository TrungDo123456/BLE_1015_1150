package com.example.doquo.ble_1150_1015.Adapter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.doquo.ble_1150_1015.Device.BLE_Device;
import com.example.doquo.ble_1150_1015.R;
import com.example.doquo.ble_1150_1015.Utils.Constants;

import java.util.ArrayList;

/**
 * Created by doquo on 03/23/2018.
 */

public class ListAdapter_BLE_Device extends ArrayAdapter<BLE_Device> {

   private Activity act;
   private int layoutResourceID;
   private ArrayList<BLE_Device> arr_ble_device;

    public ListAdapter_BLE_Device(Activity activity, int resource,ArrayList<BLE_Device> objects) {
        super(activity.getApplicationContext(), resource, objects);
        this.act = activity;
        this.layoutResourceID = resource;
        arr_ble_device = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) act.getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(layoutResourceID,parent,false);
        }
        BLE_Device device = arr_ble_device.get(position);
        Log.d("trungdo",""+device.getDistance());
        String name = device.getName();
        String address = device.getAddress();
        final String accuracy = Constants.DOUBLE_TWO_DIGIT_ACCURACY.format(device.getDistance());
        TextView tv = null;
        tv = (TextView) convertView.findViewById(R.id.txt_name);
        if (name != null && name.length() > 0) {
            tv.setText("Tên: "+device.getName());
        }
        else {
            tv.setText("Tên: "+"Chưa rõ tên(TD)");
        }

        tv = (TextView) convertView.findViewById(R.id.txt_rssi);
        tv.setText("RSSI: " + device.getRSSI());

        tv = (TextView) convertView.findViewById(R.id.txt_uuid);
        tv.setText("UUID: " + device.getUUID());

        tv = (TextView) convertView.findViewById(R.id.txt_major);
        tv.setText("Major: " + device.getMajor());

        tv = (TextView) convertView.findViewById(R.id.txt_minor);
        tv.setText("Minor: " + device.getMinor());

        tv = (TextView) convertView.findViewById(R.id.txt_distance);
        tv.setText("Distance: " + getContext().getString(R.string.formatter_meters,accuracy));

        tv = (TextView) convertView.findViewById(R.id.txt_macaddr);
        if (address != null && address.length() > 0) {
            tv.setText("MAC: "+device.getAddress());
        }
        else {
            tv.setText("MAC: "+"Không rõ địa chỉ");
        }


        return convertView;
    }
}