package com.inventors.jd.smartplanner.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.inventors.jd.smartplanner.R;
import com.inventors.jd.smartplanner.adapters.EventAdapter;
import com.inventors.jd.smartplanner.databases.EventDB;
import com.inventors.jd.smartplanner.models.Event;

import java.util.ArrayList;

public class AllScheduleActivity extends AppCompatActivity {

    private RecyclerView recyclerView;

    private ArrayList<Event> eventList = new ArrayList<>();
    private EventDB db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_schedule);

        recyclerView = findViewById(R.id.recyclerView);

        RecyclerView.LayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);

        db = new EventDB(this);

        try {
            eventList = db.onGetAllEvent();

            if (eventList != null)
                if (eventList.size() > 0)
                    recyclerView.setAdapter(new EventAdapter(this, eventList));
        } catch (Exception exp) {

        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        try {
            recyclerView.removeAllViewsInLayout();
            eventList = new ArrayList<>();

            eventList = db.onGetAllEvent();
            if (eventList != null)
                if (eventList.size() > 0)
                    recyclerView.setAdapter(new EventAdapter(this, eventList));
        } catch (Exception exp) {
        }
    }
}