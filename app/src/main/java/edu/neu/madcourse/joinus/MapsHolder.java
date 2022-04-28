package edu.neu.madcourse.joinus;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MapsHolder extends RecyclerView.ViewHolder {
    public TextView title;
    public TextView description;
    public ImageView image;
    public TextView distance;

    public MapsHolder(@NonNull View itemView) {
        super(itemView);
        title = itemView.findViewById(R.id.map_event_title);
        description = itemView.findViewById(R.id.map_event_description);
        image = itemView.findViewById(R.id.map_event_image);
        distance = itemView.findViewById(R.id.map_distance);
    }
}
