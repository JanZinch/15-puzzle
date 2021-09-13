package com.ivan.puzzle;

public interface TileTouchHelperAdapter {

    public void OnTileMove(int fromPosition, int toPosition);
    public void OnTileDismiss(int position);

    public int GetPotentialMovingDirection(int position);

}
