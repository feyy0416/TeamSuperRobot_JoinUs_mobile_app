package edu.neu.madcourse.joinus;

import static androidx.core.content.ContextCompat.startActivity;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import edu.neu.madcourse.joinus.auth.LoginActivity;

public class MapsAdapter extends RecyclerView.Adapter<MapsHolder> implements Filterable {
    private List<Event> list;
    private List<Event> listFull;

    public MapsAdapter(List<Event> list){
        this.list = list;
        listFull = new ArrayList<>(list);
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
                intent.putExtra("eventId", currentEvent.getEventId());
                context.startActivity(intent);

            }
        });

    }

    @Override
    public Filter getFilter() {
        return filter;
    }

    private Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Event> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(listFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (Event event : listFull) {
                    if (event.getTitle().toLowerCase().contains(filterPattern)) {
                        filteredList.add(event);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredList;

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            list.clear();
            list.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };

    @Override
    public int getItemCount() {
        return list.size();
    }
}
