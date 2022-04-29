package edu.neu.madcourse.joinus;

import static androidx.core.content.ContextCompat.startActivity;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import edu.neu.madcourse.joinus.auth.LoginActivity;

public class MapsAdapter extends RecyclerView.Adapter<MapsHolder>{
    private List<Event> list;
    public MapsAdapter(List<Event> eventList){
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
        Event currentEvent = list.get(position);

        holder.title.setText(currentEvent.getTitle());
        holder.description.setText(currentEvent.getDescription());
        holder.distance.setText("2.5 miles");
        holder.image.setImageResource(R.drawable.icon);
        holder.map_event_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Context context = holder.map_event_card.getContext();
                Intent intent = new Intent(context, DetailsActivity.class);
                context.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
