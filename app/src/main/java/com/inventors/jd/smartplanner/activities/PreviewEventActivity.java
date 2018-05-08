package com.inventors.jd.smartplanner.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.inventors.jd.smartplanner.R;

import java.util.ArrayList;
import java.util.List;

public class PreviewEventActivity extends AppCompatActivity {

    private TextView txtDate, txtTime, txtLocation, txtAM_PM;
    private EditText edtTitle, edtDescription;

    private Spinner spnCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview_event);

        spnCategory = findViewById(R.id.spnCategory);

        List<String> categories = new ArrayList<>();
        categories.add("Once");
        categories.add("Daily");
        categories.add("Weekly");
        categories.add("Yearly");
        categories.add("So on");
        categories.add("Custom");

        ArrayAdapter adapter1 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, categories);

        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spnCategory.setAdapter(adapter1);
        spnCategory.setEnabled(false);

        txtDate = findViewById(R.id.date_or_days);
        txtTime = findViewById(R.id.txtTime);

        txtAM_PM = findViewById(R.id.txtAM_PM);

        edtTitle = findViewById(R.id.edtTitle);
        edtDescription = findViewById(R.id.edtDescription);
        txtLocation = findViewById(R.id.edtLocation);

        try {
            if (!getIntent().getExtras().getString("date").equals("")) {
                edtTitle.setText(getIntent().getExtras().getString("title"));
                edtDescription.setText(getIntent().getExtras().getString("description"));
                txtTime.setText(getIntent().getExtras().getString("time"));
                txtAM_PM.setText(getIntent().getExtras().getString("am_pm"));
                txtLocation.setText(getIntent().getExtras().getString("location"));
                txtDate.setText(getIntent().getExtras().getString("date"));
            }
        } catch (Exception exp) {
            Toast.makeText(this, "Error: " + exp.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }
}
