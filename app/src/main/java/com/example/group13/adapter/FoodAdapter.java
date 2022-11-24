package com.example.group13.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.group13.listener.ICartLoadListener;
import com.example.group13.model.Food;
import com.example.group13.R;
import com.example.group13.UpdateCartEvent;
import com.example.group13.listener.IAddtoCartClickListener;
import com.example.group13.listener.ICartLoadListener;
import com.example.group13.model.Cart;
import com.example.group13.model.Food;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.FoodViewHolder>{

    private Context context;
    private List<Food> foodList;
    private ICartLoadListener cartLoadListener;
    private FirebaseAuth firebaseAuth;

    //Constructor
    public FoodAdapter(Context context, List<Food> foodList, ICartLoadListener cartLoadListener) {
        this.context = context;
        this.foodList = foodList;
        this.cartLoadListener = cartLoadListener;
    }

    //Display item layout by using the View Holder
    @NonNull
    @Override
    public FoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new FoodViewHolder(LayoutInflater.from(context).inflate(R.layout.food_item,parent,false));
    }


    //Set the Food details: Image, Name, Price - To the specific position by using the View Holder
    @Override
    public void onBindViewHolder(@NonNull FoodViewHolder holder, int position) {
        Glide.with(context)
                .load(foodList.get(position).getImage()) //Get image from the position
                .into(holder.foodImage); //Set into the food Image
        holder.foodPrice.setText(new StringBuilder("RM ").append(foodList.get(position).getPrice()));
        holder.foodName.setText(new StringBuilder().append(foodList.get(position).getName()));
        holder.setListener((view, adapterPosition) -> {
            addToCart(foodList.get(position));
        });

    }

    private void addToCart(Food foodModel) {

        firebaseAuth = FirebaseAuth.getInstance();

        DatabaseReference userCart = FirebaseDatabase.getInstance("https://group13-travelgo-default-rtdb.firebaseio.com/")
                .getReference("Users").child(firebaseAuth.getUid()).child("Cart");

        userCart.child(foodModel.getKey())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.exists())
                        {
                            //Update quantity and totalPrice
                            Cart cartModel = snapshot.getValue(Cart.class);
                            cartModel.setQuantity(cartModel.getQuantity()+1);
                            Map<String, Object> updateData = new HashMap<>();
                            updateData.put("quantity", cartModel.getQuantity());
                            updateData.put("totalPrice", cartModel.getQuantity()*Float.parseFloat(cartModel.getPrice()));

                            userCart.child(foodModel.getKey())
                                    .updateChildren(updateData)
                                    .addOnSuccessListener(aVoid -> {
                                        cartLoadListener.onCartLoadFailed("Add To Cart Success");
                                    })
                            .addOnFailureListener(e -> cartLoadListener.onCartLoadFailed(e.getMessage()));
                        }
                        else // If item not have in cart, add new
                        {
                            Cart cartModel = new Cart();
                            cartModel.setName(foodModel.getName());
                            cartModel.setImage(foodModel.getImage());
                            cartModel.setQuantity(1);
                            cartModel.setKey(cartModel.getKey());
                            cartModel.setPrice(foodModel.getPrice());
                            cartModel.setTotalPrice(Float.parseFloat(foodModel.getPrice()));

                            userCart.child(foodModel.getKey())
                                    .setValue(cartModel)
                                    .addOnSuccessListener(aVoid -> {
                                        cartLoadListener.onCartLoadFailed("Add To Cart Success");
                                    })
                                    .addOnFailureListener(e -> cartLoadListener.onCartLoadFailed(e.getMessage()));
                        }
                        EventBus.getDefault().postSticky(new UpdateCartEvent());
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        cartLoadListener.onCartLoadFailed(error.getMessage());
                    }
                });
    }

    @Override
    public int getItemCount() {
        return foodList.size();
    }

    public class FoodViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.foodImage)
        ImageView foodImage;
        @BindView(R.id.foodName)
        TextView foodName;
        @BindView(R.id.foodPrice)
        TextView foodPrice;

        IAddtoCartClickListener listener;

        public void setListener(IAddtoCartClickListener listener) {
            this.listener = listener;
        }

        private Unbinder unbinder;

        public FoodViewHolder(@NonNull View itemView) {
            super(itemView);
            unbinder = ButterKnife.bind(this,itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            listener.onAddtoCartClick(view,getAdapterPosition());
        }
    }
}
