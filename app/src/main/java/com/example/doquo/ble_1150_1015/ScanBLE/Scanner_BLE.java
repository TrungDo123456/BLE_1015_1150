package com.example.doquo.ble_1150_1015.ScanBLE;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.Context;

import com.example.doquo.ble_1150_1015.MainActivity;
import com.example.doquo.ble_1150_1015.Utils.Utils;

import java.util.logging.Handler;

import uk.co.alt236.bluetoothlelib.device.BluetoothLeDevice;
import uk.co.alt236.bluetoothlelib.device.beacon.BeaconType;
import uk.co.alt236.bluetoothlelib.device.beacon.BeaconUtils;
import uk.co.alt236.bluetoothlelib.device.beacon.ibeacon.IBeaconDevice;

/**
 * Created by doquo on 03/23/2018.
 */

public class Scanner_BLE {

    MainActivity mainActivity;
    private long scanPeriod;
    private int signalStrength;
    private BluetoothAdapter bluetoothAdapter;
    private android.os.Handler mHandler;
    private boolean mScanning;

    public Scanner_BLE(MainActivity mainActivity, long scanPeriod, int signalStrength){
        this.mainActivity = mainActivity;
        this.scanPeriod = scanPeriod;
        this.signalStrength = signalStrength;
        this.mHandler = new android.os.Handler();
        final BluetoothManager bluetoothManager = (BluetoothManager) mainActivity.getSystemService(Context.BLUETOOTH_SERVICE);
        bluetoothAdapter = bluetoothManager.getAdapter();
    }

    public boolean isScanning() {
        return mScanning;
    }
    public void start(){
        if(!Utils.checkBlutooth(bluetoothAdapter)){
            Utils.requestUserBluetooth(mainActivity);
            mainActivity.stopScan();
        }else{
            scanLeDevice(true);
        }
    }
    public void stop(){
        scanLeDevice(false);
    }

    private void scanLeDevice(final boolean enable){
        if(enable && !mScanning){
            Utils.Toast(mainActivity.getApplicationContext(),"Bắt đầu Scan");
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Utils.Toast(mainActivity.getApplicationContext(), "Kết thúc Scan");

                    mScanning = false;
                    bluetoothAdapter.stopLeScan(leScanCallback);

                    mainActivity.stopScan();
                }
            },scanPeriod);
            mScanning = true;
            bluetoothAdapter.startLeScan(leScanCallback);
        }else {
            mScanning = false;
            bluetoothAdapter.stopLeScan(leScanCallback);
        }
    }
    private BluetoothAdapter.LeScanCallback leScanCallback =
            new BluetoothAdapter.LeScanCallback() {
        @Override
        public void onLeScan(final BluetoothDevice bluetoothDevice, final int rssi, byte[] scanRecord) {
            final BluetoothLeDevice ble_Device = new BluetoothLeDevice(bluetoothDevice,rssi,scanRecord,System.currentTimeMillis());
            if(rssi > signalStrength){
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        if(BeaconUtils.getBeaconType(ble_Device)== BeaconType.IBEACON){//chỉ lấy bluetooth thuộc beacon
                            mainActivity.addDevice(new IBeaconDevice(ble_Device),rssi);
                        }
                    }
                });
            }
        }
    };
}
