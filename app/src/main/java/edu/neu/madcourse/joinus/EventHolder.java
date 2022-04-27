package edu.neu.madcourse.joinus;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class EventHolder extends RecyclerView.ViewHolder {

    public TextView title;
    public TextView time;
    public TextView category;

    public EventHolder(View eventView){
        super(eventView);
        title = eventView.findViewById(R.id.event_title);
        time = eventView.findViewById(R.id.event_time);
        category = eventView.findViewById(R.id.event_description);
    }
}
