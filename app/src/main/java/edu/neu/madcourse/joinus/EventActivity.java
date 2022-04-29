package edu.neu.madcourse.joinus;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.inputmethod.EditorInfo;
import android.widget.SearchView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import edu.neu.madcourse.joinus.auth.User;

public class EventActivity extends AppCompatActivity {

    private List<Event> eventList;
    private EventAdapter eventAdapter;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager rLayoutManger;

    private static final String tableName = "Events";
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_list);
        eventList = new ArrayList<>();

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
                        createRecyclerView();
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });


//        eventList.add(new Event("a", 0, 0, "2/2/2022", "a", 1,"a", "This is title a", "XXXXXXXXXXXXXXXXXXXXX", "email"));
//        eventList.add(new Event("b", 0, 0, "3/3/2022", "a", 1,"a", "This is title b", "XXXXXXXXXX XXXXXX XXXXX", "email2"));
    }

    private void createRecyclerView(){
        rLayoutManger = new LinearLayoutManager(this);
        recyclerView = findViewById(R.id.recyclerView_event_list);
        recyclerView.setHasFixedSize(true);
        eventAdapter = new EventAdapter(eventList, this);
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