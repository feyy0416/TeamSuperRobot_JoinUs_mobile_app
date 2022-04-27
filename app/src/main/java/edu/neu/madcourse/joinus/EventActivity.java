package edu.neu.madcourse.joinus;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class EventActivity extends AppCompatActivity {

    private List<Event> eventList = new ArrayList<>();
    private EventAdapter eventAdapter;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager rLayoutManger;

    private static final String tableName = "Events";
//    private DatabaseReference mDatabase;
    private String loginUsername = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_card);
    }
}