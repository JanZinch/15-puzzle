package com.ivan.puzzle;

import android.graphics.Color;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.core.view.MotionEventCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class RecyclerListAdapter extends RecyclerView.Adapter<RecyclerListAdapter.TileViewHolder> implements TileTouchHelperAdapter {

    public final OnStartDragListener _startDragListener;


    private static final String EMPTY_TILE = " ";

    private static final String[] _tilesResult = new String[]{
           EMPTY_TILE, "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15"
    };

    private static final List<String[]> _tilesSource = new ArrayList<String[]>();


    private String[] LoadLevel(){

        _tilesSource.add(new String[] {"3", "4", "7", "2",
                "9", "1", "6", "15",
                "5", "14", "10", "12",
                "13", "11", "8", EMPTY_TILE});

        _tilesSource.add(new String[] {"6", "1", "3", "15",
                "2", "13", "12", "8",
                "5", "10", "4", "11",
                "14", "9", "7", EMPTY_TILE});

        _tilesSource.add(new String[] {"4", "15", "1", "12",
                "3", "6", "13", "9",
                "5", "10", "7", "8",
                "2", "14", "11", EMPTY_TILE});


        int levelIndex = (int) (Math.random() * _tilesSource.size());
        return _tilesSource.get(levelIndex);
    }

    private final List<String> _tilesList = new ArrayList<>();

    /*public RecyclerListAdapter() {
        mItems.addAll(Arrays.asList(_tiles));

        _startDragListener = null;
    }*/

    public RecyclerListAdapter(OnStartDragListener startDragListener){

        _tilesList.addAll(Arrays.asList(LoadLevel()));
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

        holder.textView.setText(_tilesList.get(position));

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
        _tilesList.remove(position);
        notifyItemRemoved(position);
    }


    private boolean isEmptyTile(int position){

        if (position < 0 || position >= _tilesList.size()){

            return false;
        }
        else return _tilesList.get(position).equals(EMPTY_TILE);
    }

    @Override
    public int GetPotentialMovingDirection(int position) {

        if(isEmptyTile(position - 1)) return ItemTouchHelper.LEFT;
        else if (isEmptyTile(position + 1)) return ItemTouchHelper.RIGHT;
        else if (isEmptyTile(position - 4)) return ItemTouchHelper.UP;
        else if (isEmptyTile(position + 4)) return ItemTouchHelper.DOWN;
        else return 0;
    }

    @Override
    public void OnTileMove(int fromPosition, int toPosition) {

        //String prev = _tilesList.remove(fromPosition);
        //_tilesList.add(toPosition > fromPosition ? toPosition - 1 : toPosition, prev);
        //notifyItemMoved(fromPosition, toPosition);

        if(fromPosition == toPosition + 1 || fromPosition == toPosition - 1 || fromPosition == toPosition + 4 || fromPosition == toPosition - 4){

            String tmp = _tilesList.get(fromPosition);
            _tilesList.set(fromPosition, _tilesList.get(toPosition));
            _tilesList.set(toPosition, tmp);
            notifyDataSetChanged();
        }

        //notifyItemMoved(fromPosition, toPosition);
    }

    @Override
    public int getItemCount() {
        return _tilesList.size();
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

        @RequiresApi(api = Build.VERSION_CODES.P)
        @Override
        public void OnTileSelected() {

            //itemView.setBackgroundColor(Color.LTGRAY);
            itemView.setOutlineSpotShadowColor(Color.GREEN);

        }

        @RequiresApi(api = Build.VERSION_CODES.P)
        @Override
        public void OnTileClear() {

            //itemView.setBackgroundColor(0);
            itemView.setOutlineSpotShadowColor(Color.CYAN);
        }
    }

}
