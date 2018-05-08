package com.inventors.jd.smartplanner.activities;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.inventors.jd.smartplanner.R;

import java.util.Calendar;

/**
 * Created by jd on 05-May-18.
 */

public class SampleActivity extends AppCompatActivity {

    private int request_code = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sample_layout);

//        setUpAlarms();

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, 7);

        Toast.makeText(this, "" + calendar.get(Calendar.DATE), Toast.LENGTH_SHORT).show();
    }

    private void scheduleAlarm(int dayOfWeek) {

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_WEEK, dayOfWeek);
        calendar.set(Calendar.MONTH, Calendar.MAY);

        // Check we aren't setting it in the past which would trigger it to fire instantly
        if (calendar.getTimeInMillis() < System.currentTimeMillis()) {
            calendar.add(Calendar.DAY_OF_YEAR, 7);
        }

        // Set this to whatever you were planning to do at the given time
        Intent i = new Intent(this, AlarmReceiver.class);
        i.putExtra("alarm_id", request_code);
        i.putExtra("alarm_month", 4);
        if (request_code == 0)
            i.putExtra("alarm_day", Calendar.MONDAY);
        else
            i.putExtra("alarm_day", Calendar.FRIDAY);
//        i.putExtra(ApplicationUtils.KEY_IS_IN_PAST, isinpast);

        PendingIntent p1 = PendingIntent.getBroadcast(this, request_code++, i, PendingIntent.FLAG_UPDATE_CURRENT);


        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY * 7, p1);
    }

    private void setUpAlarms() {

        scheduleAlarm(Calendar.MONDAY);
        scheduleAlarm(Calendar.FRIDAY);
    }
}
