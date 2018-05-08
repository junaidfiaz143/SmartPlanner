package com.inventors.jd.smartplanner.databases;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import com.inventors.jd.smartplanner.models.Event;

import java.util.ArrayList;

/**
 * Created by jd on 20-Apr-18.
 */

public class EventDB extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "event.db";
    private static final int DATABASE_VERSION = 1;

    private Context context;

    public EventDB(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        String query = "CREATE TABLE event(id INTEGER PRIMARY KEY, title TEXT," +
                " description TEXT, time TEXT, from_day TEXT, from_month TEXT," +
                " from_year TEXT, to_day TEXT, to_month TEXT, to_year TEXT, location TEXT," +
                " image TEXT, week_days TEXT,  type INTEGER)";
        sqLiteDatabase.execSQL(query);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS event");
    }

    public long onEventInsert(Event event) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("title", event.getTitle());
        values.put("description", event.getDescription());
        values.put("time", event.getTime());
        values.put("from_day", event.getFrom_day());
        values.put("from_month", event.getFrom_month());
        values.put("from_year", event.getFrom_year());
        values.put("to_day", event.getTo_day());
        values.put("to_month", event.getTo_month());
        values.put("to_year", event.getTo_year());
        values.put("location", event.getLocation());
        values.put("image", event.getImage());
        values.put("week_days", event.getWeek_days());
        values.put("type", event.getType());

        return db.insert("event", null, values);
    }

    public ArrayList<Event> onGetAllEvent() {
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<Event> eventList = null;

        String query = "SELECT * FROM event";

        Cursor cursor = db.rawQuery(query, null);

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            eventList = new ArrayList<>();
            for (int i = 0; i < cursor.getCount(); i++) {
                Event event = new Event();
                event.setId(cursor.getInt(0));
                event.setTitle(cursor.getString(1));
                event.setDescription(cursor.getString(2));
                event.setTime(cursor.getString(3));
                event.setFrom_day(cursor.getString(4));
                event.setFrom_month(cursor.getString(5));
                event.setFrom_year(cursor.getString(6));
                event.setTo_day(cursor.getString(7));
                event.setTo_month(cursor.getString(8));
                event.setTo_year(cursor.getString(9));
                event.setLocation(cursor.getString(10));
                event.setImage(cursor.getString(11));
                event.setWeek_days(cursor.getString(12));
                event.setType(cursor.getInt(13));

                eventList.add(event);
                cursor.moveToNext();
            }
        }

        return eventList;
    }

    public ArrayList<Event> onGetDailyEvent(String day, String month, String year) {
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<Event> eventList = null;

        String query = "SELECT * FROM event WHERE " +
                "from_day <= '" + day + "' AND to_day >='" + day + "' AND " +
                "from_month <= '" + month + "' AND to_month >='" + month + "' AND " +
                "from_year <='" + year + "' AND to_year >='" + year + "'";

        Cursor cursor = db.rawQuery(query, null);

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            eventList = new ArrayList<>();
            for (int i = 0; i < cursor.getCount(); i++) {
                Event event = new Event();
                event.setId(cursor.getInt(0));
                event.setTitle(cursor.getString(1));
                event.setDescription(cursor.getString(2));
                event.setTime(cursor.getString(3));
                event.setFrom_day(cursor.getString(4));
                event.setFrom_month(cursor.getString(5));
                event.setFrom_year(cursor.getString(6));
                event.setTo_day(cursor.getString(7));
                event.setTo_month(cursor.getString(8));
                event.setTo_year(cursor.getString(9));
                event.setLocation(cursor.getString(10));
                event.setImage(cursor.getString(11));
                event.setWeek_days(cursor.getString(12));
                event.setType(cursor.getInt(13));

                eventList.add(event);
                cursor.moveToNext();
            }
        }

        return eventList;
    }

    public ArrayList<Event> onGetSpecificEvent(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<Event> eventList = null;

        String query = "SELECT * FROM event WHERE id = " + id;

        Cursor cursor = db.rawQuery(query, null);

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            eventList = new ArrayList<>();
            for (int i = 0; i < cursor.getCount(); i++) {
                Event event = new Event();
                event.setId(cursor.getInt(0));
                event.setTitle(cursor.getString(1));
                event.setDescription(cursor.getString(2));
                event.setTime(cursor.getString(3));
                event.setFrom_day(cursor.getString(4));
                event.setFrom_month(cursor.getString(5));
                event.setFrom_year(cursor.getString(6));
                event.setTo_day(cursor.getString(7));
                event.setTo_month(cursor.getString(8));
                event.setTo_year(cursor.getString(9));
                event.setLocation(cursor.getString(10));
                event.setImage(cursor.getString(11));
                event.setWeek_days(cursor.getString(12));
                event.setType(cursor.getInt(13));

                eventList.add(event);
                cursor.moveToNext();
            }
        }

        return eventList;
    }

    public ArrayList<Event> onGetMonthlyEvent(String month, String year) {
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<Event> eventList = null;

        String query = "SELECT * FROM event WHERE month ='" + month + "' AND year='" + year + "'";

        Cursor cursor = db.rawQuery(query, null);

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            eventList = new ArrayList<>();
            for (int i = 0; i < cursor.getCount(); i++) {
                Event event = new Event();
                event.setId(cursor.getInt(0));
                event.setTitle(cursor.getString(1));
                event.setDescription(cursor.getString(2));
                event.setTime(cursor.getString(3));
                event.setFrom_day(cursor.getString(4));
                event.setFrom_month(cursor.getString(5));
                event.setFrom_year(cursor.getString(6));
                event.setTo_year(cursor.getString(7));
                event.setTo_month(cursor.getString(8));
                event.setTo_year(cursor.getString(9));
                event.setLocation(cursor.getString(10));
                event.setImage(cursor.getString(11));
                event.setWeek_days(cursor.getString(12));
                event.setType(cursor.getInt(13));

                eventList.add(event);
                cursor.moveToNext();
            }
        }

        return eventList;
    }

    public int onUpdateEvent(Event event) {
        String query = "UPDATE event SET " +
                "title='" + event.getTitle() +
                "', description='" + event.getDescription() +
                "', time='" + event.getTime() +
                "', from_day='" + event.getFrom_day() +
                "', from_month='" + event.getFrom_month() +
                "', from_year='" + event.getFrom_year() +
                "', to_day='" + event.getTo_day() +
                "', to_month='" + event.getTo_month() +
                "', to_year='" + event.getTo_year() +
                "', location='" + event.getLocation() +
                "', image='" + event.getImage() +
                "', week_days='" + event.getWeek_days() +
                "', type='" + event.getType() +
                "' WHERE id=" + event.getId();

        SQLiteDatabase db = this.getWritableDatabase();

        try {
            db.execSQL(query);
            return 1;

        } catch (Exception exp) {
            Toast.makeText(context, "Error: " + exp.getMessage(), Toast.LENGTH_SHORT).show();
            return 0;
        }
    }

    public int onDeleteEvent(int id) {
        String query = "DELETE FROM event WHERE id = " + id;
        SQLiteDatabase db = this.getWritableDatabase();

        try {
            db.execSQL(query);
            return 1;

        } catch (Exception exp) {
            Toast.makeText(context, "Error: " + exp.getMessage(), Toast.LENGTH_SHORT).show();
            return 0;
        }
    }
}