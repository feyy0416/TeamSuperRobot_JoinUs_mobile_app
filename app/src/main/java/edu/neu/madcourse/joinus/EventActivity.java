package edu.neu.madcourse.joinus;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.SearchView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import edu.neu.madcourse.joinus.auth.LoginActivity;
import edu.neu.madcourse.joinus.auth.User;
import edu.neu.madcourse.joinus.util.Utils;

public class EventActivity extends AppCompatActivity {

    private List<Event> eventList;
    private EventAdapter eventAdapter;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager rLayoutManger;

    private static final String tableName = "Events";
    private DatabaseReference mDatabase;

    private double currentLatitude;
    private double currentLongitude;
    private FloatingActionButton addEventBtn;

    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_list);

        addEventBtn = findViewById(R.id.add_event);
        addEventBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EventActivity.this, AddEventActivity.class);
                startActivity(intent);
            }
        });

        eventList = new ArrayList<>();

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            currentLatitude = extras.getDouble("lati");
            currentLongitude = extras.getDouble("long");

            //The key argument here must match that used in the other activity
        }


        updateView();

        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child(tableName).addValueEventListener(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        if (snapshot.hasChildren()) {
                            for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                Event e = dataSnapshot.getValue(Event.class);
                                eventList.add(e);
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
                        createRecyclerView();
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });


//        eventList.add(new Event("a", 0, 0, "2/2/2022", "a", 1,"a", "This is title a", "XXXXXXXXXXXXXXXXXXXXX", "email"));
//        eventList.add(new Event("b", 0, 0, "3/3/2022", "a", 1,"a", "This is title b", "XXXXXXXXXX XXXXXX XXXXX", "email2"));
    }

    public class SortPlaces implements Comparator<Event> {
        Event currentLoc;

        public SortPlaces(double latitude, double longitude){
            currentLoc = new Event("curLoc", latitude, longitude,
                    "2/2/2022", "a", 1,"a", "X", "X", "email");;
        }

        @Override
        public int compare(Event event1, Event event2) {
            double lat1 = event1.getLatitude();
            double lon1 = event1.getLongitude();
            double lat2 = event2.getLatitude();
            double lon2 = event2.getLongitude();
            double distanceToPlace1 = Utils.getDistance(currentLoc.getLatitude(), currentLoc.getLongitude(), lat1, lon1);
            event1.setDistance(distanceToPlace1);
            double distanceToPlace2 = Utils.getDistance(currentLoc.getLatitude(), currentLoc.getLongitude(), lat2, lon2);
            event2.setDistance(distanceToPlace2);
            return (int) (distanceToPlace1 - distanceToPlace2);
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        updateView();
    }

    private void updateView() {
        bottomNavigationView = findViewById(R.id.bottom_nav);
        bottomNavigationView.setSelectedItemId(R.id.menu_search);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.menu_home:
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.menu_search:

                        return true;
                    case R.id.menu_chat:
                        Intent tempIntent  = new Intent(getBaseContext(), MapsActivity.class);
                        tempIntent.putExtra("lati", currentLatitude);
                        tempIntent.putExtra("long", currentLongitude);
//                            startActivity(intent);
                        startActivity(tempIntent);
                        overridePendingTransition(0, 0);
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


    private void createRecyclerView(){
//        for (Event event : eventList){
//            Log.d("event test", event.getTitle());
//        }
//        Collections.sort(eventList, new SortPlaces(currentLatitude, currentLongitude));
//        for (Event event : eventList){
//            Log.d("event sorted!!!!!!!!!!!!!", String.valueOf(event.getDistance()));
//        }

        rLayoutManger = new LinearLayoutManager(this);
        recyclerView = findViewById(R.id.recyclerView_event_list);
        recyclerView.setHasFixedSize(true);
        eventAdapter = new EventAdapter(eventList, this, currentLatitude, currentLongitude);
        recyclerView.setAdapter(eventAdapter);
        recyclerView.setLayoutManager(rLayoutManger);

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
                eventAdapter.getFilter().filter(newText);
                return false;
            }
        });
        return true;
    }
}