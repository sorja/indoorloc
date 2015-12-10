package sorja.indoorlocation;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import java.util.Random;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private boolean mapping = false;
    Bitmap bm;
    ZoomableImageView touch;
    private int floor;
    private Toolbar toolbar;
    WifiHandler wifiHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        /***********************
         * Non generated stuff *
         ***********************/
        wifiHandler = new WifiHandler(getApplicationContext());
        toolbar.setTitle("Floor: "+floor);
        floor = -1;
        touch = (ZoomableImageView) findViewById(R.id.imageView);
        ((ZoomableImageView) findViewById(R.id.imageView)).floor = -1;
        /*png to bm*/
        bm = BitmapFactory.decodeResource(getResources(), R.drawable.exactum1);
        Bitmap bmOverlay = Bitmap.createBitmap(bm.getWidth(), bm.getHeight(), bm.getConfig());
        touch.setImageBitmap(bmOverlay);
        touch.setwifiHandler(wifiHandler);
        wifiHandler.setFloor(floor);
        wifiHandler.scan();

        /**********************
         * //Non generated stuff *
         ***********************/

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        wifiHandler.unregisterListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    // stop mapping here
    // change title to floor
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        touch.setDrawing(false);
        touch.showLocation();
        switch (item.getItemId()) {
            case R.id.switchId:
                mapping = !item.isChecked();
                item.setChecked(mapping);
                if (mapping) {
                    Random rnd = new Random();
                    toolbar.setTitleTextColor(Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256)));
                    Toast.makeText(getApplicationContext(), "Finding position",
                            Toast.LENGTH_SHORT).show();
                } else {
                    toolbar.setTitleTextColor(Color.argb(255, 255, 255, 255));
                }
                return true;
            case R.id.stopscan:
                wifiHandler.unregisterListener(getApplicationContext());
                return true;
            case R.id.startscan:
                wifiHandler.registerListener(getApplicationContext());
                wifiHandler.scan();
            default:
                return false;
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        /* dry etc :-)
        * in prod should be done better.. :zz:
        * */
        switch (item.getItemId()) {
            case R.id.floor0:
                bm = BitmapFactory.decodeResource(getResources(), R.drawable.exactumk);
                touch.setImageBitmap(bm);
                floor = 0;
                toolbar.setTitle("Floor: "+floor);
                ((ZoomableImageView) findViewById(R.id.imageView)).floor = floor;
                break;
            case R.id.floor1:
                bm = BitmapFactory.decodeResource(getResources(), R.drawable.exactum1);
                touch.setImageBitmap(bm);
                floor = 1;
                toolbar.setTitle("Floor: "+floor);
                ((ZoomableImageView) findViewById(R.id.imageView)).floor = floor;
                break;
            case R.id.floor2:
                bm = BitmapFactory.decodeResource(getResources(), R.drawable.exactum2);
                touch.setImageBitmap(bm);
                floor = 2;
                toolbar.setTitle("Floor: "+floor);
                ((ZoomableImageView) findViewById(R.id.imageView)).floor = floor;
                break;
            case R.id.floor3:
                bm = BitmapFactory.decodeResource(getResources(), R.drawable.exactum3);
                touch.setImageBitmap(bm);
                floor = 3;
                toolbar.setTitle("Floor: "+floor);
                ((ZoomableImageView) findViewById(R.id.imageView)).floor = floor;
                break;
            case R.id.floor4:
                bm = BitmapFactory.decodeResource(getResources(), R.drawable.exactum4);
                touch.setImageBitmap(bm);
                floor = 4;
                toolbar.setTitle("Floor: "+floor);
                ((ZoomableImageView) findViewById(R.id.imageView)).floor = floor;
                break;
            default:
                bm = BitmapFactory.decodeResource(getResources(), R.drawable.exactumk);
                touch.setImageBitmap(bm);
                floor = 0;
                toolbar.setTitle("Floor: "+floor);
                ((ZoomableImageView) findViewById(R.id.imageView)).floor = floor;
                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
