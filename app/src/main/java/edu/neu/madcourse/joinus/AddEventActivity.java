package edu.neu.madcourse.joinus;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddEventActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private Spinner spinner;
    private String category;
    private DatabaseReference mDatabase;
    private static final String TABLE_NAME = "Messages";

    private EditText eTitle;
    private EditText eTime;
    private EditText eDescription;
    private EditText eEmail;
    private EditText eLatitude;
    private EditText eLongitude;

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
                String description = eDescription.getText().toString();
                String email = eEmail.getText().toString();
                double latitude = Double.parseDouble(eLatitude.getText().toString());
                double longitude = Double.parseDouble(eLongitude.getText().toString());
                postEvent(title, description, time, email, latitude, longitude);
            }
        });
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

    }
}