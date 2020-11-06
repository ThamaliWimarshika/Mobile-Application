package com.udara.developer.tha_app;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ProfileActivity extends AppCompatActivity {
    TextView tvName, tvIndex, tvEmail, tvMobile, tvGPA;
    Button btnExit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        tvName = findViewById(R.id.et_name);
        tvIndex = findViewById(R.id.et_index);
        tvEmail = findViewById(R.id.et_email);
        tvMobile = findViewById(R.id.et_mobile);
        tvGPA = findViewById(R.id.et_gpa);
        btnExit = findViewById(R.id.btn_exit);

        Intent intent = getIntent();

        tvName.setText(intent.getStringExtra("name"));
        tvIndex.setText(intent.getStringExtra("index"));
        tvEmail.setText(intent.getStringExtra("email"));
        tvMobile.setText(intent.getStringExtra("mobile"));
        tvGPA.setText(intent.getStringExtra("gpa"));

        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
