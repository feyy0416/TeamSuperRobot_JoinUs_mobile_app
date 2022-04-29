package edu.neu.madcourse.joinus;

import androidx.appcompat.app.AppCompatActivity;


import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import edu.neu.madcourse.joinus.auth.User;

public class AddEventActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private Spinner spinner;
    private String category;
    private DatabaseReference mDatabase;
    private static final String TABLE_NAME = "Events";

    private EditText eTitle;
    private EditText eTime;
    private EditText eDescription;
    private EditText eEmail;
    private EditText eLatitude;
    private EditText eLongitude;

    private String username;

    private double currentLatitude;
    private double currentLongitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);
        spinner = findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.category, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        eTitle = findViewById(R.id.edited_title);
        eTime = findViewById(R.id.edited_time);
        eDescription = findViewById(R.id.edited_description);
        eEmail = findViewById(R.id.edited_email);
        eLatitude = findViewById(R.id.edited_latitude);
        eLongitude = findViewById(R.id.edited_longitude);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            currentLatitude = extras.getDouble("lati");
            currentLongitude = extras.getDouble("long");
            Log.d("1111111111111111111",Double.toString(currentLatitude));
            Log.d("1111111111111111111",Double.toString(currentLongitude));
            //The key argument here must match that used in the other activity
        }

        mDatabase = FirebaseDatabase.getInstance().getReference();
        initView();


    }

    private void initView(){
        Button btn = findViewById(R.id.btn_post);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = eTitle.getText().toString();
                String time = eTime.getText().toString();
                DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                formatter.setLenient(false);
                try {
                    Date date= formatter.parse(time);
                    String description = eDescription.getText().toString();
                    String email = eEmail.getText().toString();
                    double latitude = Double.parseDouble(eLatitude.getText().toString());
                    double longitude = Double.parseDouble(eLongitude.getText().toString());
                    if (title == null || time == null || description == null || email == null
                            || "".equals(title) || "".equals(time) || "".equals(description) || "".equals(email)
                            || "".equals(eLatitude.getText().toString()) || "".equals(eLongitude.getText().toString())){
                        Toast.makeText(getApplicationContext(), "Please fill out all the fields", Toast.LENGTH_SHORT).show();
                    } else {
                        postEvent(title, description, time, email, latitude, longitude);
                        Toast.makeText(getApplicationContext(), "Post successfully", Toast.LENGTH_SHORT).show();
                        refresh();
                    }
                } catch (ParseException e) {
                    Toast.makeText(getApplicationContext(), "Time format is invalid", Toast.LENGTH_SHORT).show();
                    refresh();
                }
            }
        });
    }

    private void refresh() {
        eTitle.setText("");
        eTime.setText("");
        eDescription.setText("");
        eEmail.setText("");
        eLatitude.setText("");
        eLongitude.setText("");
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String choice = adapterView.getItemAtPosition(i).toString();
        category = choice;
//        Toast.makeText(getApplicationContext(), choice + " picked successfully", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    private void postEvent(String title, String description, String time, String email, double latitude, double longitude){
        int imageId = 0;
        if ("Cooking".equals(category)){
            imageId = 1;
        }
        if ("Study".equals(category)){
            imageId = 2;
        }
        if ("Sport".equals(category)){
            imageId = 3;
        }
        if ("Other".equals(category)){
            imageId = 4;
        }
        String eventId = UUID.randomUUID().toString();
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
//        String userId = "5eDjtAGoVGVhLR5eVe0W7ijgKdU2";
        Log.d("1111111111111111111",userId);
        mDatabase.child("users").addValueEventListener(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        if (snapshot.hasChildren()) {
                            for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                User user = dataSnapshot.getValue(User.class);
                                if (userId.equals(user.getUid())){
                                    username = user.getUsername();
                                    Log.d("1111111111111111111",username);
                                }
                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });


        Event event = new Event(eventId, latitude, longitude, time, username, imageId, category, title, description, email);
        mDatabase.child(TABLE_NAME).child(eventId).setValue(event);

    }
}