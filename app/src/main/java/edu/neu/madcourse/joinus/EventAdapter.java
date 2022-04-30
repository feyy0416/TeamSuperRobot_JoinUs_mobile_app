package edu.neu.madcourse.joinus;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filterable;
import android.widget.Filter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class EventAdapter extends RecyclerView.Adapter<EventHolder> implements Filterable {

    private List<Event> eventList = new ArrayList<>();
    private List<Event> eventListFull ;
    private Context mContext;

    private double currentLatitude;
    private double currentLongitude;

    private static final double r2d = 180.0D / 3.141592653589793D;
    private static final double d2r = 3.141592653589793D / 180.0D;
    private static final double d2km = 111189.57696D * r2d;

    public EventAdapter(List<Event> eventList, Context mContext, double currentLatitude, double currentLongitude) {
        this.eventList = eventList;
        eventListFull = new ArrayList<>(eventList);
        this.mContext = mContext;
        this.currentLatitude = currentLatitude;
        this.currentLongitude = currentLongitude;

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

        double distance = distance(currentEvent.getLatitude(), currentLatitude, currentEvent.getLongitude(), currentLongitude);
        double distanceInKm = Math.round((distance / 1000) * 100.0) / 100.0;
        currentEvent.setDistance(distanceInKm);
        Log.d("1111111111111111112",Double.toString(currentEvent.getDistance()));
        holder.distance.setText(Double.toString(distanceInKm) + " km");
        if ("Cooking".equals(currentEvent.getCategory())) {
            holder.image.setImageResource(R.drawable.img1);
        }
        if ("Study".equals(currentEvent.getCategory())) {
            holder.image.setImageResource(R.drawable.img2);
        }
        if ("Sport".equals(currentEvent.getCategory())) {
            holder.image.setImageResource(R.drawable.img3);
        }
        if ("Other".equals(currentEvent.getCategory())) {
            holder.image.setImageResource(R.drawable.img4);
        }
    }

    @Override
    public int getItemCount() {
        return eventList.size();
    }

    private double distance(double lt1, double lt2, double ln1, double ln2) {
        double x = lt1 * d2r;
        double y = lt2 * d2r;
        return Math.acos( Math.sin(x) * Math.sin(y) + Math.cos(x) * Math.cos(y) * Math.cos(d2r * (ln1 - ln2))) * d2km;
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
                filteredList.addAll(eventListFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (Event event : eventListFull) {
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
            eventList.clear();
            eventList.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };
}
