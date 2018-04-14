package com.example.doquo.ble_1150_1015.Device;
import uk.co.alt236.bluetoothlelib.device.beacon.ibeacon.IBeaconDevice;

/**
 * Created by doquo on 03/23/2018.
 * Lấy dữ liệu từ thiết bị kết nối
 */

public class BLE_Device {
    public IBeaconDevice ble_Device;
    private int rssi; // received signal strength indicator
    private double distance;
    //chỉ số cường độ tín hiệu thu, là chỉ số để đo độ mạnh của tín hiệu tại thiết bị thu (ví dụ anten), được định nghĩa trong chuẩn IEEE 802.11.
    public BLE_Device (IBeaconDevice ble_Device){
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
    public String getUUID(){
        return ble_Device.getUUID();
    }
    public int getMajor(){
        return ble_Device.getMajor();
    }
    public int getMinor(){
        return ble_Device.getMinor();
    }
    public double getDistance(){
        return distance;
    }
    public void setDistance(double distance){
        this.distance = distance;
    }
}
