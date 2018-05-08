package com.inventors.jd.smartplanner.activities;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ScaleDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Patterns;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.inventors.jd.smartplanner.R;
import com.inventors.jd.smartplanner.databases.UserDB;
import com.inventors.jd.smartplanner.models.User;

public class SignUpActivity extends AppCompatActivity {

    private EditText edtEmail, edtPassword, edtFirstName, edtLastName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        edtEmail = findViewById(R.id.edtEmail);
        edtPassword = findViewById(R.id.edtPassword);
        edtFirstName = findViewById(R.id.edtFirstName);
        edtLastName = findViewById(R.id.edtLastName);

        if (android.os.Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            Drawable mailDrawable = ContextCompat.getDrawable(SignUpActivity.this, R.drawable.mail_icon);
            mailDrawable.setBounds(0, 0, (int) (mailDrawable.getIntrinsicWidth() * 0.1),
                    (int) (mailDrawable.getIntrinsicHeight() * 0.1));
            ScaleDrawable sd = new ScaleDrawable(mailDrawable, Gravity.START, 1, 1);

            edtEmail.setCompoundDrawables(sd.getDrawable(), null, null, null);

            Drawable passDrawable = ContextCompat.getDrawable(SignUpActivity.this, R.drawable.password_icon);
            passDrawable.setBounds(0, 0, (int) (passDrawable.getIntrinsicWidth() * 0.1),
                    (int) (passDrawable.getIntrinsicHeight() * 0.1));
            ScaleDrawable sd1 = new ScaleDrawable(passDrawable, 0, 1, 1);


            edtPassword.setCompoundDrawables(sd1.getDrawable(), null, null, null);

            Drawable userDrawable = ContextCompat.getDrawable(SignUpActivity.this, R.drawable.user_icon);
            userDrawable.setBounds(0, 0, (int) (userDrawable.getIntrinsicWidth() * 0.1),
                    (int) (userDrawable.getIntrinsicHeight() * 0.1));
            ScaleDrawable sd2 = new ScaleDrawable(userDrawable, 0, 1, 1);


            edtFirstName.setCompoundDrawables(sd2.getDrawable(), null, null, null);
            edtLastName.setCompoundDrawables(sd2.getDrawable(), null, null, null);

        }
    }

    public void signUpBtn(View view) {

        int mail, pass, first, last;
        mail = pass = first = last = 0;

        if (edtEmail.getText().toString().equals(""))
            edtEmail.setError("empty");
        else if (Patterns.EMAIL_ADDRESS.matcher(edtEmail.getText()).matches())
            mail = 1;
        else
            edtEmail.setError("invalid");

        if (edtPassword.getText().toString().equals(""))
            edtPassword.setError("empty");
        else if (edtPassword.getText().toString().length() >= 5)
            pass = 1;
        else
            edtPassword.setError("invalid");

        if (edtPassword.getText().toString().equals(""))
            edtPassword.setError("empty");
        else if (edtPassword.getText().toString().length() >= 5)
            pass = 1;
        else
            edtPassword.setError("invalid");

        if (edtFirstName.getText().toString().equals(""))
            edtFirstName.setError("empty");
        else if (edtFirstName.getText().toString().length() >= 3)
            first = 1;
        else
            edtFirstName.setError("invalid");

        if (edtLastName.getText().toString().equals(""))
            edtLastName.setError("empty");
        else if (edtLastName.getText().toString().length() >= 3)
            last = 1;
        else
            edtLastName.setError("invalid");

        if (mail == 1 && pass == 1 && first == 1 && last == 1) {

            UserDB db = new UserDB(this);
            User user = new User(edtFirstName.getText().toString() + " " + edtLastName.getText().toString(), edtEmail.getText().toString(), edtPassword.getText().toString());


            if (db.onUserInsert(user) > -1) {
                Toast.makeText(this, "data inserted!", Toast.LENGTH_LONG).show();
                startActivity(new Intent(this, LoginActivity.class));
            } else {
                Toast.makeText(this, "some error!", Toast.LENGTH_LONG).show();
            }
        }
    }

    public void signInBtn(View view) {
        startActivity(new Intent(this, LoginActivity.class));

    }
}
