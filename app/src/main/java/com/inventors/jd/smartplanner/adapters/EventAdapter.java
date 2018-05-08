package com.inventors.jd.smartplanner.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.view.menu.MenuPopupHelper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.inventors.jd.smartplanner.R;
import com.inventors.jd.smartplanner.activities.AlarmActivity;
import com.inventors.jd.smartplanner.activities.CalendarActivity;
import com.inventors.jd.smartplanner.activities.PreviewEventActivity;
import com.inventors.jd.smartplanner.databases.EventDB;
import com.inventors.jd.smartplanner.models.Event;

import java.util.ArrayList;

/**
 * Created by jd on 20-Apr-18.
 */

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.ViewHolder> {

    private Context context;
    private ArrayList<Event> eventList;

    public EventAdapter(Context context, ArrayList<Event> eventList) {
        this.context = context;
        this.eventList = eventList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.new_list_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final Event event = eventList.get(position);

        holder.txtTitle.setText(event.getTitle());
        holder.txtDescription.setText(event.getDescription());
//        holder.txtDate.setText(event.getDay() + " " + getMonthName(event.getMonth()));// + "-" + event.getYear());
        holder.txtTime.setText(event.getTime());
        holder.txtLocation.setText(event.getLocation());

        holder.btnOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onMenuBtn(view, event, position);
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, PreviewEventActivity.class);

                intent.putExtra("title", event.getTitle());
                intent.putExtra("description", event.getDescription());
                String time[] = event.getTime().split(" ");
                intent.putExtra("time", time[0]);
                intent.putExtra("am_pm", time[1]);
                intent.putExtra("location", event.getLocation());
//                intent.putExtra("date", event.getDay() + "-" + event.getMonth() + "-" + event.getYear());

                context.startActivity(intent);
            }
        });
    }

    public String getMonthName(String month) {
        switch (month) {
            case "01":
                return "Jan";
            case "02":
                return "Feb";
            case "03":
                return "May";
            case "04":
                return "Apr";
            case "05":
                return "May";
            case "06":
                return "Jun";
            case "07":
                return "Jul";
            case "08":
                return "Aug";
            case "09":
                return "Sept";
            case "10":
                return "Oct";
            case "11":
                return "Nov";
            case "12":
                return "Dec";
            default:
                return "";
        }
    }

    @Override
    public int getItemCount() {
        return eventList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView txtTitle, txtDescription, txtTime, txtDate, txtLocation;
        ImageView btnOption;

        public ViewHolder(View itemView) {
            super(itemView);

            txtTitle = itemView.findViewById(R.id.txtTitle);
            txtDescription = itemView.findViewById(R.id.txtDescription);
            txtTime = itemView.findViewById(R.id.txtTime);
            txtDate = itemView.findViewById(R.id.txtDate);
            txtLocation = itemView.findViewById(R.id.txtLocation);

            btnOption = itemView.findViewById(R.id.btnOption);
        }
    }

    @SuppressLint("RestrictedApi")
    public void onMenuBtn(View view, final Event event, final int position) {
        MenuBuilder menuBuilder = new MenuBuilder(context);
        MenuInflater inflater = new MenuInflater(context);
        inflater.inflate(R.menu.list_menu, menuBuilder);
        MenuPopupHelper optionsMenu = new MenuPopupHelper(context, menuBuilder, view);
        optionsMenu.setForceShowIcon(true);

        menuBuilder.setCallback(new MenuBuilder.Callback() {
            @Override
            public boolean onMenuItemSelected(MenuBuilder menu, MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.opt_edit:
//                        Toast.makeText(context, "edit", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(context, AlarmActivity.class);

                        intent.putExtra("id", event.getId());
                        intent.putExtra("title", event.getTitle());
                        intent.putExtra("description", event.getDescription());
                        String time[] = event.getTime().split(" ");
                        intent.putExtra("time", time[0]);
                        intent.putExtra("am_pm", time[1]);
                        intent.putExtra("location", event.getLocation());
//                        intent.putExtra("date", event.getDay() + "-" + event.getMonth() + "-" + event.getYear());

                        context.startActivity(intent);

                        break;
                    case R.id.opt_delete:
                        AlertDialog.Builder builder;
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            builder = new AlertDialog.Builder(context, android.R.style.Theme_Material_Dialog_Alert);
                        } else {
                            builder = new AlertDialog.Builder(context);
                        }
                        builder.setTitle("Delete entry")
                                .setMessage("Are you sure you want to delete this EVENT?")
                                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        EventDB db = new EventDB(context);
                                        if (db.onDeleteEvent(event.getId()) == 1) {
                                            Toast.makeText(context, "Deleted", Toast.LENGTH_SHORT).show();
                                            eventList.remove(position);
                                            notifyDataSetChanged();
                                            CalendarActivity.onSetEventPoints(context);
                                        }
                                    }
                                })
                                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        // do nothing
                                    }
                                })
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .show();
                        break;
                }
                return false;
            }

            @Override
            public void onMenuModeChange(MenuBuilder menu) {
            }
        });

        optionsMenu.show();
    }
}
