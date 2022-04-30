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
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
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
import com.google.android.material.bottomnavigation.BottomNavigationView;
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

import edu.neu.madcourse.joinus.auth.LoginActivity;
import edu.neu.madcourse.joinus.auth.SignupActivity;
import edu.neu.madcourse.joinus.util.Utils;

public class MainActivity extends AppCompatActivity {
    private TextView tvUsername;
    private TextView cityName;
    private RecyclerView hotEvents;
    private RecyclerView sports;
    private RecyclerView study;
    private RecyclerView cooking;
    private RecyclerView others;

    private LocationRequest locationRequest;
    private double latitude;
    private double longitude;
    private DatabaseReference reference;
    private FirebaseUser user;
    private String userID;
    private String loginUsername = "";
    //private Button btnLogOut;
    private BottomNavigationView bottomNavigationView;

    private Intent tempIntent;

    private List<Event> otherEventList;
    private List<Event> cookingEventList;
    private List<Event> studyEventList;
    private List<Event> sportEventList;
    private List<Event> recEventList;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        otherEventList = new ArrayList<>();
        cookingEventList = new ArrayList<>();
        studyEventList = new ArrayList<>();
        sportEventList = new ArrayList<>();
        recEventList = new ArrayList<>();
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottom_nav);
        updateView();

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

        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("Events").addValueEventListener(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        if (snapshot.hasChildren()) {
                            for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                Event e = dataSnapshot.getValue(Event.class);
                                recEventList.add(e);


                            }
                        }
//                        eventList.sort(Comparator.comparing(o -> o.getDistance()));

                        createRecRecyclerView();
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });

        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("Events").addValueEventListener(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        if (snapshot.hasChildren()) {
                            for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                Event e = dataSnapshot.getValue(Event.class);
                                if ("Sport".equals(e.getCategory())){
                                    sportEventList.add(e);
                                    Log.d("event added!!!!!!!!!!!!!", e.getEventId());
                                }

                            }
                        }
//                        eventList.sort(Comparator.comparing(o -> o.getDistance()));

                        createSportRecyclerView();
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });


        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("Events").addValueEventListener(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        if (snapshot.hasChildren()) {
                            for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                Event e = dataSnapshot.getValue(Event.class);
                                if ("Study".equals(e.getCategory())){
                                    studyEventList.add(e);
                                    Log.d("event added!!!!!!!!!!!!!", e.getEventId());
                                }

                            }
                        }
//                        eventList.sort(Comparator.comparing(o -> o.getDistance()));

                        createStudyRecyclerView();
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });


        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("Events").addValueEventListener(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        if (snapshot.hasChildren()) {
                            for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                Event e = dataSnapshot.getValue(Event.class);
                                if ("Cooking".equals(e.getCategory())){
                                    cookingEventList.add(e);
                                    Log.d("event added!!!!!!!!!!!!!", e.getEventId());
                                }

                            }
                        }
//                        eventList.sort(Comparator.comparing(o -> o.getDistance()));

                        createCookingRecyclerView();
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });


        //others


        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("Events").addValueEventListener(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        if (snapshot.hasChildren()) {
                            for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                Event e = dataSnapshot.getValue(Event.class);
                                if ("Other".equals(e.getCategory())){
                                    otherEventList.add(e);
                                    Log.d("event added!!!!!!!!!!!!!", e.getEventId());
                                }

                            }
                        }
//                        eventList.sort(Comparator.comparing(o -> o.getDistance()));

                        createOthersRecyclerView();
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });

    }

    private void createRecRecyclerView(){
        hotEvents = findViewById(R.id.rcv_hot_events);
        hotEvents.setHasFixedSize(true);
        hotEvents.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,
                false));
        HotEventsAdapter othersAdapter = new HotEventsAdapter(recEventList);
        hotEvents.setAdapter(othersAdapter);
    }

    private void createCookingRecyclerView(){
        cooking = findViewById(R.id.rcv_cooking);
        cooking.setHasFixedSize(true);
        cooking.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,
                false));
        HotEventsAdapter othersAdapter = new HotEventsAdapter(cookingEventList);
        cooking.setAdapter(othersAdapter);
    }

    private void createStudyRecyclerView(){
        study = findViewById(R.id.rcv_study);
        study.setHasFixedSize(true);
        study.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,
                false));
        HotEventsAdapter othersAdapter = new HotEventsAdapter(studyEventList);
        study.setAdapter(othersAdapter);
    }

    private void createSportRecyclerView(){
        sports = findViewById(R.id.rcv_sports);
        sports.setHasFixedSize(true);
        sports.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,
                false));
        HotEventsAdapter othersAdapter = new HotEventsAdapter(sportEventList);
        sports.setAdapter(othersAdapter);
    }

    private void createOthersRecyclerView(){
        for(Event e : otherEventList){
            Log.d("event:::", e.getEventId());
        }
        others = findViewById(R.id.rcv_others);
        others.setHasFixedSize(true);
        others.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,
                false));
        HotEventsAdapter othersAdapter = new HotEventsAdapter(otherEventList);
        others.setAdapter(othersAdapter);
        Log.d("event rvc created!!!!!!!!!!!!!", "YEAHHHHHHHH");
    }


    @Override
    public void onResume() {
        super.onResume();
        updateView();
    }

    private void updateView() {

        bottomNavigationView.setSelectedItemId(R.id.menu_home);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.menu_home:

                        return true;
                    case R.id.menu_search:
                        //pass value to add event activity
                        tempIntent  = new Intent(getBaseContext(), EventActivity.class);
                        tempIntent.putExtra("lati", latitude);
                        tempIntent.putExtra("long", longitude);
                        startActivity(tempIntent);
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.menu_chat:
                        tempIntent  = new Intent(getBaseContext(), MapsActivity.class);
                        tempIntent.putExtra("lati", latitude);
                        tempIntent.putExtra("long", longitude);
//                            startActivity(intent);
                        startActivity(tempIntent);
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.menu_account:
                        Intent intent_maps  = new Intent(getBaseContext(), AccountActivity.class);
                        intent_maps.putExtra("lati", latitude);
                        intent_maps.putExtra("long", longitude);
                        startActivity(intent_maps);
                        overridePendingTransition(0, 0);
                        return true;
                }
                return false;
            }
        });
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
                            //Geocoder geocoder = new Geocoder(getApplicationContext(),
                                    //Locale.getDefault());
                            latitude = locationResult.getLocations().get(position).getLatitude();
                            longitude = locationResult.getLocations().get(position).getLongitude();


                            /*List<Address> addresses = null;
                            try {
                                addresses = geocoder.getFromLocation(latitude,
                                        longitude, 1);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }*/
                            try {
                                cityName.setText(Utils.getCityName(latitude, longitude));
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
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

}
