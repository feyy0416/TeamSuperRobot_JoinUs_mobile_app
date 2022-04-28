package edu.neu.madcourse.joinus;

import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

import edu.neu.madcourse.joinus.databinding.ActivityMapsBinding;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityMapsBinding binding;
    private List<Event> eventList= new ArrayList<>();;
    private MapsAdapter mapsAdapter;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager rLayoutManger;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        eventList.add(new Event("a", 0, 0, "2/2/2022", "a", 1,"a", "This is title a", "XXXXXXXXXXXXXXXXXXXXX", "email"));
        eventList.add(new Event("b", 0, 0, "3/3/2022", "a", 1,"a", "This is title b", "XXXXXXXXXX XXXXXX XXXXX", "email2"));
        updateView();
    }

    private void updateView() {

        updateRecyclerView(eventList);
    }
    private void updateRecyclerView(List<Event> eventList){
        rLayoutManger = new LinearLayoutManager(this);
        recyclerView = findViewById(R.id.rcv_map_events);
        mapsAdapter = new MapsAdapter(this, this.eventList);
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

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }
}

