package edu.neu.madcourse.joinus;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class HotEventsRviewHolder extends RecyclerView.ViewHolder{
    public ImageView hotEventImage;
    public TextView hotEventTitle;
    public TextView hotEventDescription;

    public HotEventsRviewHolder(@NonNull View itemView) {
        super(itemView);
        hotEventImage = itemView.findViewById(R.id.hot_event_image);
        hotEventTitle = itemView.findViewById(R.id.hot_event_title);
        hotEventDescription = itemView.findViewById(R.id.hot_event_description);

        /*itemView.setOnClickListener(v -> {
            int position = getLayoutPosition();
            if (position != RecyclerView.NO_POSITION) {
                linkClickListener.onLinkClick(position);
            }
        });*/
    }
}
