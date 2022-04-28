package edu.neu.madcourse.joinus;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class MapsAdapter extends RecyclerView.Adapter<MapsHolder>{
    private Context mContext;
    private List<Event> list;
    public MapsAdapter(Context mContext, List<Event> eventList){
        this.mContext = mContext;
        this.list = eventList;
    }
    @NonNull
    @Override
    public MapsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.map_events_card, parent, false);
        return new MapsHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MapsHolder holder, int position) {
        mContext = mContext.getApplicationContext();
        Event currentEvent = list.get(position);
        holder.title.setText(currentEvent.getTitle());
        holder.description.setText(currentEvent.getDescription());
        holder.distance.setText("2.5 miles");
        holder.image.setImageResource(R.drawable.icon);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
