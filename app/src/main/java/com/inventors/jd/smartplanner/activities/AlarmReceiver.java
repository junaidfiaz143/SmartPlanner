package com.inventors.jd.smartplanner.activities;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

import com.inventors.jd.smartplanner.R;
import com.inventors.jd.smartplanner.databases.EventDB;
import com.inventors.jd.smartplanner.models.Event;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by jd on 07-May-18.
 */

public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        Calendar calendar = Calendar.getInstance();

        EventDB db = new EventDB(context);

        ArrayList<Event> eventList = db.onGetSpecificEvent("" + intent.getExtras().getInt("alarm_request_code"));

        if (eventList != null)
            for (Event event : eventList) {

                if (event.getType() != 0)
                    onTriggerAlarm(event, calendar, context);
                else
                    onTriggerOnceAlarm(event, calendar, context);

            }
        else
            Toast.makeText(context, "No Alarm", Toast.LENGTH_SHORT).show();
    }

    public void onTriggerOnceAlarm(Event event, Calendar calendar, Context context) {

        String time[] = event.getTime().split(" ");
        String hour_min[] = time[0].split(":");
        try {
            if (Integer.parseInt(event.getFrom_year()) == calendar.get(Calendar.YEAR)) {
                if ((Integer.parseInt(event.getFrom_month()) - 1) == calendar.get(Calendar.MONTH)) {
                    if (Integer.parseInt(event.getFrom_day()) == calendar.get(Calendar.DATE))
                        if (Integer.parseInt(hour_min[0]) == calendar.get(Calendar.HOUR))
                            if (Integer.parseInt(hour_min[1]) == calendar.get(Calendar.MINUTE))
                                sendNotification(context, event.getId(), event.getTitle(), event.getDescription());
                            else
                                Toast.makeText(context, "wrong min", Toast.LENGTH_SHORT).show();
                        else
                            Toast.makeText(context, "wrong hour", Toast.LENGTH_SHORT).show();
                    else
                        Toast.makeText(context, "wrong day", Toast.LENGTH_SHORT).show();
                } else
                    Toast.makeText(context, "wrong month", Toast.LENGTH_SHORT).show();
            } else
                Toast.makeText(context, "wrong year", Toast.LENGTH_SHORT).show();
        } catch (Exception exp) {
            Toast.makeText(context, "" + exp.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public void onTriggerAlarm(Event event, Calendar calendar, Context context) {

        String time[] = event.getTime().split(" ");
        String hour_min[] = time[0].split(":");
        try {
            if (Integer.parseInt(event.getFrom_year()) <= calendar.get(Calendar.YEAR) && Integer.parseInt(event.getTo_year()) >= calendar.get(Calendar.YEAR)) {
                if ((Integer.parseInt(event.getFrom_month()) - 1) <= calendar.get(Calendar.MONTH) && (Integer.parseInt(event.getTo_month()) - 1) >= calendar.get(Calendar.MONTH)) {
                    if (Integer.parseInt(event.getFrom_day()) <= calendar.get(Calendar.DATE) && Integer.parseInt(event.getTo_day()) >= calendar.get(Calendar.DATE))
                        if (Integer.parseInt(hour_min[0]) == calendar.get(Calendar.HOUR))
                            if (Integer.parseInt(hour_min[1]) == calendar.get(Calendar.MINUTE))
                                sendNotification(context, event.getId(), event.getTitle(), event.getDescription());
                            else
                                Toast.makeText(context, "wrong min", Toast.LENGTH_SHORT).show();
                        else
                            Toast.makeText(context, "wrong hour", Toast.LENGTH_SHORT).show();
                    else
                        Toast.makeText(context, "wrong day", Toast.LENGTH_SHORT).show();
                } else
                    Toast.makeText(context, "wrong month", Toast.LENGTH_SHORT).show();
            } else
                Toast.makeText(context, "wrong year", Toast.LENGTH_SHORT).show();
        } catch (Exception exp) {
            Toast.makeText(context, "" + exp.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public void sendNotification(Context context, int id, String title, String description) {
        Uri notifySound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(context, id + "")
                        .setSmallIcon(R.mipmap.ic_launcher_round)
                        .setContentTitle(title)
                        .setContentText("Note: " + description)
                        .setSound(notifySound);

        PendingIntent contentIntent = PendingIntent.getActivity(context, 0,
                new Intent(context, CalendarActivity.class), PendingIntent.FLAG_UPDATE_CURRENT);

        mBuilder.setContentIntent(contentIntent);

        NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        if (mNotificationManager != null) {
            mNotificationManager.notify(id, mBuilder.build());
        }
    }
}
