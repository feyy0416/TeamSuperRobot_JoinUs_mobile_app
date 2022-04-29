package edu.neu.madcourse.joinus;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private TextView tvUsername;
    private TextView cityName;
    private RecyclerView hotEvents;
    private LocationRequest locationRequest;
    private double latitude;
    private double longitude;
    private DatabaseReference reference;
    private FirebaseUser user;
    private String userID;
    private String loginUsername = "";
    //private Button btnLogOut;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(5000);
        locationRequest.setFastestInterval(2000);

        //display current location
        cityName = findViewById(R.id.location);
        getCurrentLocation();

        //display username
        tvUsername = findViewById(R.id.username);
        user = FirebaseAuth.getInstance().getCurrentUser();
        userID = user.getUid();
        reference = FirebaseDatabase.getInstance().getReference();

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                loginUsername =
                        snapshot.child("users").child(userID).child("username").getValue(String.class);
                tvUsername.setText("Hi, " + loginUsername + "!");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MainActivity.this, "Something wrong happened!", Toast.LENGTH_LONG).show();
            }
        });

        //loginUsername = getIntent().getStringExtra("username");

        //btnLogOut = findViewById(R.id.btn_log_out);
        /*btnLogOut.setOnClickListener(view -> {
            mAuth.signOut();
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
        }); */

        hotEvents = findViewById(R.id.rcv_hot_events);
        hotEvents.setHasFixedSize(true);
        hotEvents.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,
                false));

        List<Event> hotEventsList = new ArrayList<>();

        hotEventsList.add(new Event("a", 0, 0, "0", "a", R.drawable.app_logo_round, "a", "This is title a",
                "XXXXXXXXXXXXXXXXXXXXX", "email"));
        hotEventsList.add(new Event("b", 0, 0, "0", "a", R.drawable.app_logo_round, "b", "This is " +
                "title b",
                "XXXXXXXXXXXXXXXXXXXXX", "email"));
        hotEventsList.add(new Event("c", 0, 0, "0", "a", R.drawable.app_logo_round, "c", "This is " +
                "title c",
                "XXXXXXXXXXXXXXXXXXXXX", "email"));

        HotEventsAdapter  hotEventsAdapter = new HotEventsAdapter(hotEventsList);
        hotEvents.setAdapter(hotEventsAdapter);
    }

    private void getCurrentLocation() {
        if (ActivityCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

            if (isGPSEnabled()) {
                LocationServices.getFusedLocationProviderClient(MainActivity.this).requestLocationUpdates(locationRequest, new LocationCallback() {
                    @Override
                    public void onLocationResult(LocationResult locationResult) {
                        super.onLocationResult(locationResult);
                        LocationServices.getFusedLocationProviderClient(MainActivity.this).removeLocationUpdates(this);

                        if (locationResult != null && locationResult.getLocations().size() >0){
                            int position = locationResult.getLocations().size() - 1;
                            Geocoder geocoder = new Geocoder(getApplicationContext(),
                                    Locale.getDefault());
                            latitude = locationResult.getLocations().get(position).getLatitude();
                            longitude = locationResult.getLocations().get(position).getLongitude();
                            List<Address> addresses = null;
                            try {
                                addresses = geocoder.getFromLocation(latitude,
                                        longitude, 1);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            cityName.setText(addresses.get(0).getLocality() + ", " + addresses.get(0).getAdminArea());
                        }
                    }}, Looper.getMainLooper());
            } else {
                enableGPS();
            }

        } else {
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }
    }

    private boolean isGPSEnabled() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        boolean isEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        return isEnabled;
    }

    private void enableGPS() {
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder().addLocationRequest(locationRequest);
        builder.setAlwaysShow(true);
        Task<LocationSettingsResponse> task = LocationServices.getSettingsClient(getApplicationContext()).checkLocationSettings(builder.build());

        task.addOnCompleteListener(new OnCompleteListener<LocationSettingsResponse>() {
            @Override
            public void onComplete(Task<LocationSettingsResponse> locationTask) {

                try {
                    LocationSettingsResponse locationResponse = locationTask.getResult(ApiException.class);
                    Toast.makeText(MainActivity.this, "GPS is already enabled", Toast.LENGTH_LONG).show();

                } catch (ApiException e) {
                    switch (e.getStatusCode()) {
                        case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:

                            try {
                                ResolvableApiException resolvableApiException = (ResolvableApiException) e;
                                resolvableApiException.startResolutionForResult(MainActivity.this, 2);
                            } catch (IntentSender.SendIntentException ex) {
                                ex.printStackTrace();
                            }
                            break;

                        case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                            break;
                    }
                }
            }
        });

    }

    @Override
    public void onRequestPermissionsResult(int request, String[] permissions, int[] result) {
        super.onRequestPermissionsResult(request, permissions, result);
        if (request == 1){

            if (result[0] == PackageManager.PERMISSION_GRANTED){

                if (isGPSEnabled()) {
                    getCurrentLocation();
                }else {
                    enableGPS();
                }
            }

        }
    }

    @Override
    protected void onActivityResult(int request, int result, Intent intent) {
        super.onActivityResult(request, result, intent);
        if (request == 2) {
            if (result == Activity.RESULT_OK) {
                getCurrentLocation();
            }
        }
    }

    public double getLatitude(){ return latitude;}
    public double getLongitude(){ return longitude;}
}
