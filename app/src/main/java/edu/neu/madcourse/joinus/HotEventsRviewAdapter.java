package edu.neu.madcourse.joinus;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class HotEventsRviewAdapter extends RecyclerView.Adapter<HotEventsRviewHolder> {
    private final ArrayList<Event> evenList;

    public HotEventsRviewAdapter(ArrayList<Event> evenList) {
        this.evenList = evenList;
    }

    @NonNull
    @Override
    public HotEventsRviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull HotEventsRviewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
