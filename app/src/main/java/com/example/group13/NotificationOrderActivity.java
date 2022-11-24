package com.example.group13;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class NotificationOrderActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_order);

        TextView textOrder = findViewById(R.id.text_Order);

        String message = getIntent().getStringExtra("message");
        textOrder.setText(message);
    }
}