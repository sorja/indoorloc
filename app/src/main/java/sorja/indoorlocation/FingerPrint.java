package sorja.indoorlocation;

/**
 * Created by mxO on 6.12.2015.
 */
public class FingerPrint {
//    Basically it should consist of a positioning function in your
//    favorite programming environment, that will return
//    co-ordinates (x,y,z) based on the fingerprint given as input
//    (mac address - rssi -pairs).

    private String ssid;
    private String mac;
    private int rssi;
    private long timestamp;

    public FingerPrint(){}
    public FingerPrint(String ssid, int rssi, String mac, long timestamp) {
        this.ssid = ssid;
        this.rssi = rssi;
        this.mac = mac;
        this.timestamp = timestamp;
    }

    // make it look part of json
    public String toJson() {
        return "FingerPrint:" + "|" +
                "ssid:" + ssid +  "|" +
                "rssi: " + rssi + "|" +
                "mac: " + mac + "|" +
                "timestamp=" + timestamp;
    }
}
