package edu.neu.madcourse.joinus;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import edu.neu.madcourse.joinus.auth.User;

public class AccountActivity extends AppCompatActivity {

    private List<Event> eventList;
    private AccountAdapter  accAdapter;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager rLayoutManger;
    private Button btn_log_out;

    private static final String tableName = "Events";
    private DatabaseReference mDatabase;

    private double currentLatitude;
    private double currentLongitude;
    private BottomNavigationView bottomNavigationView;
    private String username;

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

        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("users").child(userId).child("username").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                }
                else {
                    Log.d("firebase", String.valueOf(task.getResult().getValue()));
                    username = String.valueOf(task.getResult().getValue());
                    TextView tv = findViewById(R.id.tv_user_name);
                    tv.setText(username);

                }
            }
        });

        mDatabase.child("users").child(userId).child("email").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                }
                else {
                    Log.d("firebase", String.valueOf(task.getResult().getValue()));
                    String email = String.valueOf(task.getResult().getValue());
                    TextView tv2 = findViewById(R.id.tv_user_email);
                    tv2.setText(email);

                }
            }
        });

        mDatabase.child("Events").addValueEventListener(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        if (snapshot.hasChildren()) {
                            for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                Event e = dataSnapshot.getValue(Event.class);
                                if (e.getAnnouncer().equals(username)) {
                                    Log.d("1111111115454511112","added");
                                    eventList.add(e);

                                }
                            }
                        }
                        createRecyclerView();
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });

        updateView();
    }


    @Override
    public void onResume() {
        super.onResume();
        updateView();
    }

    private void createRecyclerView(){
        rLayoutManger = new LinearLayoutManager(this);
        recyclerView = findViewById(R.id.rcv_acc_event_list);
        recyclerView.setHasFixedSize(true);
        recyclerView.setNestedScrollingEnabled(true);
        accAdapter = new AccountAdapter(eventList, this);
        recyclerView.setAdapter(accAdapter);
        recyclerView.setLayoutManager(rLayoutManger);

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