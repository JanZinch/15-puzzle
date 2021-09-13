package com.ivan.puzzle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity implements OnStartDragListener {

    public static MainActivity instance = null;

    private  static final String COMPLETE = "Level completed!";
    private ItemTouchHelper _itemTouchHelper = null;
    private final int _spanCount = 4;

    private TextView _gameStateLabel = null;

    private RecyclerView _recyclerView = null;
    private RecyclerView.LayoutManager _layoutManager = null;


    private RecyclerListAdapter _adapter = null;
    private ItemTouchHelper.Callback _callback = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        instance = this;

        LoadLevel();
    }


    protected void LoadLevel(){

        _gameStateLabel = (TextView) findViewById(R.id.state_label);
        _gameStateLabel.setText("");

        _adapter = new RecyclerListAdapter(this);

        _recyclerView = (RecyclerView) findViewById(R.id.recycler_list);
        _recyclerView.setHasFixedSize(true);
        _recyclerView.setAdapter(_adapter);

        //RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);

        _layoutManager = new GridLayoutManager(this, _spanCount);
        _recyclerView.setLayoutManager(_layoutManager);

        _callback = new TileTouchHelperCallback(_adapter);
        _itemTouchHelper = new ItemTouchHelper(_callback);
        _itemTouchHelper.attachToRecyclerView(_recyclerView);
    }

    @Override
    protected void onStart() {
        super.onStart();

        Button restartButton = (Button) findViewById(R.id.restart_button);
        restartButton.setOnClickListener(v -> {

            LoadLevel();
            //onCreate(new Bundle());
        });
    }


    public void SetLevelCompleted(){

        _gameStateLabel.setText(COMPLETE);
    }


    @Override
    public void onStartDrag(RecyclerView.ViewHolder viewHolder) {

        _itemTouchHelper.startDrag(viewHolder);
    }
}