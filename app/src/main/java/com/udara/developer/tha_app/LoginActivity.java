package com.udara.developer.tha_app;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity {
    private EditText etIndex, etPassword;

    DatabaseHandler databaseHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        databaseHandler = new DatabaseHandler(this);

        etIndex = findViewById(R.id.et_index);
        etPassword = findViewById(R.id.et_password);
        Button btnLogin = findViewById(R.id.btn_login);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this);
                progressDialog.setMessage("Logging you in...");
                progressDialog.setCancelable(false);
                progressDialog.show();
                Handler handler = new Handler(Looper.getMainLooper());
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        progressDialog.dismiss();
                        UserProfile userProfile = databaseHandler.login(etIndex.getText().toString(), etPassword.getText().toString());
                        if (userProfile != null) {
                            Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
                            intent.putExtra("name", userProfile.name);
                            intent.putExtra("index", userProfile.index);
                            intent.putExtra("email", userProfile.email);
                            intent.putExtra("mobile", userProfile.mobile);
                            intent.putExtra("gpa", userProfile.gpa);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        } else {
                            new AlertDialog.Builder(LoginActivity.this)
                                    .setTitle("Failed")
                                    .setMessage("Invalid username or password")
                                    .setPositiveButton("Close", null)
                                    .show();
                        }
                    }
                }, 2000);
            }
        });
    }

    @Override
    protected void onDestroy() {
        databaseHandler.close();
        super.onDestroy();
    }
}
