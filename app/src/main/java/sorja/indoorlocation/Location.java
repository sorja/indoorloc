package sorja.indoorlocation;

import android.content.Context;
import android.graphics.Point;
import android.util.Log;
import android.widget.Toast;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 * Created by mxO on 10.12.2015.
 */
public class Location {
    String TAG = "Location";
    Kryo kryo = new Kryo();
    String filename = "scanresults.kryo";
    Input input;
    Context context;
    List<ScanPrint> scanresults = new ArrayList<>();
    List<ScanPrint> fingerprint = new ArrayList<>();

    public Location(Context context, List<ScanPrint> fp) {
        this.context = context;
        this.fingerprint = fp;
    }

    public Point getLocation() {
        if(fingerprint.isEmpty()) Toast.makeText(context,
                "No wifi entries found.", Toast.LENGTH_LONG);
        Point p = new Point();
        ArrayList<ScanPrint> results = new ArrayList<>();
        fetchFromFile();
        if(scanresults.isEmpty()) return p;
        /*
        * Here we loop the CURRENT fingerprint and compare to the list of fingerprints we have
        * SO O(n^2)
        * */
        for (ScanPrint fp : fingerprint ) {
            ScanPrint _fp = closest(fp);
            if (_fp == null) continue;
            results.add(_fp);
        }

        Log.d(TAG, "------------------------------------------------");
        for (ScanPrint s : results) {
            String theString = String.format("" +
                    "X: %f\t"+
                    "Y: %f\t"+
                    "z: %f\t"+
                    "mac: %s\t"+
                    "rssi: %s\t"+
                    "ssid: %s\t" ,s.x,s.y,s.z,s.getMac(), s.getRssi(), s.getSsid());
            Log.d(TAG, theString);
        }
        Log.d(TAG, "------------------------------------------------");

        return p;
    }

    private ScanPrint closest(ScanPrint fp) {
        int n = fp.getRssi();
        if(n >= 0) return null;
        int dist = Math.abs(scanresults.get(0).getRssi() - n);
        ScanPrint closest = null;

        for (ScanPrint s : scanresults ) {
            if(!s.getMac().equalsIgnoreCase(fp.getMac())) continue;

            int m = s.getRssi();
            int curr = Math.abs(n-m);
            if(curr < dist){
                closest = s;
                dist = curr;
            }
        }

        return closest;
    }

    private void fetchFromFile() {
        List<ScanPrint> kiitosjava2015 = new ArrayList<>();
        try {
            input = new Input(context.openFileInput(filename));
            kiitosjava2015 = kryo.readObject(input, scanresults.getClass());
            input.close();
        } catch (Exception e) {e.printStackTrace();}
        Iterator<ScanPrint> сука = kiitosjava2015.iterator();
        while (сука.hasNext()){
            ScanPrint s = сука.next();
            if (s.x > 0 && s.y > 0 && s.z > -2) scanresults.add(s);
        }

    }
}
