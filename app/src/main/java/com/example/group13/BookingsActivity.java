package com.example.group13;

import androidx.appcompat.app.AppCompatActivity;

import android.accounts.Account;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class BookingsActivity extends AppCompatActivity implements View.OnClickListener {

    private Button account1, home1, orderHistory1, cart1, btnCompletedOrder, feedback_button;

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.account1:
                Intent toLogin = new Intent(this, AccountActivity.class);
                startActivity(toLogin);
                break;
            case R.id.home1:
                Intent toLogin1 = new Intent(this, HomeActivity.class);
                startActivity(toLogin1);
                break;
            case R.id.orderHistory1:
                Intent toLogin2 = new Intent(this, BookingsActivity.class);
                startActivity(toLogin2);
                break;

            case R.id.cart1:
                Intent toLogin3 = new Intent(this, CartActivity.class);
                startActivity(toLogin3);
                break;



        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookings);

        account1 = findViewById(R.id.account1);
        account1.setOnClickListener(this);

        home1 = findViewById(R.id.home1);
        home1.setOnClickListener(this);

        orderHistory1 = findViewById(R.id.orderHistory1);
        orderHistory1.setOnClickListener(this);

        cart1 = findViewById(R.id.cart1);
        cart1.setOnClickListener(this);

        btnCompletedOrder = findViewById(R.id.btnCompletedOrder);
        btnCompletedOrder.setOnClickListener(this);

        feedback_button = findViewById(R.id.feedback_button);
        feedback_button.setOnClickListener(this);
    }

}