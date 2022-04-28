package edu.neu.madcourse.joinus;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class EventAdapter extends RecyclerView.Adapter<EventHolder>{

    private List<Event> eventList = new ArrayList<>();
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
//        SimpleDateFormat sf = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss");
//        Date date = new Date(Long.parseLong(Long.toString(currentEvent.getTime())));
        holder.time.setText(currentEvent.getTime());
        holder.title.setText(currentEvent.getTitle());
        holder.description.setText(currentEvent.getDescription());
        holder.distance.setText("1km");
        holder.image.setImageResource(R.drawable.icon);
    }

    @Override
    public int getItemCount() {
        return eventList.size();
    }

    private double distance(double lat1, double lat2, double lon1, double lon2, double el1, double el2) {
        final int R = 6371;
        double a = Math.sin(Math.toRadians(lat2 - lat1) / 2) * Math.sin(Math.toRadians(lat2 - lat1) / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(Math.toRadians(lon2 - lon1) / 2) * Math.sin(Math.toRadians(lon2 - lon1) / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = R * c * 1000; // convert to meters
        double height = el1 - el2;
        distance = Math.pow(distance, 2) + Math.pow(height, 2);
        return Math.sqrt(distance);
    }
}
