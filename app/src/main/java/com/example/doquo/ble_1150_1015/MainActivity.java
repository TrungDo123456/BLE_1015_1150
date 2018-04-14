package com.example.doquo.ble_1150_1015;

import android.app.NotificationManager;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.ScrollView;

import com.example.doquo.ble_1150_1015.Adapter.ListAdapter_BLE_Device;
import com.example.doquo.ble_1150_1015.BroadCast.BroadcastReceiver_BTState;
import com.example.doquo.ble_1150_1015.Device.BLE_Device;
import com.example.doquo.ble_1150_1015.ScanBLE.Scanner_BLE;
import com.example.doquo.ble_1150_1015.Utils.Notify;
import com.example.doquo.ble_1150_1015.Utils.Utils;
import java.util.ArrayList;
import java.util.HashMap;

import uk.co.alt236.bluetoothlelib.device.beacon.ibeacon.IBeaconDevice;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {

    private Context context;
    private NotificationManager notificationManager;
    private int notification_id;
    public static int REQUEST_ENABLE_BT = 1;
    private static final int SCANPERIOD = 1000;
    private static final int SINGLESTRENGTH = -75;
    private BroadcastReceiver_BTState mBTStateUpdateReceiver;
    private ListAdapter_BLE_Device listAdapter_ble_device;
    private ListView listView_ble_device;
    private HashMap<String,BLE_Device> mBLEDeviceHashMap;
    private ArrayList<BLE_Device> mBLEDeviceArrList;
    private Scanner_BLE scanner_ble;
    private Handler mHanler;

    private Button btnSan,btnStop;
    private ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Kiểm tra BLE có được hổ trợ trên thiết bị không
        if(!getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE)){
            Utils.Toast(getApplicationContext(),"Thiết bị không hổ trợ BLE");
            finish();
        }
        //Khởi tạo
        init();
        //
        action();
    }
    private void init(){
        context = this;
        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notification_id = (int) System.currentTimeMillis();
        mBTStateUpdateReceiver = new BroadcastReceiver_BTState(getApplicationContext());
        scanner_ble = new Scanner_BLE(this,SCANPERIOD,SINGLESTRENGTH);
        mBLEDeviceHashMap = new HashMap<>();
        mBLEDeviceArrList = new ArrayList<>();
        mHanler = new Handler();
        listAdapter_ble_device = new ListAdapter_BLE_Device(this,R.layout.ble_device_list_item,mBLEDeviceArrList);
        listView_ble_device = new ListView(this);
        listView_ble_device.setAdapter(listAdapter_ble_device);
        btnSan = findViewById(R.id.btn_scan);
        btnStop = findViewById(R.id.btn_stopscan);
        progressBar = findViewById(R.id.progessBar);

    }
    public void invisible_btn(){
        btnSan.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
        btnStop.setVisibility(View.VISIBLE);
    }
    public void visible_btn(){
        btnSan.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);
        btnStop.setVisibility(View.GONE);
    }
    private void action(){
        ((ScrollView)findViewById(R.id.scrollView)).addView(listView_ble_device);
        listView_ble_device.setOnItemClickListener(this);
        btnSan.setOnClickListener(this);
        btnStop.setOnClickListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        registerReceiver(mBTStateUpdateReceiver,new IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED));
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(mBTStateUpdateReceiver);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //Log.d("trungdo",""+requestCode);
        if (requestCode == REQUEST_ENABLE_BT) {
            if (resultCode == RESULT_OK) {
                //
            }
            else if (resultCode == RESULT_CANCELED) {
                Utils.Toast(getApplicationContext(), "Ứng dụng cần sử dụng Bluetooth");
                finish();
                return; // thoát app
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_scan:
                Refresh();
                if(!scanner_ble.isScanning()){
                    startScan();
                }else{
                    stopScan();
                }
                break;
            case R.id.btn_stopscan:
                scanner_ble.stopTimer();
                break;
                default:
                    break;
        }
    }
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        stopScan();
        visible_btn();
        final String address = mBLEDeviceArrList.get(i).getAddress();
        Utils.Toast(getApplicationContext(),address);
    }
    public void Refresh(){
        if(mBLEDeviceArrList.size() > 0){
            mBLEDeviceArrList.clear();
            mBLEDeviceHashMap.clear();
            listAdapter_ble_device.notifyDataSetChanged();
        }
    }

    public void addDevice(IBeaconDevice ble_Device, final int rssi){
        final String address = ble_Device.getAddress();
        final  double distance = ble_Device.getAccuracy();
        if(!mBLEDeviceHashMap.containsKey(address)){
            Notify(context,
                    "Mac: "+
                            address,
                    "How about having your free coffee?",
                    "0934197445",
                    notification_id);
            BLE_Device ble_device = new BLE_Device(ble_Device);
            ble_device.setRSSI(rssi);
            ble_device.setDistance(distance);
            mBLEDeviceHashMap.put(address,ble_device);
            mBLEDeviceArrList.add(ble_device);
        }else {
            mBLEDeviceHashMap.get(address).setRSSI(rssi);
            mBLEDeviceHashMap.get(address).setDistance(distance);
        }
        listAdapter_ble_device.notifyDataSetChanged();
    }

    public void stopScan(){
        btnSan.setText("SCAN AGAIN");
        scanner_ble.stop();
    }
    public void startScan(){
        btnSan.setText("Scaning...");
        scanner_ble.start();
    }
    public void Notify(Context context,String title,String content,String numberPhone,int notification_id){
        notificationManager.notify(
                notification_id,
                Notify.showNotify(context,title,content,numberPhone).build());
    }
}
//    Notify(context,
//                    "Hej!"+
//                            " We're glad you're here :D",
//                            "How about having your free coffee?",
//                            "0934197445",
//           notification_id);