package com.example.kendall.hiddenhistory;

import android.*;
import android.Manifest;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.w3c.dom.Text;

public class AdventureDisplayActivity extends FragmentActivity implements OnMapReadyCallback {

    private String email;
    private String token;
    private APICaller caller;
    private String description;
    private String locationName;
    private String findLocationInfo;
    private GoogleMap mMap;
    private LocationListener locationListener;

    @Override
    @TargetApi(23)
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle extras = this.getIntent().getExtras();
        this.email = extras.getString("email");
        this.token = extras.getString("token");

        setContentView(R.layout.activity_adventure_display);

        caller = new APICaller();

        Log.d("Test for Extras", "email: " + email + ", token: " + token);

        final Adventure myAdventure = caller.getAdventure(token);

        MapFragment mapFragment = (MapFragment) getFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        description = myAdventure.getDescription();
        locationName = myAdventure.getLocationName();

        /*Adventure myAdventure = caller.startAdventure(token, 35.9, -79, 5, 10, email); */

        TextView adventureDisplay = (TextView) findViewById(R.id.adventure_display);

        final Button findButton = (Button) findViewById(R.id.find_location_button);

        findButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findLocation();
            }
        });

        if (myAdventure != null) {
            Log.d("AdventureMainThread", myAdventure.toString());
            adventureDisplay.setText(locationName);
            adventureDisplay.setTextColor(Color.BLUE);
        } else {
            adventureDisplay.setText("Adventure was null!");
        }
        if ( ContextCompat.checkSelfPermission( this, android.Manifest.permission.ACCESS_COARSE_LOCATION ) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION},
                    23);
        }

        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(android.location.Location location) {
                Log.d("Location Change", "Location was detected as changed");
                float[] distance = new float[1];
                android.location.Location.distanceBetween(location.getLatitude(), location.getLongitude(), myAdventure.getLocation().getLatitude(), myAdventure.getLocation().getLongitude(), distance);
                // distance[0] is now the distance between these lat/lons in meters
                if(distance[0] > 500.0 && distance[0] < 1000.0)
                {
                    findButton.setTextColor(Color.RED);
                    findButton.setText("Under 1000m Away");
                }
                else if (distance[0] > 250.0)
                {
                    findButton.setTextColor(Color.YELLOW);
                    findButton.setText("Under 500m Away");
                }
                else if(distance[0] > 100.0)
                {
                    findButton.setTextColor(Color.YELLOW);
                    findButton.setText("Under 250m Away");
                }
                else if(distance[0] > 50.0)
                {
                    findButton.setTextColor(Color.BLUE);
                    findButton.setText("Under 100m Away");
                }
                else if (distance[0] > 10.0)
                {
                    findButton.setTextColor(Color.GREEN);
                    findButton.setText("Under 50m Away");
                }
                else if (distance[0] <= 10.0) {
                    findButton.setTextColor(Color.GREEN);
                    findButton.setText("Location Found!");
                }
                else
                {
                    findButton.setTextColor(Color.RED);
                    findButton.setText("Over 1000m Away");
                }
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED)
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 0, locationListener);
        else
            Log.e("Security Exception", "Can't update location I guess.");
    }

    protected void findLocation() {
        findLocationInfo = caller.findLocation(token);
        Intent intent = new Intent(this, Narrative.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        this.locationListener = null;
        intent.putExtra("email", email);
        intent.putExtra("token", token);
        intent.putExtra("name", locationName);
        intent.putExtra("description", description);
        intent.putExtra("find_info", findLocationInfo);
        startActivity(intent);
    }

    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        Log.d("Map Ready", "Hey look, the map's ready!");
        // Add a marker in Sydney and move the camera
        LatLng CH = new LatLng(35.9132, -79.0558);
        mMap.addMarker(new MarkerOptions().position(CH).title("Marker in Chapel Hill"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(CH, 18));
        Log.d("Marker Add", "There should be a marker now");

        Location clueLocation = caller.getAdventure(token).getLocation();

        newLocation(clueLocation.getLatitude(), clueLocation.getLongitude());
    }

    public void newLocation(double latitude, double longitude) {
        LatLng currentClue = new LatLng(latitude, longitude);
        mMap.addMarker(new MarkerOptions().position(currentClue).title("Here is your next destination"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentClue, 18));
    }

}
