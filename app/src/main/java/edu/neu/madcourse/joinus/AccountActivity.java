package edu.neu.madcourse.joinus;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Button;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.List;

public class AccountActivity extends AppCompatActivity {

    private List<Event> eventList;
    private EventAdapter eventAdapter;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager rLayoutManger;
    private Button btn_log_out;

    private static final String tableName = "Events";
    private DatabaseReference mDatabase;

    private double currentLatitude;
    private double currentLongitude;
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        eventList = new ArrayList<>();

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            currentLatitude = extras.getDouble("lati");
            currentLongitude = extras.getDouble("long");
//            Log.d("1111111111111111112",Double.toString(currentLatitude));
//            Log.d("1111111111111111112",Double.toString(currentLongitude));
            //The key argument here must match that used in the other activity

        }
        btn_log_out = findViewById(R.id.btn_sign_out);

        updateView();
    }

    @Override
    public void onResume() {
        super.onResume();
        updateView();
    }

    private void updateView() {
        bottomNavigationView = findViewById(R.id.bottom_nav);
        bottomNavigationView.setSelectedItemId(R.id.menu_account);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.menu_home:
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.menu_search:
                        Intent intent_search  = new Intent(getBaseContext(), EventActivity.class);
                        intent_search.putExtra("lati", currentLatitude);
                        intent_search.putExtra("long", currentLongitude);
                        startActivity(intent_search);
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.menu_chat:
                        Intent intent_maps  = new Intent(getBaseContext(), MapsActivity.class);
                        intent_maps.putExtra("lati", currentLatitude);
                        intent_maps.putExtra("long", currentLongitude);
                        startActivity(intent_maps);
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.menu_account:
                        return true;
                }
                return false;
            }
        });
    }
}