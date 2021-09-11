package com.ivan.puzzle;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RecyclerListAdapter extends RecyclerView.Adapter<RecyclerListAdapter.TileViewHolder> implements TileTouchHelperAdapter {


    private static final String[] STRINGS = new String[]{
            "One", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine", "Ten"
    };

    private final List<String> mItems = new ArrayList<>();

    public RecyclerListAdapter() {
        mItems.addAll(Arrays.asList(STRINGS));
    }

    @Override
    public TileViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.tile, parent, false);
        TileViewHolder itemViewHolder = new TileViewHolder(view);
        return itemViewHolder;
    }

    @Override
    public void onBindViewHolder(final TileViewHolder holder, int position) {
        holder.textView.setText(mItems.get(position));
    }

    @Override
    public void OnTileDismiss(int position) {
        mItems.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public void OnTileMove(int fromPosition, int toPosition) {
        String prev = mItems.remove(fromPosition);
        mItems.add(toPosition > fromPosition ? toPosition - 1 : toPosition, prev);
        notifyItemMoved(fromPosition, toPosition);
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }


    public static class TileViewHolder extends RecyclerView.ViewHolder implements
            TileTouchHelperViewHolder {

        public final TextView textView;

        public TileViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView;
        }

        @Override
        public void OnTileSelected() {
            itemView.setBackgroundColor(Color.LTGRAY);
        }

        @Override
        public void OnTileClear() {
            itemView.setBackgroundColor(0);
        }
    }

}
