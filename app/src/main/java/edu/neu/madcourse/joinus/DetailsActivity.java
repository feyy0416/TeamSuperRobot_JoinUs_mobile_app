package edu.neu.madcourse.joinus;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        img0 = findViewById(R.id.details_img0);
        img1 = findViewById(R.id.details_img1);
        img2 = findViewById(R.id.details_img2);

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference();
        DatabaseReference getImage = databaseReference.child("Images");

        getImage.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.hasChildren()){
                    for (DataSnapshot item: snapshot.getChildren()){
                        String url = item.getValue(String.class);
                        Picasso.get().load(url).into(img0);

                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(DetailsActivity.this, "Error Loading Image", Toast.LENGTH_SHORT).show();
            }
        });
    }
}