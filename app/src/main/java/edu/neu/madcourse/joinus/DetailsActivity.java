package edu.neu.madcourse.joinus;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class DetailsActivity extends AppCompatActivity {

    ImageView img0;
    ImageView img1;
    ImageView img2;
    ImageButton btn_back;
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        img0 = findViewById(R.id.details_img0);
        img1 = findViewById(R.id.details_img1);
        img2 = findViewById(R.id.details_img2);
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
                        item.setChecked(false);
                        return true;
                    case R.id.menu_share:
                        Log.d("share", "share selected");
                        item.setChecked(false);
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
        Uri gmmIntentUri = Uri.parse("geo:37.7749,-122.4194");
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        if (mapIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(mapIntent);
        }
    }
}