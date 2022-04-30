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
import android.view.inputmethod.EditorInfo;
import android.widget.SearchView;

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

import edu.neu.madcourse.joinus.auth.User;

public class EventActivity extends AppCompatActivity {

    private List<Event> eventList;
    private EventAdapter eventAdapter;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager rLayoutManger;

    private static final String tableName = "Events";
    private DatabaseReference mDatabase;

    private double currentLatitude;
    private double currentLongitude;

    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_list);
        bottomNavigationView = findViewById(R.id.bottom_nav);
        updateView();
        eventList = new ArrayList<>();

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            currentLatitude = extras.getDouble("lati");
            currentLongitude = extras.getDouble("long");
            Log.d("1111111111111111112",Double.toString(currentLatitude));
            Log.d("1111111111111111112",Double.toString(currentLongitude));
            //The key argument here must match that used in the other activity
        }

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
                        eventList.sort(Comparator.comparing(o -> o.getDistance()));
                        createRecyclerView();
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });


//        eventList.add(new Event("a", 0, 0, "2/2/2022", "a", 1,"a", "This is title a", "XXXXXXXXXXXXXXXXXXXXX", "email"));
//        eventList.add(new Event("b", 0, 0, "3/3/2022", "a", 1,"a", "This is title b", "XXXXXXXXXX XXXXXX XXXXX", "email2"));
    }

    @Override
    public void onResume() {
        super.onResume();
        updateView();
    }

    private void updateView() {
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
                        startActivity(new Intent(getApplicationContext(), MapsActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                }
                return false;
            }
        });
    }


    private void createRecyclerView(){
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