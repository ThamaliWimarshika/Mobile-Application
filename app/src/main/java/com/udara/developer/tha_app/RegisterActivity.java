package com.udara.developer.tha_app;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {
    EditText etName, etIndex, etEmail, etMobile, etGPA, etPassword, etRePassword;
    Button btnSubmit;

    DatabaseHandler databaseHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        databaseHandler = new DatabaseHandler(RegisterActivity.this);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etName = findViewById(R.id.et_name);
        etIndex = findViewById(R.id.et_index);
        etEmail = findViewById(R.id.et_email);
        etMobile = findViewById(R.id.et_mobile);
        etGPA = findViewById(R.id.et_gpa);
        etPassword = findViewById(R.id.et_password);
        etRePassword = findViewById(R.id.et_re_password);

        btnSubmit = findViewById(R.id.btn_submit);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isValid()) {
                    final ProgressDialog progressDialog = new ProgressDialog(RegisterActivity.this);
                    progressDialog.setMessage("Signing up...");
                    progressDialog.setCancelable(false);
                    progressDialog.show();
                    Handler handler = new Handler(Looper.getMainLooper());
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            progressDialog.dismiss();
                            if (databaseHandler.register(
                                    etName.getText().toString(),
                                    etIndex.getText().toString(),
                                    etEmail.getText().toString(),
                                    etMobile.getText().toString(),
                                    etGPA.getText().toString(),
                                    etPassword.getText().toString()
                            )) {
                                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                            } else {
                                new AlertDialog.Builder(RegisterActivity.this)
                                        .setTitle("Failed")
                                        .setMessage("Error signing up")
                                        .setPositiveButton("Close", null)
                                        .show();
                            }
                        }
                    }, 2000);
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        databaseHandler.close();
        super.onDestroy();
    }

    private boolean isValid() {
        String name = etName.getText().toString(),
                index = etIndex.getText().toString(),
                email = etEmail.getText().toString(),
                mobile = etMobile.getText().toString(),
                gpa = etGPA.getText().toString(),
                password = etPassword.getText().toString(),
                rePassword = etRePassword.getText().toString();

        if (name.isEmpty() || index.isEmpty() || email.isEmpty() || mobile.isEmpty() || gpa.isEmpty() || password.isEmpty() || rePassword.isEmpty()) {
            Toast.makeText(this, "Fields cannot be empty", Toast.LENGTH_SHORT).show();
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).find()) {
            Toast.makeText(this, "Email address is not valid", Toast.LENGTH_SHORT).show();
        } else if (!(Pattern.compile("[0-9]").matcher(mobile).find() && mobile.length() == 10)) {
            Toast.makeText(this, "Mobile number is invalid", Toast.LENGTH_SHORT).show();
        } else if (!Pattern.compile("^16[0-9]{4}[A-Z]$").matcher(index).find()) {
            Toast.makeText(this, "Invalid index number", Toast.LENGTH_SHORT).show();
        } else if (!password.equals(rePassword)) {
            Toast.makeText(this, "Passwords doesn't match", Toast.LENGTH_SHORT).show();
        } else {
            return true;
        }
        return false;
    }
}
