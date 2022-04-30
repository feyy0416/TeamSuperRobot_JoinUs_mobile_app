package edu.neu.madcourse.joinus;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class AccountAdapter extends RecyclerView.Adapter<AccountHolder>{

    private List<Event> eventList = new ArrayList<>();
    private Context mContext;


    public AccountAdapter(List<Event> eventList, Context mContext) {
        this.eventList = eventList;
        this.mContext = mContext;

    }
    @NonNull
    @Override
    public AccountHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_event_card, parent, false);
        return new AccountHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AccountHolder holder, int position) {

        mContext = mContext.getApplicationContext();
        Event currentEvent = eventList.get(position);
        holder.time.setText(currentEvent.getTime());
        holder.title.setText(currentEvent.getTitle());
        holder.description.setText(currentEvent.getDescription());
        holder.distance.setText("");
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

        holder.event_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context = holder.event_card.getContext();
                Intent intent = new Intent(context, DetailsActivity.class);
                intent.putExtra("eventId", currentEvent.getEventId());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return eventList.size();
    }
}
