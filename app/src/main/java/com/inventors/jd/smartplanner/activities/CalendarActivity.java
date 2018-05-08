package com.inventors.jd.smartplanner.activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.CalendarView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.github.sundeepk.compactcalendarview.domain.Event;
import com.inventors.jd.smartplanner.R;
import com.inventors.jd.smartplanner.adapters.EventAdapter;
import com.inventors.jd.smartplanner.databases.EventDB;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class CalendarActivity extends AppCompatActivity {

    private static final String TAG = "onCreate Calendar";

    private CalendarView calendarView;

    private TextView txtMonth;

    private RecyclerView recyclerView;

    private String date;

    public static Calendar c;

    private EventAdapter adapter;

    public static EventDB db;

    public static CompactCalendarView compactCalendarView;

    private String month;

    private ArrayList<com.inventors.jd.smartplanner.models.Event> eventList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        db = new EventDB(this);

        recyclerView = findViewById(R.id.recyclerView);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);

        calendarView = findViewById(R.id.calendarView);

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2) {
                Toast.makeText(CalendarActivity.this, i2 + "-" + (i1 + 1) + "-" + i, Toast.LENGTH_SHORT).show();
            }
        });

        txtMonth = findViewById(R.id.txtMonth);

        compactCalendarView = findViewById(R.id.compactcalendar_view);
        // Set first day of week to Monday, defaults to Monday so calling setFirstDayOfWeek is not necessary
        // Use constants provided by Java Calendar class
        compactCalendarView.setFirstDayOfWeek(Calendar.MONDAY);

//        Event ev2 = new Event(Color.WHITE, timeInMillis(7, 4, 2018), "Reminder Task!");
//        Event ev3 = new Event(Color.WHITE, timeInMillis(8, 4, 2018), "Reminder Task New!");
//        compactCalendarView.addEvent(ev2);
//        compactCalendarView.addEvent(ev3);

        // Query for events on Sun, 07 Jun 2015 GMT.
        // Time is not relevant when querying for events, since events are returned by day.
        // So you can pass in any arbitrary DateTime and you will receive all events for that day.
//        List<Event> events = compactCalendarView.getEvents(timeInMillis(7, 4, 2018)); // can also take a Date object

        // events has size 2 with the 2 events inserted previously
//        Log.d(TAG, "Events: " + events);

        c = Calendar.getInstance();

        getMonthName(c.get(Calendar.MONTH));

