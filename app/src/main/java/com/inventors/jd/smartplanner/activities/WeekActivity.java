package com.inventors.jd.smartplanner.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.inventors.jd.smartplanner.R;

public class WeekActivity extends AppCompatActivity {

//    WeekView mWeekView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_week);
//
//        // Get a reference for the week view in the layout.
//        mWeekView = findViewById(R.id.weekView);
//
//// Set an action when any event is clicked.
//        WeekView.EventClickListener mEventClickListener = new WeekView.EventClickListener() {
//            @Override
//            public void onEventClick(WeekViewEvent event, RectF eventRect) {
//
//            }
//        };
//        mWeekView.setOnEventClickListener(mEventClickListener);
//
//// The week view has infinite scrolling horizontally. We have to provide the events of a
//// month every time the month changes on the week view.
//        MonthLoader.MonthChangeListener mMonthChangeListener = new MonthLoader.MonthChangeListener() {
//            @Override
//            public List<? extends WeekViewEvent> onMonthChange(int newYear, int newMonth) {
//                return null;
//            }
//        };
//        mWeekView.setMonthChangeListener(mMonthChangeListener);
//
//// Set long press listener for events.
//
//        WeekView.EventLongPressListener mEventLongPressListener = new WeekView.EventLongPressListener() {
//            @Override
//            public void onEventLongPress(WeekViewEvent event, RectF eventRect) {
//
//            }
//        };
//        mWeekView.setEventLongPressListener(mEventLongPressListener);
//    }

    }
}
