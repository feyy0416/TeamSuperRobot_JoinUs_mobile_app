package edu.neu.madcourse.joinus;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import edu.neu.madcourse.joinus.auth.LoginActivity;

public class HotEventsAdapter extends RecyclerView.Adapter<HotEventsAdapter.HotEventsHolder> {
    private List<Event> evenList;

    public HotEventsAdapter(List<Event> evenList) {
        this.evenList = evenList;
    }

    @NonNull
    @Override
    public HotEventsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.hot_events_card, parent , false);
        return new HotEventsHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HotEventsHolder holder, int position) {
        if ("Cooking".equals(evenList.get(position).getCategory())) {
            holder.hotEventImage.setImageResource(R.drawable.img1);
        }
        if ("Study".equals(evenList.get(position).getCategory())) {
            holder.hotEventImage.setImageResource(R.drawable.img2);
        }
        if ("Sport".equals(evenList.get(position).getCategory())) {
            holder.hotEventImage.setImageResource(R.drawable.img3);
        }
        if ("Other".equals(evenList.get(position).getCategory())) {
            holder.hotEventImage.setImageResource(R.drawable.img4);
        }
        holder.hotEventTitle.setText(evenList.get(position).getTitle());
        holder.hotEventDescription.setText(evenList.get(position).getDescription());
        Event currentEvent = evenList.get(position);

        holder.hot_event_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context = holder.hot_event_card.getContext();
                Intent intent = new Intent(context, DetailsActivity.class);
                intent.putExtra("eventId", currentEvent.getEventId());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return evenList.size();
    }

    public class HotEventsHolder extends RecyclerView.ViewHolder {


        public ImageView hotEventImage;
        public TextView hotEventTitle;
        public TextView hotEventDescription;
        public CardView hot_event_card;

        public HotEventsHolder(@NonNull View itemView) {
            super(itemView);

            hotEventImage = itemView.findViewById(R.id.hot_event_image);
            hotEventTitle = itemView.findViewById(R.id.hot_event_title);
            hotEventDescription = itemView.findViewById(R.id.hot_event_description);
            hot_event_card = itemView.findViewById(R.id.hot_event_card);
        }
    }
}
