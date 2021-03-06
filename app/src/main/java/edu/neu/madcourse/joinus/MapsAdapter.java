package edu.neu.madcourse.joinus;

import static androidx.core.content.ContextCompat.startActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
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
import edu.neu.madcourse.joinus.util.Utils;

public class MapsAdapter extends RecyclerView.Adapter<MapsHolder> implements Filterable {
    private List<Event> list;
    private List<Event> listFull;
    private static final double r2d = 180.0D / 3.141592653589793D;
    private static final double d2r = 3.141592653589793D / 180.0D;
    private static final double d2km = 111189.57696D * r2d;
    private double currentLongitude;
    private double currentLatitude;
    private Context mContext;

    public MapsAdapter(List<Event> list, Context mContext, double currentLatitude, double currentLongitude){
        this.list = list;
        listFull = new ArrayList<>(list);
        this.mContext = mContext;
        this.currentLatitude = currentLatitude;
        this.currentLongitude = currentLongitude;
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

        double distance = Utils.getDistance(currentLatitude, currentLongitude,
                currentEvent.getLatitude(), currentEvent.getLongitude());
        double distanceInKm = Math.round((distance ) * 100.0) / 100.0;
        currentEvent.setDistance(distanceInKm);

//        Log.d("1111111111111111112",Double.toString(currentEvent.getDistance()));
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
//
//        holder.map_event_card.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                Context context = holder.map_event_card.getContext();
//                Intent intent = new Intent(context, DetailsActivity.class);
//                intent.putExtra("eventId", currentEvent.getEventId());
//                context.startActivity(intent);
//
//            }
//        });

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
