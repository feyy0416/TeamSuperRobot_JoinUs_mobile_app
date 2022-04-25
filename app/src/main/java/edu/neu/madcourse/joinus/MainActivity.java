package edu.neu.madcourse.joinus;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MainActivity extends AppCompatActivity {
    private String cityName;
    private RecyclerView hotEvents;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        hotEvents = findViewById(R.id.rcv_hot_events);
        hotEventsRecycler();
    }

    private void hotEventsRecycler() {
        hotEvents.setHasFixedSize(true);
        hotEvents.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
    }
}
