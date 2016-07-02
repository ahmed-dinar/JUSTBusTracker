package com.ahmeddinar.justbustracker;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.ahmeddinar.justbustracker.rest.RestCient;
import com.ahmeddinar.justbustracker.rest.model.BusLocation;
import com.ahmeddinar.justbustracker.rest.service.ApiService;
import com.ahmeddinar.justbustracker.utils.MyMapUtils;
import com.akexorcist.googledirection.DirectionCallback;
import com.akexorcist.googledirection.GoogleDirection;
import com.akexorcist.googledirection.constant.AvoidType;
import com.akexorcist.googledirection.constant.Language;
import com.akexorcist.googledirection.constant.TransitMode;
import com.akexorcist.googledirection.constant.TransportMode;
import com.akexorcist.googledirection.model.Direction;
import com.akexorcist.googledirection.model.Leg;
import com.akexorcist.googledirection.model.Route;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;


public class MainActivity extends FragmentActivity implements OnMapReadyCallback {

    TextView textView;
    private GoogleMap myMap;
    private static Map<String, LatLng> stoppages;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        textView = (TextView) findViewById(R.id.mapText);

        try {
            SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.mapFragment);
            mapFragment.getMapAsync(this);


        } catch (Exception e) {
            textView.setText(e.getMessage().toString());
            e.printStackTrace();
        }


    }


    @Override
    public void onMapReady(GoogleMap googleMap) {

        if(googleMap==null){
            showAlertExit("Error","Stooped Working");
            return;
        }

        Toast.makeText(getApplicationContext(), "Yay! map loaded", Toast.LENGTH_SHORT).show();

        myMap = googleMap;
        stoppages = MyMapUtils.getStoppages();

        addRoute(stoppages.get("Monihar"), stoppages.get("JUST"));
        addRoute(stoppages.get("Chachra"), stoppages.get("Palbari"));


        BitmapDescriptor busStopIcon = BitmapDescriptorFactory.fromResource(R.drawable.bs);

        for (Map.Entry<String, LatLng> entry : stoppages.entrySet()) {
            LatLng pos = entry.getValue();
            myMap.addMarker(new MarkerOptions().position(pos).title(entry.getKey()).icon(busStopIcon));
        }


        //check permission
        if (Build.VERSION.SDK_INT >= 23 && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            showAlertExit("sdk loction Permision","Stooped Working");
            return;
        }
        myMap.setMyLocationEnabled(false);


        myMap.moveCamera(CameraUpdateFactory.newLatLngZoom(stoppages.get("Palbari"),13.06f));


        final Map<String, Marker> busMarker = new HashMap<>();

        textView.setText("Calling api..");

        RestCient client = new RestCient();
        final ApiService service = client.getApiService();


        final Observable<List<BusLocation>> call = service.get();

        final int[] x = {0};
        final BitmapDescriptor busIcon = BitmapDescriptorFactory.fromResource(R.drawable.busico);

        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        call
                                .subscribeOn(Schedulers.newThread())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(new Subscriber<List<BusLocation>>() {
                                    @Override
                                    public void onCompleted() {


                                    }

                                    @Override
                                    public void onError(Throwable e) {
                                        textView.setText(e.getMessage().toString());
                                    }

                                    @Override
                                    public void onNext(List<BusLocation> busLocations) {

                                        if (busLocations == null) {
                                            textView.setText("bus Locationres null");
                                            return;
                                        }

                                        textView.setText(Integer.toString(x[0]));
                                        x[0]++;

                                        for(BusLocation bus : busLocations) {

                                            LatLng ltlng = new LatLng(Double.parseDouble(bus.getLatitude()),Double.parseDouble(bus.getLongitude()));
                                            MarkerOptions mo = new MarkerOptions();
                                            mo.position(ltlng).title("Bus").icon(busIcon);

                                            Marker mrk = busMarker.get(bus.getId());
                                            if(  mrk != null ){
                                                mrk.remove();
                                            }
                                            mrk = myMap.addMarker(mo);
                                            busMarker.put(bus.getId(),mrk);

                                            textView.setText("(" + textView.getText() + ") " + bus.getId() + ". " + bus.getLatitude() + " " + bus.getLongitude() + "\n" );
                                        }

                                    }
                                });

                    }
                });
            }
        }, 1000 * 0, 1000 * 7);

    }


    /**
     * Add route in map
     * @param origin
     * @param destination
     */
    private void addRoute(LatLng origin, LatLng destination) {

        String serverKey = "AIzaSyBbriNiFtf28jBt3ex71KcRv-y0yWZtUI8";



        GoogleDirection.withServerKey(serverKey)
                .from(origin)
                .to(destination)
                .language(Language.ENGLISH)
                .transportMode(TransportMode.DRIVING)
                .avoid(AvoidType.INDOOR)
                .transitMode(TransitMode.BUS)
                .execute(new DirectionCallback() {
                    @Override
                    public void onDirectionSuccess(Direction direction, String rawBody) {
                        String status = direction.getStatus();
                        Toast.makeText(getApplicationContext(), "get dir with status: " + status, Toast.LENGTH_SHORT).show();
                        if (direction.isOK()) {

                            Toast.makeText(getApplicationContext(), "Adding polyline.......", Toast.LENGTH_SHORT).show();

                            Route route = direction.getRouteList().get(0);
                            Leg leg = route.getLegList().get(0);
                            ArrayList<LatLng> directionPositionList = leg.getDirectionPoint();

                            PolylineOptions rectLine = new PolylineOptions().width(7).color(
                                    Color.BLUE);
                            for (int i = 0; i < directionPositionList.size(); i++) {
                                rectLine.add(directionPositionList.get(i));
                            }
                            Polyline polylin = myMap.addPolyline(rectLine);


                        } else {
                            Toast.makeText(getApplicationContext(), "get dir but NOT OK :O", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onDirectionFailure(Throwable t) {
                        Toast.makeText(getApplicationContext(), "direction fail", Toast.LENGTH_SHORT).show();
                    }
                });
    }



    /**
     * On Failure exit app
     */
    private void showAlertExit(String title,String msg) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(msg)
                .setTitle(title)
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        finish();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }
}
