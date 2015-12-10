package sorja.indoorlocation;

import android.content.Context;
import android.graphics.Point;
import android.util.Log;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
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

    public Location(Context context) {
        this.context = context;
    }

    public Point getLocation() {
        Point p = new Point();
        p.x = 25;
        p.y = 25;
        String path = context.getFilesDir().getAbsolutePath();
        String fullpath = context.getFilesDir().getAbsolutePath() +"/"+ filename;
        try {
            input = new Input(context.openFileInput(filename));
            scanresults = kryo.readObject(input, scanresults.getClass());
            input.close();
            for (ScanPrint s :
                    scanresults) {
                Log.d(TAG, "x" + s.x + " y" +s.y+ " z" + s.z);
            }
        } catch (Exception e) {e.printStackTrace();}

        return p;
    }
}
