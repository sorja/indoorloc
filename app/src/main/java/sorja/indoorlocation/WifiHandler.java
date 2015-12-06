package sorja.indoorlocation;

import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.content.Context;
import android.util.Log;

import java.util.List;

/**
 * Created by mxO on 6.12.2015.
 */

public class WifiHandler extends BroadcastReceiver{
    private final String TAG =  "BroadcastReceiver";
    WifiManager wifiManager;
    List<ScanResult> scanResults;
    Context context;

    public WifiHandler(Context context) {
        wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        this.context = context;
       registerListener(context);
    }

    public void registerListener(Context context){
        context.getApplicationContext()
                .registerReceiver(this,
                        new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));
    }

    public void unregisterListener(Context context){
        context.getApplicationContext().unregisterReceiver(this);
    }

    public List<ScanResult> scan(){
        if (!wifiManager.isWifiEnabled()) wifiManager.setWifiEnabled(true);
        wifiManager.startScan();
        return scanResults;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        scanResults = wifiManager.getScanResults();
        for (ScanResult s : scanResults) {
            if(s.level > -1) return;
            String ssid = s.SSID;
            String mac = s.BSSID;
            int rssi = s.level;
            long timestamp = s.timestamp;

            FingerPrint fingerPrint = new FingerPrint(ssid, rssi, mac, timestamp);
            Log.d(TAG, fingerPrint.toJson());
        }

    }
}
