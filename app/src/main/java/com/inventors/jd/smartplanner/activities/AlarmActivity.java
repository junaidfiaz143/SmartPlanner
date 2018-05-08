package com.inventors.jd.smartplanner.activities;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.inventors.jd.smartplanner.R;
import com.inventors.jd.smartplanner.databases.EventDB;
import com.inventors.jd.smartplanner.models.Event;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class AlarmActivity extends AppCompatActivity {

    private TextView txtTime;

    private TextView txtDate;

    private int year, month, day;
    private int hour, min;

    private String image = "";

    private String week_days = "";

    private int type;

    private TextView edtLocation;

    private EditText edtTitle, edtDescription;

    private Boolean updateEvent = false;
    private int id;

    private AppCompatSpinner spnType, spnNotification;

    private TextView txtAM_PM;

    private ImageView imageView;

    private TextView txtMon, txtTue, txtWed, txtThr, txtFri, txtSat, txtSun;

    private int mon, tue, wed, thr, fri, sat, sun;

    private CardView weekLayout;
    private int request_code;

    private Calendar selectedCalendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sample_layout);

//        getRequestCode();
//        Toast.makeText(this, "" + request_code, Toast.LENGTH_SHORT).show();

        final CharSequence[] buttons = {"Take Photo", "Choose from Gallery", "Cancel"};
        imageView = findViewById(R.id.imageView);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(AlarmActivity.this);
                builder.setTitle("Add Photo");
                builder.setItems(buttons, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        if (buttons[i].equals("Take Photo"))
                            cameraIntent();
                        else if (buttons[i].equals("Choose from Gallery"))
                            galleryIntent();
                        else
                            dialogInterface.dismiss();
                    }
                });

                builder.show();
            }
        });


        weekLayout = findViewById(R.id.weekLayout);
        weekLayout.setVisibility(View.GONE);

        mon = tue = wed = thr = fri = sat = sun = 0;

        txtMon = findViewById(R.id.txtMon);
        txtTue = findViewById(R.id.txtTue);
        txtWed = findViewById(R.id.txtWed);
        txtThr = findViewById(R.id.txtThr);
        txtFri = findViewById(R.id.txtFri);
        txtSat = findViewById(R.id.txtSat);
        txtSun = findViewById(R.id.txtSun);


        txtAM_PM = findViewById(R.id.txtAM_PM);

        spnType = findViewById(R.id.spnCategory);
        spnNotification = findViewById(R.id.spnNotification);

        List<String> typeList = new ArrayList<>();
        typeList.add("Once");
        typeList.add("Weekly");
        typeList.add("Monthly");
        typeList.add("Yearly");
        typeList.add("Repeat So on");
        typeList.add("Custom");

        List<String> notifyList = new ArrayList<>();
        notifyList.add("On time");
        notifyList.add("30 min before");

        ArrayAdapter typeAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, typeList);
        typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnType.setAdapter(typeAdapter);

        spnType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                Toast.makeText(AlarmActivity.this, "" + spnCategory.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
                if (spnType.getSelectedItem().toString().equals("Custom")) {
                    startActivity(new Intent(AlarmActivity.this, TryActivity.class));
                    weekLayout.setVisibility(View.GONE);
                } else if (spnType.getSelectedItem().toString().equals("Once"))
                    weekLayout.setVisibility(View.GONE);
                else
                    weekLayout.setVisibility(View.VISIBLE);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                if (spnType.getSelectedItem().toString().equals("Custom")) {
                    startActivity(new Intent(AlarmActivity.this, TryActivity.class));
                    weekLayout.setVisibility(View.GONE);
                } else if (spnType.getSelectedItem().toString().equals("Once"))
                    weekLayout.setVisibility(View.GONE);
                else
                    weekLayout.setVisibility(View.VISIBLE);
            }
        });

        ArrayAdapter notifyAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, notifyList);
        notifyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnNotification.setAdapter(notifyAdapter);

        edtLocation = findViewById(R.id.edtLocation);
        edtLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(AlarmActivity.this, MapsActivity.class), 1122);
            }
        });

        Calendar cal = Calendar.getInstance();
        selectedCalendar = cal;

        hour = cal.get(Calendar.HOUR);
        min = cal.get(Calendar.MINUTE);

        year = cal.get(Calendar.YEAR);
        month = cal.get(Calendar.MONTH);
        day = cal.get(Calendar.DATE);

        txtDate = findViewById(R.id.date_or_days);

        txtDate.setText(String.format("%02d", day) + "-" + String.format("%02d", (month + 1)) + "-" + year);

        txtDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog.OnDateSetListener myDateListener = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker arg0, int arg1, int arg2, int arg3) {
                        // TODO Auto-generated method stub
                        year = arg1;
                        month = arg2;
                        day = arg3;

                        selectedCalendar.set(Calendar.YEAR, year);
                        selectedCalendar.set(Calendar.MONTH, mon);
                        selectedCalendar.set(Calendar.DATE, day);
//                        showDate(arg1, arg2 + 1, arg3);
                        txtDate.setText(String.format("%02d", day) + "-" + String.format("%02d", (month + 1)) + "-" + year);
                    }
                };

                new DatePickerDialog(AlarmActivity.this, myDateListener, year, month, day).show();
            }
        });

        edtTitle = findViewById(R.id.edtTitle);
        edtDescription = findViewById(R.id.edtDescription);

        txtTime = findViewById(R.id.txtTime);
        txtTime.setText(String.format("%02d", hour) + ":" + String.format("%02d", min));
        txtTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(AlarmActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        txtAM_PM.setText((selectedHour < 12) ? "AM" : "PM");

                        hour = selectedHour;
                        min = selectedMinute;

                        selectedCalendar.set(Calendar.HOUR, hour);
                        selectedCalendar.set(Calendar.MINUTE, min);

                        if (selectedHour % 12 == 0)
                            txtTime.setText("12:" + String.format("%02d", selectedMinute));
                        else
                            txtTime.setText(String.format("%02d", selectedHour % 12) + ":" + String.format("%02d", selectedMinute));
                    }
                }, hour, min, false);//No 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }
        });

        try {
            if (!getIntent().getExtras().getString("date").equals("")) {
                id = getIntent().getExtras().getInt("id");
                edtTitle.setText(getIntent().getExtras().getString("title"));
                edtDescription.setText(getIntent().getExtras().getString("description"));
                txtTime.setText(getIntent().getExtras().getString("time"));
                txtAM_PM.setText(getIntent().getExtras().getString("am_pm"));
                edtLocation.setText(getIntent().getExtras().getString("location"));
                txtDate.setText(getIntent().getExtras().getString("date"));
                updateEvent = true;
            }
        } catch (Exception exp) {
//            Toast.makeText(this, "" + exp.getMessage(), Toast.LENGTH_SHORT).show();
        }

        try {
            if (!getIntent().getExtras().getString("selected_date").equals(""))
                txtDate.setText(getIntent().getExtras().getString("selected_date"));
        } catch (Exception exp) {

        }

    }

    @Override
    protected void onResume() {
        super.onResume();

        try {
            if (!MapsActivity.myLocation.equals("")) {
                edtLocation.setText(MapsActivity.myLocation);
            }
        } catch (Exception exp) {

        }
    }

    public void onInsertEvent(View view) {
        String title = edtTitle.getText().toString();
        String description = edtDescription.getText().toString();
        String time = txtTime.getText().toString();
        String date = txtDate.getText().toString();
        String location = edtLocation.getText().toString();

        Event event = new Event();

        EventDB db = new EventDB(this);

        long checkInsertion = 0;

        String[] dates = date.split("-");

        if (updateEvent) {
            event = new Event();
//            event = new Event(id, title, description, time + " " + txtAM_PM.getText().toString(), dates[0], dates[1], dates[2], location);
            if (db.onUpdateEvent(event) == 1) {
                Toast.makeText(this, "Event Updated", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(this, "Something went wrong!", Toast.LENGTH_SHORT).show();
            }
        } else {
            if (spnType.getSelectedItem().toString().equals("Once")) {
                getType();
                getWeekDays();
                event = new Event(title, description, time + " " + txtAM_PM.getText().toString(), dates[0], dates[1], dates[2], dates[0], dates[1], dates[2], location, image, week_days, type);
                checkInsertion = db.onEventInsert(event);
            } else if (spnType.getSelectedItem().toString().equals("Weekly")) {
                getType();
                getWeekDays();
                Calendar calendar;
                calendar = selectedCalendar;
                calendar.add(Calendar.DAY_OF_MONTH, 7);
                Toast.makeText(this, "" + calendar.get(Calendar.DAY_OF_MONTH), Toast.LENGTH_SHORT).show();
                event = new Event(title, description, time + " " + txtAM_PM.getText().toString(), dates[0], dates[1], dates[2], "" + calendar.get(Calendar.DAY_OF_MONTH), "" + (calendar.get(Calendar.MONTH) + 1), calendar.get(Calendar.YEAR) + "", location, image, week_days, type);
                checkInsertion = db.onEventInsert(event);
            } else
                Toast.makeText(this, "functionality under construction!", Toast.LENGTH_SHORT).show();
        }

        if (!updateEvent)
            if (checkInsertion > -1) {
                if (spnType.getSelectedItem().toString().equals("Once"))
                    setAlarmOnce(event);
                Toast.makeText(this, "Event Inserted", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(this, "Something went wrong!", Toast.LENGTH_SHORT).show();
            }

    }

    public void galleryIntent() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select image"), 1);
    }

    public void cameraIntent() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, 2);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK)
            if (requestCode == 1) {
//                onSelectFromGallery(data);
                Uri uri = data.getData();
                image = uri.toString();
                imageView.setImageURI(uri);
            } else if (requestCode == 2) {
                onCaptureImage(data);
            }
    }

    public void onSelectFromGallery(Intent data) {
        Bitmap bm = null;

        try {
            if (data != null) {
                try {
                    bm = MediaStore.Images.Media.getBitmap(getApplication().getContentResolver(), data.getData());
                } catch (Exception exp) {
                }
            }
        } catch (Exception exp) {
        }

        imageView.setImageBitmap(bm);
    }

    public void onCaptureImage(Intent data) {
        Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        thumbnail.compress(Bitmap.CompressFormat.JPEG, 100, bytes);

        File destination = new File(Environment.getExternalStorageDirectory(), System.currentTimeMillis() + ".jpg");

        FileOutputStream fo;
        try {
            destination.createNewFile();
            fo = new FileOutputStream(destination);
            fo.write(bytes.toByteArray());
            fo.close();
        } catch (Exception exp) {
        }

        imageView.setImageURI(getImageUri(this, thumbnail));

        image = getImageUri(this, thumbnail).toString();
//        imageView.setImageBitmap(thumbnail);
    }

    private Uri getImageUri(Context context, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(context.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
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
        }
    }

    public void getRequestCode() {
        SharedPreferences preferences = getSharedPreferences("Alarm", MODE_PRIVATE);
        request_code = preferences.getInt("alarm_request_code", 1);

    }

    private void updateRequestCode() {
        SharedPreferences preferences = getSharedPreferences("Alarm", MODE_PRIVATE);

        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("alarm_request_code", ++request_code);

        editor.apply();
    }

    public void setAlarmOnce(Event event) {

        getRequestCode();

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.HOUR, hour);
        calendar.set(Calendar.MINUTE, min);

        // Check we aren't setting it in the past which would trigger it to fire instantly
        if (calendar.getTimeInMillis() < System.currentTimeMillis()) {
            calendar.add(Calendar.DAY_OF_YEAR, 7);
        }

        // Set this to whatever you were planning to do at the given time
        Intent i = new Intent(this, AlarmReceiver.class);
        i.putExtra("alarm_request_code", request_code);

        PendingIntent p1 = PendingIntent.getBroadcast(this, request_code, i, PendingIntent.FLAG_UPDATE_CURRENT);

        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), p1);

        updateRequestCode();
    }

    public void setAlarmWeekly(int day) {

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, 7);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.HOUR, hour);
        calendar.set(Calendar.MINUTE, min);
        calendar.set(Calendar.DAY_OF_WEEK, day);

        // Check we aren't setting it in the past which would trigger it to fire instantly
        if (calendar.getTimeInMillis() < System.currentTimeMillis()) {
            calendar.add(Calendar.DAY_OF_YEAR, 7);
        }

        // Set this to whatever you were planning to do at the given time
        Intent i = new Intent(this, AlarmReceiver.class);
        i.putExtra("alarm_request_code", request_code);

        PendingIntent p1 = PendingIntent.getBroadcast(this, request_code, i, PendingIntent.FLAG_UPDATE_CURRENT);

        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY * 7, p1);
    }

    public void setAlarmMonthly() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, 30);
    }

    public void setAlarmYearly() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, 365);
    }

    public void setAlarmRepeatSoOn() {
    }

    public void setAlarmCustom() {
    }

    public void getType() {
        switch (spnType.getSelectedItem().toString()) {
            case "Once":
                type = 0;
                break;
            case "Weekly":
                type = 1;
                break;
            case "Monthly":
                type = 2;
                break;
            case "Yearly":
                type = 3;
                break;
            case "Repeat So on":
                type = 4;
                break;
            case "Custom":
                type = 5;
                break;
        }
    }

    public void getWeekDays() {
        week_days = "";

        if (sun == 1) {
            week_days = week_days + "1,";
        }
        if (mon == 1) {
            week_days = week_days + "2,";
        }
        if (tue == 1) {
            week_days = week_days + "3,";
        }
        if (wed == 1) {
            week_days = week_days + "4,";
        }
        if (thr == 1) {
            week_days = week_days + "5,";
        }
        if (fri == 1) {
            week_days = week_days + "6,";
        }
        if (sat == 1) {
            week_days = week_days + "7,";
        }
        week_days = week_days.replaceAll(",$", "");

        if (type == 1)
            getWeekNames();
    }

    public void getWeekNames() {
        for (String number : week_days.split(",")) {
            switch (number) {
                case "1":
                    Toast.makeText(this, "Sunday", Toast.LENGTH_SHORT).show();
                    getRequestCode();
                    setAlarmWeekly(Calendar.SUNDAY);
                    updateRequestCode();
                    break;
                case "2":
                    Toast.makeText(this, "Monday", Toast.LENGTH_SHORT).show();
                    getRequestCode();
                    setAlarmWeekly(Calendar.MONDAY);
                    updateRequestCode();
                    break;
                case "3":
                    Toast.makeText(this, "Tuesday", Toast.LENGTH_SHORT).show();
                    getRequestCode();
                    setAlarmWeekly(Calendar.TUESDAY);
                    updateRequestCode();
                    break;
                case "4":
                    Toast.makeText(this, "Wednesday", Toast.LENGTH_SHORT).show();
                    getRequestCode();
                    setAlarmWeekly(Calendar.WEDNESDAY);
                    updateRequestCode();
                    break;
                case "5":
                    Toast.makeText(this, "Thursday", Toast.LENGTH_SHORT).show();
                    getRequestCode();
                    setAlarmWeekly(Calendar.THURSDAY);
                    updateRequestCode();
                    break;
                case "6":
                    Toast.makeText(this, "Friday", Toast.LENGTH_SHORT).show();
                    getRequestCode();
                    setAlarmWeekly(Calendar.FRIDAY);
                    updateRequestCode();
                    break;
                case "7":
                    Toast.makeText(this, "Saturday", Toast.LENGTH_SHORT).show();
                    getRequestCode();
                    setAlarmWeekly(Calendar.SATURDAY);
                    updateRequestCode();
                    break;
            }
        }
    }
}
