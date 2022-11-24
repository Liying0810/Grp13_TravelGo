package com.example.group13;

import android.accounts.Account;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.group13.adapter.FoodAdapter;
import com.example.group13.listener.ICartLoadListener;
import com.example.group13.listener.IFoodLoadListener;
import com.example.group13.model.Cart;
import com.example.group13.model.Food;
import com.example.group13.utils.SpaceItemDecoration;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class food_page extends AppCompatActivity implements IFoodLoadListener, ICartLoadListener, View.OnClickListener{
    private Button account1,home1,orderHistory1;
    private ImageView btnBack;
    private FirebaseAuth firebaseAuth;

    @BindView(R.id.foodListRecycler)
    RecyclerView foodListRecycler;
    @BindView(R.id.food_layout)
    RelativeLayout food_layout;
    @BindView(R.id.cart1)
    Button cart1;

    IFoodLoadListener foodLoadListener;
    ICartLoadListener cartLoadListener;

    protected void onStart(){
        super.onStart();
        EventBus.getDefault().register(this);
    }

    protected void onStop(){
        if(EventBus.getDefault().hasSubscriberForEvent(UpdateCartEvent.class));
            EventBus.getDefault().removeStickyEvent(UpdateCartEvent.class);
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    @Subscribe(threadMode = ThreadMode.MAIN,sticky = true)
    public void onUpdateCart(UpdateCartEvent event)
    {
        countCartItem();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.food_main_page);

        btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(this);

        account1 = findViewById(R.id.account1);
        account1.setOnClickListener(this);

        home1 = findViewById(R.id.home1);
        home1.setOnClickListener(this);

        orderHistory1= findViewById(R.id.orderHistory1);
        orderHistory1.setOnClickListener(this);

        cart1= findViewById(R.id.cart1);
        cart1.setOnClickListener(this);
        
        init();
        loadFoodFromFirebase();
        //countCartItem();
    }

    private void init(){
        ButterKnife.bind(this);

        foodLoadListener = this;
        cartLoadListener = this;

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        foodListRecycler.setLayoutManager(linearLayoutManager);
        foodListRecycler.addItemDecoration(new SpaceItemDecoration());

        cart1.setOnClickListener(view -> startActivity(new Intent(this, CartActivity.class)));
    }

    //Read All Food from Firebase
    private void loadFoodFromFirebase() {
        List<Food> foodModels = new ArrayList<>();

        FirebaseDatabase database = FirebaseDatabase.getInstance("https://intea-delight-default-rtdb.asia-southeast1.firebasedatabase.app");
        DatabaseReference reference = database.getReference("Food");

            reference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if(snapshot.exists()){
                        for(DataSnapshot foodSnapshot:snapshot.getChildren())
                        {
                            Food foodModel = foodSnapshot.getValue(Food.class);
                            foodModel.setKey(foodSnapshot.getKey());
                            foodModels.add(foodModel);
                        }
                        foodLoadListener.onFoodLoadSuccess(foodModels);
                    }
                    else
                        foodLoadListener.onFoodLoadFailed("Can't find the Rice");
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    foodLoadListener.onFoodLoadFailed(error.getMessage());
                }
            });
    }

    @Override
    public void onFoodLoadSuccess(List<Food> foodModelList) {
        FoodAdapter adapter = new FoodAdapter(this, foodModelList, cartLoadListener);
        foodListRecycler.setAdapter(adapter);
    }

    @Override
    public void onFoodLoadFailed(String message) {
        Toast.makeText(food_page.this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCartLoadSuccess(List<Cart> cartModelList) {

        int cartSum = 0;
        for(Cart cartModel: cartModelList)
            cartSum+=cartModel.getQuantity();

    }

    @Override
    public void onCartLoadFailed(String message) {
        Toast.makeText(food_page.this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onResume(){
        super.onResume();
        countCartItem();
    }

    private void countCartItem() {

        firebaseAuth = FirebaseAuth.getInstance();
        List<Cart> cartModels = new ArrayList<>();
        FirebaseDatabase.getInstance("https://group13-travelgo-default-rtdb.firebaseio.com/")
                .getReference("Users")
                .child(firebaseAuth.getUid()).child("Cart")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for(DataSnapshot cartSnapshot:snapshot.getChildren())
                        {
                            Cart cartModel = cartSnapshot.getValue(Cart.class);
                            cartModel.setKey(cartSnapshot.getKey());
                            cartModels.add(cartModel);
                        }
                        cartLoadListener.onCartLoadSuccess(cartModels);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        cartLoadListener.onCartLoadFailed(error.getMessage());
                    }
                });
    }

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

}