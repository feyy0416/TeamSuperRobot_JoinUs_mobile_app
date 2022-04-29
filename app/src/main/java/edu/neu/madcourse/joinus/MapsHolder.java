package edu.neu.madcourse.joinus;

import static androidx.core.content.ContextCompat.startActivity;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import edu.neu.madcourse.joinus.auth.LoginActivity;
import edu.neu.madcourse.joinus.auth.SignupActivity;

public class MapsHolder extends RecyclerView.ViewHolder {
    public TextView title;
    public TextView description;
    public ImageView image;
    public TextView distance;
    public CardView map_event_card;

    public MapsHolder(@NonNull View itemView) {
        super(itemView);
        title = itemView.findViewById(R.id.map_event_title);
        description = itemView.findViewById(R.id.map_event_description);
        image = itemView.findViewById(R.id.map_event_image);
        distance = itemView.findViewById(R.id.map_distance);
        map_event_card = itemView.findViewById(R.id.map_event_card);

    }
}
