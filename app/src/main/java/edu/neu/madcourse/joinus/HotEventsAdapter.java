package edu.neu.madcourse.joinus;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class HotEventsAdapter extends RecyclerView.Adapter<HotEventsAdapter.HotEventsHolder> {
    private final ArrayList<Event> evenList;

    public HotEventsAdapter(ArrayList<Event> evenList) {
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
        holder.hotEventImage.setImageResource(evenList.get(position).getImageId());
        holder.hotEventTitle.setText(evenList.get(position).getTitle());
        holder.hotEventDescription.setText(evenList.get(position).getDescription());
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class HotEventsHolder extends RecyclerView.ViewHolder{

        private ImageView hotEventImage;
        private TextView hotEventTitle;
        private TextView hotEventDescription;

        public HotEventsHolder(@NonNull View itemView) {
            super(itemView);

            hotEventImage = itemView.findViewById(R.id.hot_event_image);
            hotEventTitle = itemView.findViewById(R.id.hot_event_title);
            hotEventDescription = itemView.findViewById(R.id.hot_event_description);
        }
    }
}
