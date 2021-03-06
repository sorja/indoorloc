package sorja.indoorlocation;

import android.graphics.Point;

/**
 * Created by mxO on 6.12.2015.
 */
public class ScanPrint extends FingerPrint {
    float x;
    float y;
    float z;

    public ScanPrint() {
    }

    public ScanPrint(String ssid, int rssi, String mac, long timestamp, float x, float y, float z) {
        super(ssid, rssi, mac, timestamp);
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getZ() {
        return z;
    }

    public Point getPoint() {
        return new Point( (int) x , (int) y);
    }
}