//        onSetEventPoints();

        // define a listener to receive callbacks when certain events happen.
        compactCalendarView.setListener(new CompactCalendarView.CompactCalendarViewListener() {
            @Override
            public void onDayClick(Date dateClicked) {
                List<Event> events = compactCalendarView.getEvents(dateClicked);
                c.setTime(dateClicked);
//                Toast.makeText(CalendarActivity.this, dateClicked + " - " + events.toString(), Toast.LENGTH_LONG).show();
                Log.d(TAG, "Day was clicked: " + dateClicked + " with events " + events);

                date = String.format("%02d", c.get(Calendar.DATE)) + "-" + String.format("%02d", (c.get(Calendar.MONTH) + 1)) + "-" + c.get(Calendar.YEAR);
//                Toast.makeText(CalendarActivity.this, "" + date, Toast.LENGTH_LONG).show();

                try {
                    eventList = new ArrayList<>();
                    recyclerView.removeAllViewsInLayout();
//                    adapter.notifyDataSetChanged();

                    ArrayList<com.inventors.jd.smartplanner.models.Event> list = new ArrayList<>();
                    eventList = db.onGetDailyEvent(String.format("%02d", c.get(Calendar.DATE)), String.format("%02d", c.get(Calendar.MONTH) + 1), "" + c.get(Calendar.YEAR));

                    for (com.inventors.jd.smartplanner.models.Event event : eventList) {
                        for (String number : event.getWeek_days().split(",")) {
                            switch (number) {
                                case "1":
                                    if (c.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY)
                                        list.add(event);
                                    break;
                                case "2":
                                    if (c.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY)
                                        list.add(event);
                                    break;
                                case "3":
                                    if (c.get(Calendar.DAY_OF_WEEK) == Calendar.TUESDAY)
                                        list.add(event);
                                    break;
                                case "4":
                                    if (c.get(Calendar.DAY_OF_WEEK) == Calendar.WEDNESDAY)
                                        list.add(event);
                                    break;
                                case "5":
                                    if (c.get(Calendar.DAY_OF_WEEK) == Calendar.THURSDAY)
                                        list.add(event);
                                    break;
                                case "6":
                                    if (c.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY)
                                        list.add(event);
                                    break;
                                case "7":
                                    if (c.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY)
                                        list.add(event);
                                    break;
                            }

                        }
                    }
                    if (list.size() > 0) {
                        adapter = new EventAdapter(CalendarActivity.this, list);
                        recyclerView.setAdapter(new EventAdapter(CalendarActivity.this, eventList));
                    }
                } catch (Exception exp) {
//                    Toast.makeText(CalendarActivity.this, "not found", Toast.LENGTH_SHORT).show();
                }

//                startActivity(new Intent(CalendarActivity.this, AlarmActivity.class).putExtra("selected_date", date));

            }

            @Override
            public void onMonthScroll(Date firstDayOfNewMonth) {
                Log.d(TAG, "Month was scrolled to: " + firstDayOfNewMonth);
                Calendar cal = Calendar.getInstance();
                cal.setTime(firstDayOfNewMonth);
//                Toast.makeText(CalendarActivity.this, "" + cal.get(Calendar.MONTH), Toast.LENGTH_SHORT).show();
                getMonthName(cal.get(Calendar.MONTH));
            }
        });

        date = String.format("%02d", c.get(Calendar.DATE)) + "-" + String.format("%02d", (c.get(Calendar.MONTH) + 1)) + "-" + c.get(Calendar.YEAR);

        try {
            eventList = new ArrayList<>();
            eventList = db.onGetDailyEvent(String.format("%02d", c.get(Calendar.DATE)), String.format("%02d", c.get(Calendar.MONTH) + 1), "" + c.get(Calendar.YEAR));
            recyclerView.removeAllViews();

            if (eventList.size() > 0) {
                adapter = new EventAdapter(this, eventList);
                recyclerView.setAdapter(adapter);
            }
        } catch (Exception exp) {

        }

    }

    public void getMonthName(int n) {
        switch (n) {
            case 0:
                txtMonth.setText("January");
                month = "01";
                break;
            case 1:
                txtMonth.setText("February");
                month = "02";
                break;
            case 2:
                txtMonth.setText("March");
                month = "03";
                break;
            case 3:
                txtMonth.setText("April");
                month = "04";
                break;
            case 4:
                txtMonth.setText("May");
                month = "05";
                break;
            case 5:
                txtMonth.setText("June");
                month = "06";
                break;
            case 6:
                txtMonth.setText("July");
                month = "07";
                break;
            case 7:
                txtMonth.setText("August");
                month = "08";
                break;
            case 8:
                txtMonth.setText("September");
                month = "09";
                break;
            case 9:
                txtMonth.setText("October");
                month = "10";
                break;
            case 10:
                txtMonth.setText("November");
                month = "11";
                break;
            case 11:
                txtMonth.setText("December");
                month = "12";
                break;
        }
    }

    private static long timeInMillis(int day, int month, int year) {
        String str_date = month + "-" + day + "-" + year;
        DateFormat formatter = new SimpleDateFormat("MM-dd-yyyy");
        Date date = null;
        try {
            date = formatter.parse(str_date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long output = date.getTime() / 1000L;
        String str = Long.toString(output);
        return Long.parseLong(str) * 1000;
    }

//    @SuppressLint("RestrictedApi")
//    public void onMenuBtn(View view) {
//        MenuBuilder menuBuilder = new MenuBuilder(this);
//        MenuInflater inflater = new MenuInflater(this);
//        inflater.inflate(R.menu.list_menu, menuBuilder);
//        MenuPopupHelper optionsMenu = new MenuPopupHelper(this, menuBuilder, view);
//        optionsMenu.setForceShowIcon(true);
//
//        // Set Item Click Listener
//        menuBuilder.setCallback(new MenuBuilder.Callback() {
//            @Override
//            public boolean onMenuItemSelected(MenuBuilder menu, MenuItem item) {
//                switch (item.getItemId()) {
//                    case R.id.opt_edit: // Handle option1 Click
//                        Toast.makeText(CalendarActivity.this, "edit", Toast.LENGTH_SHORT).show();
//                    case R.id.opt_delete: // Handle option2 Click
//                        Toast.makeText(CalendarActivity.this, "delete", Toast.LENGTH_SHORT).show();
//                    default:
//                        return false;
//                }
//            }
//
//            @Override
//            public void onMenuModeChange(MenuBuilder menu) {
//            }
//        });
//
//        // Display the menu
//        optionsMenu.show();
//    }

    public void onAddBtn(View view) {
        startActivity(new Intent(this, AlarmActivity.class).putExtra("selected_date", date));
    }

    public void allScheduleBtn(View view) {
        startActivity(new Intent(this, AllScheduleActivity.class));

    }

    @Override
    protected void onResume() {
        super.onResume();

        try {
            recyclerView.removeAllViewsInLayout();
            eventList = new ArrayList<>();

            eventList = db.onGetDailyEvent(String.format("%02d", c.get(Calendar.DATE)), String.format("%02d", c.get(Calendar.MONTH) + 1), "" + c.get(Calendar.YEAR));
            if (eventList != null)
                if (eventList.size() > 0) {
                    adapter = new EventAdapter(this, eventList);
                    recyclerView.setAdapter(adapter);
                }
        } catch (Exception exp) {
        }

        try {
            onSetEventPoints();
        } catch (Exception exp) {
        }
    }

    public static void onSetEventPoints() {
//        String data = "28-04-2018";
//        String[] items = data.split("-");
//        for (String item : items) {
//            Toast.makeText(this, "" + item, Toast.LENGTH_SHORT).show();
//        }

        try {

            Calendar calendar = c;
            ArrayList<com.inventors.jd.smartplanner.models.Event> list = new ArrayList<>();

            compactCalendarView.removeAllEvents();
            for (int i = 1; i <= 31; i++) {
                list = db.onGetDailyEvent(String.format("%02d", i), String.format("%02d", c.get(Calendar.MONTH) + 1), "" + c.get(Calendar.YEAR));

                if (list != null)
                    for (com.inventors.jd.smartplanner.models.Event event : list) {

                        calendar.set(Calendar.DAY_OF_MONTH, i);
                        int day = i;
                        int month = calendar.get(Calendar.MONTH) + 1;
                        int year = calendar.get(Calendar.YEAR);

                        for (String number : event.getWeek_days().split(",")) {
                            switch (number) {
                                case "1":
                                    if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY)
                                        compactCalendarView.addEvent(new Event(Color.WHITE, timeInMillis(day, month, year), "Reminder Task New!"));
                                    break;
                                case "2":
                                    if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY)
                                        compactCalendarView.addEvent(new Event(Color.WHITE, timeInMillis(day, month, year), "Reminder Task New!"));
                                    break;
                                case "3":
                                    if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.TUESDAY)
                                        compactCalendarView.addEvent(new Event(Color.WHITE, timeInMillis(day, month, year), "Reminder Task New!"));
                                    break;
                                case "4":
                                    if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.WEDNESDAY)
                                        compactCalendarView.addEvent(new Event(Color.WHITE, timeInMillis(day, month, year), "Reminder Task New!"));
                                    break;
                                case "5":
                                    if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.THURSDAY)
                                        compactCalendarView.addEvent(new Event(Color.WHITE, timeInMillis(day, month, year), "Reminder Task New!"));
                                    break;
                                case "6":
                                    if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY)
                                        compactCalendarView.addEvent(new Event(Color.WHITE, timeInMillis(day, month, year), "Reminder Task New!"));
                                    break;
                                case "7":
                                    if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY)
                                        compactCalendarView.addEvent(new Event(Color.WHITE, timeInMillis(day, month, year), "Reminder Task New!"));
                                    break;
                            }

                        }
                    }
            }

        } catch (Exception exp) {
//            Toast.makeText(this, "nb: " + exp.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}
