package edu.neu.madcourse.joinus;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

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
        holder.hotEventImage.setImageResource(R.drawable.icon);
        holder.hotEventTitle.setText(evenList.get(position).getTitle());
        holder.hotEventDescription.setText(evenList.get(position).getDescription());
    }

    @Override
    public int getItemCount() {
        return evenList.size();
    }

    public class HotEventsHolder extends RecyclerView.ViewHolder{

        public ImageView hotEventImage;
        public TextView hotEventTitle;
        public TextView hotEventDescription;

        public HotEventsHolder(@NonNull View itemView) {
            super(itemView);

            hotEventImage = itemView.findViewById(R.id.hot_event_image);
            hotEventTitle = itemView.findViewById(R.id.hot_event_title);
            hotEventDescription = itemView.findViewById(R.id.hot_event_description);
        }
    }
}
