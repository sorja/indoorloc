package sorja.indoorlocation;

import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mxO on 6.12.2015.
 */

public class WifiHandler extends BroadcastReceiver{
    private final String TAG =  "BroadcastReceiver";
    WifiManager wifiManager;
    Context context;
    private List<ScanPrint> allResults = new ArrayList<>();
    private float x;
    private float y;
    private float z;
    private boolean writing;

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

    public void scan(){
        Log.d(TAG, context.getFilesDir()+"");
        if (!wifiManager.isWifiEnabled()) wifiManager.setWifiEnabled(true);
        wifiManager.startScan();
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        List<ScanResult> scanResults  = wifiManager.getScanResults();
        if(x <= 0) return;
        if(y <= 0) return;
        if(z <= -2) return;
        for (ScanResult s : scanResults) {
            if(s.level > -1) return;
            String ssid = s.SSID;
            String mac = s.BSSID;
            int rssi = s.level;
            long timestamp = s.timestamp;
            allResults.add(new ScanPrint(ssid, rssi, mac, timestamp, x, y, z));
        }
        if (writing) writeToFile(context);
    }

    private void writeToFile(Context context) {
        String filename = "scanresults.kryo";
        Output outputStream;

        Kryo kryo = new Kryo();

        try {
            outputStream = new Output(context.openFileOutput(filename + ".tmp", Context.MODE_PRIVATE));
            kryo.writeObject(outputStream, allResults);
            outputStream.close();

            String path = context.getFilesDir().getAbsolutePath();
            try {
                new File(path+"/"+filename).renameTo(new File(path+"/"+filename + ".old"));
            } catch (Exception ex) {}
            new File(path+"/"+filename+".tmp").renameTo(new File(path+"/"+filename));

            try {
                new File(path+"/"+filename + ".old").delete();
            } catch(Exception ex){}

            Input juuh = new Input(context.openFileInput(filename));
            List<ScanPrint> asd = kryo.readObject(juuh, allResults.getClass());
            juuh.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Toast.makeText(context, "Printing done.", Toast.LENGTH_SHORT);
    }

    public void setPoints(float x1, float y1, int z) {
        this.x = x1;
        this.y = y1;
//        ????
        setFloor(z);
    }

    public void setFloor(int z){
        this.z = (float)z;
    }

    public void setWriting(boolean writing) {
        this.writing = writing;
    }
}

