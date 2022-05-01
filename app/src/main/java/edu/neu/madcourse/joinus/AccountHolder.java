package edu.neu.madcourse.joinus;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class AccountHolder extends RecyclerView.ViewHolder{

    public TextView title;
    public TextView time;
    public TextView description;
    public ImageView image;
    public CardView event_card;

    public AccountHolder(View eventView){
        super(eventView);
        title = eventView.findViewById(R.id.event_title_b);
        time = eventView.findViewById(R.id.event_time_b);
        description = eventView.findViewById(R.id.map_event_description_b);
        image = eventView.findViewById(R.id.event_img_b);
        event_card = eventView.findViewById(R.id.event_card_b);
    }
}
