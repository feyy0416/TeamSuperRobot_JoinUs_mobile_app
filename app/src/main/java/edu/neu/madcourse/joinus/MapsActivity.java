package edu.neu.madcourse.joinus;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.SystemClock;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.inputmethod.EditorInfo;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMyLocationButtonClickListener;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import edu.neu.madcourse.joinus.databinding.ActivityMapsBinding;
import edu.neu.madcourse.joinus.util.Utils;

public class MapsActivity extends AppCompatActivity
        implements OnMapReadyCallback,
        OnMyLocationButtonClickListener,
        ActivityCompat.OnRequestPermissionsResultCallback {

    private GoogleMap mMap;
    private ActivityMapsBinding binding;
    private List<Event> eventList= new ArrayList<>();;
    private MapsAdapter mapsAdapter;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager rLayoutManger;
    private View mapView;
    BottomNavigationView bottomNavigationView;

    private DatabaseReference mDatabase;
    private static final String tableName = "Events";

    private double currentLatitude;
    private double currentLongitude;
    ProgressBar progressBar;
    private int timer;

    /**
     * Request code for location permission request.
     *
     * @see #onRequestPermissionsResult(int, String[], int[])
     */
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;

    /**
     * Flag indicating whether a requested permission has been denied after returning in
     * {@link #onRequestPermissionsResult(int, String[], int[])}.
     */
    private boolean permissionDenied = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            currentLatitude = extras.getDouble("lati");
            currentLongitude = extras.getDouble("long");
            //The key argument here must match that used in the other activity
        }

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

//        eventList.add(new Event("a", 0, 0,
//                "2/2/2022", "a", 1,"a", "a",
//                "XXXXXXXXXXXXXXXXXXXXX", "email"));
//        eventList.add(new Event("b", 0, 0,
//                "3/3/2022", "a", 1,"a", "b",
//                "XXXXXXXXXX" +
//                " XXXXXX XXXXX", "email2"));
//        eventList.add(new Event("c", 0, 0,
//                "3/3/2022", "a", 1,"a", "REQD",
//                "XXXXXXXXXX" +
//                        " XXXXXX XXXXX", "email2"));


        bottomNavigationView = findViewById(R.id.bottom_nav);


        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child(tableName).addValueEventListener(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        if (snapshot.hasChildren()) {
                            for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                Event event = dataSnapshot.getValue(Event.class);
                                eventList.add(event);
                                LatLng pos = new LatLng(event.getLatitude(), event.getLongitude());
                                Log.d("test marker", String.valueOf(event.getLatitude()));
                                mMap.addMarker(new MarkerOptions()
                                        .position(pos)
                                        .title(event.getTitle()));
                            }
                        }
