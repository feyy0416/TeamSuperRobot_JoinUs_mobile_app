package edu.neu.madcourse.joinus;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class EventAdapter extends RecyclerView.Adapter<EventHolder>{

    private List<Event> eventList;
    private Context mContext;

    public EventAdapter(List<Event> eventList, Context mContext) {
        this.eventList = eventList;
        this.mContext = mContext;
    }


    @NonNull
    @Override
    public EventHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_event_card, parent, false);
        return new EventHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EventHolder holder, int position) {
        mContext = mContext.getApplicationContext();
        Event currentEvent = eventList.get(position);
        SimpleDateFormat sf = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss");
        Date date = new Date(Long.parseLong(Long.toString(currentEvent.getTime())));
        holder.time.setText(sf.format(date));
        holder.title.setText(currentEvent.getTitle());
        holder.description.setText(currentEvent.getDescription());
        holder.distance.setText("1km");
    }

    @Override
    public int getItemCount() {
        return eventList.size();
    }
}
