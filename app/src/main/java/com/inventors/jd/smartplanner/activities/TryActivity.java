package com.inventors.jd.smartplanner.activities;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.TextView;

import com.inventors.jd.smartplanner.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by jd on 04-May-18.
 */

public class TryActivity extends AppCompatActivity {

    private AppCompatSpinner spnCount, spnCategory;

    private TextView txtMon, txtTue, txtWed, txtThr, txtFri, txtSat, txtSun;

    private int mon, tue, wed, thr, fri, sat, sun;

    private CardView weekLayout;

    private TextView txtFromDate, txtToDate;

    private int day, month, year;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.try_alarm_layout);

        txtFromDate = findViewById(R.id.txtFromDate);
        txtToDate = findViewById(R.id.txtToDate);

        Calendar calendar = Calendar.getInstance();

        day = calendar.get(Calendar.DAY_OF_MONTH);
        month = calendar.get(Calendar.MONTH);
        year = calendar.get(Calendar.YEAR);

        txtFromDate.setText(String.format("%02d", day) + "-" + String.format("%02d", (month + 1)) + "-" + year);
        txtToDate.setText(String.format("%02d", day) + "-" + String.format("%02d", (month + 1)) + "-" + year);

        txtFromDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog.OnDateSetListener myDateListener = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker arg0, int arg1, int arg2, int arg3) {
                        // TODO Auto-generated method stub
                        year = arg1;
                        month = arg2;
                        day = arg3;

//                        selectedCalendar.set(Calendar.YEAR, year);
//                        selectedCalendar.set(Calendar.MONTH, mon);
//                        selectedCalendar.set(Calendar.DATE, day);
//                        showDate(arg1, arg2 + 1, arg3);
                        txtFromDate.setText(String.format("%02d", day) + "-" + String.format("%02d", (month + 1)) + "-" + year);
                    }
                };

                new DatePickerDialog(TryActivity.this, myDateListener, year, month, day).show();

            }
        });

        txtToDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog.OnDateSetListener myDateListener = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker arg0, int arg1, int arg2, int arg3) {
                        // TODO Auto-generated method stub
                        year = arg1;
                        month = arg2;
                        day = arg3;

//                        selectedCalendar.set(Calendar.YEAR, year);
//                        selectedCalendar.set(Calendar.MONTH, mon);
//                        selectedCalendar.set(Calendar.DATE, day);
//                        showDate(arg1, arg2 + 1, arg3);
                        txtToDate.setText(String.format("%02d", day) + "-" + String.format("%02d", (month + 1)) + "-" + year);
                    }
                };

                new DatePickerDialog(TryActivity.this, myDateListener, year, month, day).show();

            }
        });

        weekLayout = findViewById(R.id.weekLayout);

        mon = tue = wed = thr = fri = sat = sun = 0;

        txtMon = findViewById(R.id.txtMon);
        txtTue = findViewById(R.id.txtTue);
        txtWed = findViewById(R.id.txtWed);
        txtThr = findViewById(R.id.txtThr);
        txtFri = findViewById(R.id.txtFri);
        txtSat = findViewById(R.id.txtSat);
        txtSun = findViewById(R.id.txtSun);

        spnCount = findViewById(R.id.spnCount);
        spnCategory = findViewById(R.id.spnCategory);

        List<Integer> countList = new ArrayList<>();
        countList.add(1);
        countList.add(2);
        countList.add(3);
        countList.add(4);
        countList.add(5);
        countList.add(6);
        countList.add(7);
        countList.add(8);
        countList.add(9);
        countList.add(10);

        List<String> categories = new ArrayList<>();
        categories.add("Day");
        categories.add("Week");
        categories.add("Month");
        categories.add("Year");

        ArrayAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, countList);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spnCount.setAdapter(adapter);

        ArrayAdapter adapter1 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, categories);

        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spnCategory.setAdapter(adapter1);

        spnCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (spnCategory.getSelectedItem().toString().equals("Week")) {
                    weekLayout.setVisibility(View.VISIBLE);
                } else
                    weekLayout.setVisibility(View.GONE);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    public void onBtnClick(View view) {
        switch (view.getId()) {
            case R.id.txtMon:
                if (mon == 0) {
                    txtMon.setBackground(getResources().getDrawable(R.drawable.outline_background));
                    mon = 1;
                } else {
                    txtMon.setBackground(null);
                    mon = 0;
                }
                break;
            case R.id.txtTue:
                if (tue == 0) {
                    txtTue.setBackground(getResources().getDrawable(R.drawable.outline_background));
                    tue = 1;
                } else {
                    txtTue.setBackground(null);
                    tue = 0;
                }
                break;
            case R.id.txtWed:
                if (wed == 0) {
                    txtWed.setBackground(getResources().getDrawable(R.drawable.outline_background));
                    wed = 1;
                } else {
                    txtWed.setBackground(null);
                    wed = 0;
                }
                break;
            case R.id.txtThr:
                if (thr == 0) {
                    txtThr.setBackground(getResources().getDrawable(R.drawable.outline_background));
                    thr = 1;
                } else {
                    txtThr.setBackground(null);
                    thr = 0;
                }
                break;
            case R.id.txtFri:
                if (fri == 0) {
                    txtFri.setBackground(getResources().getDrawable(R.drawable.outline_background));
                    fri = 1;
                } else {
                    txtFri.setBackground(null);
                    fri = 0;
                }
                break;
            case R.id.txtSat:
                if (sat == 0) {
                    txtSat.setBackground(getResources().getDrawable(R.drawable.outline_background));
                    sat = 1;
                } else {
                    txtSat.setBackground(null);
                    sat = 0;
                }
                break;
            case R.id.txtSun:
                if (sun == 0) {
                    txtSun.setBackground(getResources().getDrawable(R.drawable.outline_background));
                    sun = 1;
                } else {
                    txtSun.setBackground(null);
                    sun = 0;
                }
                break;
            default:
                finish();
//                Uri notifySound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
//
//                NotificationCompat.Builder mBuilder =
//                        new NotificationCompat.Builder(this, "123")
//                                .setSmallIcon(R.mipmap.ic_launcher_round)
//                                .setContentTitle("Title")
//                                .setContentText("You have set an Event!")
//                                .setSound(notifySound);
//
//                PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
//                        new Intent(this, CalendarActivity.class), PendingIntent.FLAG_UPDATE_CURRENT);
//
//                mBuilder.setContentIntent(contentIntent);
//
//                NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//
//                if (mNotificationManager != null) {
//                    mNotificationManager.notify(1, mBuilder.build());
//                }
        }
    }
}
