package com.ivan.puzzle;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

public class TileTouchHelperCallback extends ItemTouchHelper.Callback {


    private final TileTouchHelperAdapter _adapter;

    public TileTouchHelperCallback(TileTouchHelperAdapter adapter){

        _adapter = adapter;
    }

    @Override
    public boolean isLongPressDragEnabled() {
        return true;
    }

    @Override
    public boolean isItemViewSwipeEnabled() {
        return true;
    }




    @Override
    public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {

        try{

            /*if (_adapter.isEmptyTileNeighbour(viewHolder.getAdapterPosition())){

                dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
            }*/

            int dragFlags = _adapter.GetPotentialMovingDirection(viewHolder.getAdapterPosition());
            final int swipeFlags = 0;

            return makeMovementFlags(dragFlags, swipeFlags);
        }
        catch (Exception ex){

            Debug.Log(ex.getMessage());
        }

        return  0;


    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder source, RecyclerView.ViewHolder target) {

        _adapter.OnTileMove(source.getAdapterPosition(), target.getAdapterPosition());
        return true;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int i) {
        _adapter.OnTileDismiss(viewHolder.getAdapterPosition());
    }

    @Override
    public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
        if (actionState != ItemTouchHelper.ACTION_STATE_IDLE) {
            TileTouchHelperViewHolder itemViewHolder = (TileTouchHelperViewHolder) viewHolder;
            itemViewHolder.OnTileSelected();
        }

        super.onSelectedChanged(viewHolder, actionState);
    }

    @Override
    public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        super.clearView(recyclerView, viewHolder);

        TileTouchHelperViewHolder itemViewHolder = (TileTouchHelperViewHolder) viewHolder;
        itemViewHolder.OnTileClear();
    }




}
