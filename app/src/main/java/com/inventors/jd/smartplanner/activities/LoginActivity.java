package com.inventors.jd.smartplanner.activities;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ScaleDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.inventors.jd.smartplanner.R;
import com.inventors.jd.smartplanner.databases.UserDB;
import com.inventors.jd.smartplanner.models.User;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private GoogleSignInClient mGoogleSignInClient;

    private int RC_SIGN_IN = 1;

    private static final String TAG = "onCreate Login";

    private EditText edtEmail, edtPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edtEmail = findViewById(R.id.edtEmail);
        edtPassword = findViewById(R.id.edtPassword);

        if (android.os.Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            Drawable mailDrawable = ContextCompat.getDrawable(LoginActivity.this, R.drawable.mail_icon);
            mailDrawable.setBounds(0, 0, (int) (mailDrawable.getIntrinsicWidth() * 0.1),
                    (int) (mailDrawable.getIntrinsicHeight() * 0.1));
            ScaleDrawable sd = new ScaleDrawable(mailDrawable, Gravity.START, 1, 1);

            edtEmail.setCompoundDrawables(sd.getDrawable(), null, null, null);

            Drawable passDrawable = ContextCompat.getDrawable(LoginActivity.this, R.drawable.password_icon);
            passDrawable.setBounds(0, 0, (int) (passDrawable.getIntrinsicWidth() * 0.1),
                    (int) (passDrawable.getIntrinsicHeight() * 0.1));
            ScaleDrawable sd1 = new ScaleDrawable(passDrawable, 0, 1, 1);


            edtPassword.setCompoundDrawables(sd1.getDrawable(), null, null, null);
        }

        try {

            // Configure sign-in to request the user's ID, email address, and basic
//          profile. ID and basic profile are included in DEFAULT_SIGN_IN.
//            GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//                    .requestEmail()
//                    .build();
//            // Build a GoogleSignInClient with the options specified by gso.
//            mGoogleSignInClient = GoogleSignIn.getClient(LoginActivity.this, gso);
//
//            // Check for existing Google Sign In account, if the user is already signed in
////       the GoogleSignInAccount will be non-null.
//            GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
////        updateUI(account);
//
//            SignInButton signInButton = findViewById(R.id.btnGoogleSignIn);
//            signInButton.setSize(SignInButton.SIZE_STANDARD);
//            findViewById(R.id.btnGoogleSignIn).setOnClickListener(this);
        } catch (Exception exp) {
//            Toast.makeText(this, "Error: " + exp.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btnGoogleSignIn:
                signIn();
                break;
            // ...
        }
    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            String personName = account.getDisplayName();
//            String personPhotoUrl = account.getPhotoUrl().toString();
//            String email = account.getEmail();

            Toast.makeText(this, "Name: " + personName, Toast.LENGTH_SHORT).show();
            // Signed in successfully, show authenticated UI.
//            updateUI(account);
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w(TAG, "signInResult:failed code=" + e.getStatusCode());
//            updateUI(null);
        }
    }

    public void signInBtn(View view) {

//        int mail, pass;
//        mail = pass = 0;
//
//        if (edtEmail.getText().toString().equals(""))
//            edtEmail.setError("empty");
//        else if (Patterns.EMAIL_ADDRESS.matcher(edtEmail.getText()).matches())
//            mail = 1;
//        else
//            edtEmail.setError("invalid");
//
//        if (edtPassword.getText().toString().equals(""))
//            edtPassword.setError("empty");
//        else if (edtPassword.getText().toString().length() >= 5)
//            pass = 1;
//        else
//            edtPassword.setError("invalid");
//
//        if (mail == 1 && pass == 1)

        UserDB db = new UserDB(this);

        User user = db.onGetUser(edtEmail.getText().toString(), edtPassword.getText().toString());

//
        if (user == null) {
            Toast.makeText(this, "user not found!", Toast.LENGTH_SHORT).show();
        } else {
            startActivity(new Intent(this, CalendarActivity.class));
            Toast.makeText(this, "" + user.getName(), Toast.LENGTH_SHORT).show();
        }

    }

    public void signUpBtn(View view) {
        startActivity(new Intent(this, SignUpActivity.class));

    }


}
