package edu.neu.madcourse.joinus;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import edu.neu.madcourse.joinus.auth.LoginActivity;


public class DetailsActivity extends AppCompatActivity {

    private TextView title;
    private TextView time;
    private TextView description;
    private TextView announcer;
    private ImageView image;
    private TextView latitude;
    private TextView longitude;
    private TextView email;
    private String eventId;
    ImageButton btn_back;
    BottomNavigationView bottomNavigationView;

    private static final String tableName = "Events";
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            eventId = extras.getString("eventId");
            Log.d("111111111111111122222",eventId);
            //The key argument here must match that used in the other activity
        }
        title = findViewById(R.id.details_title);
        time = findViewById(R.id.details_time);
        description = findViewById(R.id.details_description);
        image = findViewById(R.id.details_img);
        latitude = findViewById(R.id.details_latitude);
        longitude = findViewById(R.id.details_longitude);
        email = findViewById(R.id.details_email);
        announcer = findViewById(R.id.details_annoucer);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child(tableName).addValueEventListener(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        if (snapshot.hasChildren()) {
                            for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                Event e = dataSnapshot.getValue(Event.class);
                                if (eventId.equals(e.getEventId())){
                                    announcer.setText(e.getAnnouncer());
                                    title.setText(e.getTitle());
                                    Log.d("111111111111111122222","title set");
                                    time.setText(e.getTime());
                                    Log.d("111111111111111122222","time");
                                    description.setText(e.getDescription());
                                    Log.d("111111111111111122222","desc");
                                    latitude.setText(Double.toString(e.getLatitude()));
                                    Log.d("111111111111111122222","la");
                                    longitude.setText(Double.toString(e.getLongitude()));
                                    Log.d("111111111111111122222","llo");
                                    email.setText(e.getEmail());
                                    String category = e.getCategory();
                                    if ("Cooking".equals(category)) {
                                        image.setImageResource(R.drawable.img1);
                                    }
                                    if ("Study".equals(category)) {
                                        image.setImageResource(R.drawable.img2);
                                    }
                                    if ("Sport".equals(category)) {
                                        image.setImageResource(R.drawable.img3);
                                    }
                                    if ("Other".equals(category)) {
                                        image.setImageResource(R.drawable.img4);
                                    }
                                    Log.d("111111111111111122222","img");
                                    break;
                                }
                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });


        btn_back = findViewById(R.id.btn_back);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        bottomNavigationView = findViewById(R.id.bottom_direct);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.menu_direction:
                        getDirections();
                        item.setChecked(true);
                        return true;
                    case R.id.menu_share:
                        Toast.makeText(DetailsActivity.this, "Share, coming soon!",
                                Toast.LENGTH_SHORT).show();
                        item.setChecked(true);
                        return true;

                }
                return false;
            }
        });
//        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
//        DatabaseReference databaseReference = firebaseDatabase.getReference();
//        DatabaseReference getImage = databaseReference.child("Images");
//
//        getImage.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                if (snapshot.hasChildren()){
//                    for (DataSnapshot item: snapshot.getChildren()){
//                        String url = item.getValue(String.class);
//                        if (url!= null){
//                            Log.d("test", "item work");
//                        }
//                        String url2 = snapshot.getValue(String.class);
//                        if (url2!= null){
//                            Log.d("test", "snap work");
//                        }
//                        Picasso.get().load(url).into(img0);
//
//                    }
//
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                Toast.makeText(DetailsActivity.this, "Error Loading Image", Toast.LENGTH_SHORT).show();
//            }
//        });
    }

    private void getDirections() {
        Uri gmmIntentUri = Uri.parse("google.navigation:q=37.7749,-122.4194");
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        if (mapIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(mapIntent);
        }
    }
}