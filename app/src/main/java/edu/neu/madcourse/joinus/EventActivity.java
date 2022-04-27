package edu.neu.madcourse.joinus;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class EventActivity extends AppCompatActivity {

    private List<Event> eventList;
    private EventAdapter eventAdapter;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager rLayoutManger;

    private static final String tableName = "Events";
//    private DatabaseReference mDatabase;
    private String loginUsername = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_list);
        eventList = new ArrayList<>();
        eventList.add(new Event("a", 0, 0, 0, "a", "a", "a", "This is title a", "XXXXXXXXXXXXXXXXXXXXX"));
        eventList.add(new Event("b", 0, 0, 0, "a", "a", "a", "This is title b", "XXXXXXXXXX XXXXXX XXXXX"));
        createRecyclerView();
    }

    private void createRecyclerView(){
        rLayoutManger = new LinearLayoutManager(this);
        recyclerView = findViewById(R.id.recyclerView_event_list);
        recyclerView.setHasFixedSize(true);
        eventAdapter = new EventAdapter(eventList, this);
        recyclerView.setAdapter(eventAdapter);
        recyclerView.setLayoutManager(rLayoutManger);

    }
}