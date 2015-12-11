# indoorloc

## Install

Easiest way through Android Studio.
Just hit run.

## Thoughts

In the end, it was way easier to create the algorithm then I was expecting.
Everything could be redone, with way less fatigue.

## Steps to create indoor location O(n²):

  - Finger prints!
    - Get enough mac - rssi pairs with XYZ position.
    - When getting the location, go thru the saved fingerprints, let's call this list allfingerprints and the current 'you are here' fingerprint called here as currentfingerprint
    - Find the closest match, ex. pseudo:

##### Here you could also just go with the EUCL. distance instead of doing it in another method after.
##### Like mentioned above, everything could be done faster better harder stronger
```
minfp = null;
min = int_max;

foreach fp : allfingerprints
  foreach currfp : currentfingerprint
    if fp.mac equals currfp.mac
        int diff = abs[fp.rssi - currfp.rssi]
        if diff < min
          minfp = currfp
          min = diff
return minfp
```

## Final thoughts
  - Java = horrible (not as horrible as some other...)
  - Algo = easy, no real algo here imo, nearest neighbour
  - Very low amount of experience with android, seems pretty easy, although stackoverflow and android dev docs were very close.
  - [ZoomableImageView](http://stackoverflow.com/a/17649895) found this on stackoverflow and edited it a little.
    - Added detection for touch to find the X and Y points on click. Little problem was to find the correct as X Y that remain the same when zooming or moving the map.
 - App is pretty buggy, especially because my thoughts weren't that clear when making it. Especially scanprint and fingerprint classes seem to pretty obsolete. And the data/event flow is horrible creating a lot of problems.
 - Better way of creating the app (might be done in coming weeks - maybe)
   - Show pos if data found for curr mac addr
   - Click / select 'mapping' to stop looping saved data and start mapping.
   - Click xy pos on map --> save xyz + fp --> scan wifis (* 1 - 5 ) -->notify user scan done --> don't start new scan yet
