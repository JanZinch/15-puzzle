package com.ivan.puzzle;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.view.MotionEventCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RecyclerListAdapter extends RecyclerView.Adapter<RecyclerListAdapter.TileViewHolder> implements TileTouchHelperAdapter {

    public final OnStartDragListener _startDragListener;


    private static final String[] _tiles = new String[]{
           " ", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15"
    };

    private final List<String> mItems = new ArrayList<>();

    public RecyclerListAdapter() {
        mItems.addAll(Arrays.asList(_tiles));

        _startDragListener = null;
    }

    public RecyclerListAdapter(OnStartDragListener startDragListener){

        mItems.addAll(Arrays.asList(_tiles));
        _startDragListener = startDragListener;
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

        holder.imageView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (MotionEventCompat.getActionMasked(event) == MotionEvent.ACTION_DOWN) {
                    _startDragListener.onStartDrag(holder);
                }
                return false;
            }
        });


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
        public final ImageView imageView;

        public TileViewHolder(View itemView) {
            super(itemView);
            //textView = (TextView) itemView;

            textView = (TextView) itemView.findViewById(R.id.number);
            imageView = (ImageView) itemView.findViewById(R.id.tile_texture);
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
