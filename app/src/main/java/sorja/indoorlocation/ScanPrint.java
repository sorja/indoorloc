package sorja.indoorlocation;

/**
 * Created by mxO on 6.12.2015.
 */
public class ScanPrint extends FingerPrint {
    int x;
    int y;
    int z;

    public ScanPrint(String ssid, int rssi, String mac, long timestamp, int x, int y, int z) {
        super(ssid, rssi, mac, timestamp);
        this.x = x;
        this.y = y;
        this.z = z;
    }
}
