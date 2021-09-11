package com.ivan.puzzle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;



public class MainActivity extends AppCompatActivity implements OnStartDragListener {


    private ItemTouchHelper _itemTouchHelper = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerListAdapter adapter = new RecyclerListAdapter(this);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_list);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        ItemTouchHelper.Callback callback = new TileTouchHelperCallback(adapter);
        _itemTouchHelper = new ItemTouchHelper(callback);
        _itemTouchHelper.attachToRecyclerView(recyclerView);

    }

    @Override
    public void onStartDrag(RecyclerView.ViewHolder viewHolder) {

        _itemTouchHelper.startDrag(viewHolder);
    }
}