//                        eventList.sort(Comparator.comparing(o -> o.getDistance()));
                        Collections.sort(eventList, new Comparator<Event>() {
                            @Override
                            public int compare(Event event1, Event event2) {
                                double lat1 = event1.getLatitude();
                                double lon1 = event1.getLongitude();
                                double lat2 = event2.getLatitude();
                                double lon2 = event2.getLongitude();
                                return (int) (Utils.getDistance(currentLatitude, currentLongitude, lat1, lon1)
                                        - Utils.getDistance(currentLatitude, currentLongitude, lat2, lon2));
                            }
                        });
                        updateView();
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });
//        mapView = findViewById(R.id.map_frame);
//        mapView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
//            @Override
//            public void onGlobalLayout() {
//                SystemClock.sleep(1000);
//                progressBar.setVisibility(View.GONE);
//            }
//        });
        progressBar = findViewById(R.id.pBar);
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (timer<10){
                    timer++;
                    try{
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                progressBar.setProgress(timer);
                                if (timer>8){
                                    progressBar.setVisibility(View.GONE);

                                }
                            }
                        });
                        Thread.sleep(200);
                    }catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
                timer= 0;
            }

        }).start();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu event) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.event_menu, event);
        MenuItem searchItem = event.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                mapsAdapter.getFilter().filter(newText);
                return false;
            }
        });
        return true;
    }

    private void updateView() {
        updateRecyclerView(eventList);
        bottomNavigationView.setSelectedItemId(R.id.menu_chat);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.menu_home:
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.menu_search:
                        //pass value to add event activity
                        Intent tempIntent  = new Intent(getBaseContext(), EventActivity.class);
                        tempIntent.putExtra("lati", currentLatitude);
                        tempIntent.putExtra("long", currentLongitude);
                        startActivity(tempIntent);
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.menu_chat:
                        return true;
                    case R.id.menu_account:
                        Intent intent_maps  = new Intent(getBaseContext(), AccountActivity.class);
                        intent_maps.putExtra("lati", currentLatitude);
                        intent_maps.putExtra("long", currentLongitude);
                        startActivity(intent_maps);
                        overridePendingTransition(0, 0);
                        return true;
                }
                return false;
            }
        });
    }
    private void updateRecyclerView(List<Event> eventList){
        rLayoutManger = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView = findViewById(R.id.rcv_map_events);
        recyclerView.setNestedScrollingEnabled(true);
        mapsAdapter = new MapsAdapter(eventList, this, currentLatitude, currentLongitude);
        recyclerView.setAdapter(mapsAdapter);
        recyclerView.setLayoutManager(rLayoutManger);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
//        Log.d("cur lat", String.valueOf(currentLatitude));
//        Log.d("curlong", String.valueOf(currentLongitude));
        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-33.852, 151.211);
//        mMap.addMarker(new MarkerOptions()
//                .position(sydney)
//                .title("Marker in Sydney"));
        // [START_EXCLUDE silent]
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

        LatLng curLoc = new LatLng(currentLatitude, currentLongitude);
////        mMap.addMarker(new MarkerOptions().position(sydney).title("User's position"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(curLoc));
        mMap.moveCamera(CameraUpdateFactory.zoomTo(15.0f));
        mMap.setOnMyLocationButtonClickListener(this);
//        Log.d("test marker", String.valueOf(eventList.size()));
//        for(Event event: eventList){
//            LatLng pos = new LatLng(event.getLatitude(), event.getLongitude());
//            Log.d("test marker", String.valueOf(event.getLatitude()));
//            mMap.addMarker(new MarkerOptions()
//                    .position(pos)
//                    .title(event.getTitle()));
//            mMap.moveCamera(CameraUpdateFactory.newLatLng(pos));
//        }
        enableMyLocation();

    }

    private void enableMyLocation() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            if (mMap != null) {
                mMap.setMyLocationEnabled(true);
            }
        } else {
            // Permission to access the location is missing. Show rationale and request permission
            PermissionUtils.requestPermission(this, LOCATION_PERMISSION_REQUEST_CODE,
                    Manifest.permission.ACCESS_FINE_LOCATION, true);
        }
    }

    @Override
    public boolean onMyLocationButtonClick() {
        Log.d("Loc", "MyLocation button clicked");
        return false;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode != LOCATION_PERMISSION_REQUEST_CODE) {
            return;
        }

        if (PermissionUtils.isPermissionGranted(permissions, grantResults, Manifest.permission.ACCESS_FINE_LOCATION)) {
            // Enable the my location layer if the permission has been granted.
            enableMyLocation();
        } else {
            // Permission was denied. Display an error message
            // [START_EXCLUDE]
            // Display the missing permission error dialog when the fragments resume.
            permissionDenied = true;
            // [END_EXCLUDE]
        }
    }

    @Override
    public void onResume(){
        super.onResume();
        updateView();
    }

    @Override
    protected void onResumeFragments() {
        super.onResumeFragments();
        if (permissionDenied) {
            // Permission was not granted, display error dialog.
            showMissingPermissionError();
            permissionDenied = false;
        }
    }

    private void showMissingPermissionError() {
        PermissionUtils.PermissionDeniedDialog
                .newInstance(true).show(getSupportFragmentManager(), "dialog");
    }


}

