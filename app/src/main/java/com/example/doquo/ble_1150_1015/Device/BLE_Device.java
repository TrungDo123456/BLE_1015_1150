package com.example.doquo.ble_1150_1015.Device;

import android.bluetooth.BluetoothDevice;

/**
 * Created by doquo on 03/23/2018.
 * Lấy dữ liệu từ thiết bị kết nối
 */

public class BLE_Device {
    private BluetoothDevice ble_Device;
    private int rssi; // received signal strength indicator
    //chỉ số cường độ tín hiệu thu, là chỉ số để đo độ mạnh của tín hiệu tại thiết bị thu (ví dụ anten), được định nghĩa trong chuẩn IEEE 802.11.
    public BLE_Device (BluetoothDevice ble_Device){
        this.ble_Device = ble_Device;
    }
    public String getName(){
        return ble_Device.getName();
    }
    public String getAddress(){
        return ble_Device.getAddress();
    }
    public void setRSSI(int rssi){
        this.rssi = rssi;
    }
    public int getRSSI(){
        return this.rssi;
    }
}
