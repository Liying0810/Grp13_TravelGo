package com.example.group13;

import android.accounts.Account;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class changePassword extends AppCompatActivity implements View.OnClickListener {

    //Button forgotPass;
    Button changePass;
    EditText newPass, confirmPass, currentPass;
    ProgressDialog dialog;
    String passwordData;
    private Button account1,home1, orderHistory1, cart1;
    ImageButton Back_Button;

    FirebaseAuth firebaseAuth;
    DatabaseReference reference;
    FirebaseUser user;

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

            case R.id.Back_Button:
                Intent backToProfile = new Intent(this, AccountActivity.class);
                startActivity(backToProfile);
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.change_password);

        account1 = findViewById(R.id.account1);
        account1.setOnClickListener(this);

        home1 = findViewById(R.id.home1);
        home1.setOnClickListener(this);

        orderHistory1 = findViewById(R.id.orderHistory1);
        orderHistory1.setOnClickListener(this);

        cart1 = findViewById(R.id.cart1);
        cart1.setOnClickListener(this);

        Back_Button = findViewById(R.id.Back_Button);
        Back_Button.setOnClickListener(this);

        dialog = new ProgressDialog(this);

        newPass = (EditText) findViewById(R.id.new_password);
        confirmPass = (EditText) findViewById(R.id.confirm_password);
        currentPass = (EditText) findViewById(R.id.etCurrent_password);
        changePass = findViewById(R.id.btnConfirmChangePass);


        /*forgotPass = findViewById(R.id.btnForgotPass);

         forgotPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(changePassword.this, ForgotPasswordActivity.class));
            }
        }); */

        firebaseAuth = FirebaseAuth.getInstance();
        reference = FirebaseDatabase.getInstance("https://group13-travelgo-default-rtdb.firebaseio.com/")
                .getReference("Password").child(firebaseAuth.getUid());

        changePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                            passwordData = dataSnapshot.getValue().toString();

                        }
                        onPasswordChange(passwordData);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            } //Onclick
            public void onPasswordChange(String passwordData) {
                user = firebaseAuth.getCurrentUser();
                dialog.setMessage("Changing password, Please wait");
                if (passwordData.equals(currentPass.getText().toString())) {
                    if (newPass.getText().toString().equals(confirmPass.getText().toString()) && newPass.getText().toString().isEmpty() == false) {
                        dialog.show();
                        user.updatePassword(newPass.getText().toString())
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            dialog.dismiss();
                                            Toast.makeText(changePassword.this,
                                                    "Password successfully changed.", Toast.LENGTH_LONG).show();
                                            firebaseAuth.signOut();
                                            finish();
                                            Intent i = new Intent(changePassword.this, LoginActivity.class);
                                            startActivity(i);
                                        } else {
                                            dialog.dismiss();
                                            Toast.makeText(changePassword.this,
                                                    "Password must be at least 6 characters", Toast.LENGTH_LONG).show();
                                        }
                                    }
                                });
                    } else if (newPass.getText().toString().isEmpty() && confirmPass.getText().toString().isEmpty()) {
                        dialog.dismiss();
                        Toast.makeText(changePassword.this,
                                "Please enter your New Password.", Toast.LENGTH_LONG).show();
                    } else if (newPass.getText().toString().isEmpty()) {
                        dialog.dismiss();
                        Toast.makeText(changePassword.this,
                                "Please enter your New Password.", Toast.LENGTH_LONG).show();
                    } else if (confirmPass.getText().toString().isEmpty()) {
                        dialog.dismiss();
                        Toast.makeText(changePassword.this,
                                "Please confirm your New Password.", Toast.LENGTH_LONG).show();
                    } else {
                        dialog.dismiss();
                        Toast.makeText(changePassword.this,
                                "Password not matched.", Toast.LENGTH_LONG).show();
                    }
                }   else if (currentPass.getText().toString().isEmpty()){
                        Toast.makeText(changePassword.this,
                                "Please enter your Current Password.", Toast.LENGTH_LONG).show();
                    } else {
                    Toast.makeText(changePassword.this,
                            "Current Password Incorrect.", Toast.LENGTH_LONG).show();
                }
            }
        });

    } //onCreate
} //End class