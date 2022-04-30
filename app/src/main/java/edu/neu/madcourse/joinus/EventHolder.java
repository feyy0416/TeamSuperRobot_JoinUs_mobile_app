package edu.neu.madcourse.joinus;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class EventHolder extends RecyclerView.ViewHolder {

    public TextView title;
    public TextView time;
    public TextView description;
    public ImageView image;
    public TextView distance;
    public CardView event_card;

    public EventHolder(View eventView){
        super(eventView);
        title = eventView.findViewById(R.id.event_title);
        time = eventView.findViewById(R.id.event_time);
//        description = eventView.findViewById(R.id.event_description);
        image = eventView.findViewById(R.id.event_img);
        distance = eventView.findViewById(R.id.event_location);
        event_card = eventView.findViewById(R.id.event_card);
    }
}
