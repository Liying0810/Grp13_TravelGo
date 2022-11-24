package com.example.group13.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.group13.model.Cart;
import com.example.group13.R;
import com.example.group13.UpdateCartEvent;
import com.example.group13.model.Cart;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {

    private Context context;
    private List<Cart> cartModelList;
    private FirebaseAuth firebaseAuth;

    public CartAdapter(Context context, List<Cart> cartModelList) {
        this.context = context;
        this.cartModelList = cartModelList;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CartViewHolder(LayoutInflater.from(context)
                .inflate(R.layout.cart_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        Glide.with(context)
                .load(cartModelList.get(position).getImage()) //Get image from the position
                .into(holder.foodImage); //Set into the food Image
        holder.foodPrice.setText(new StringBuilder("RM ").append(cartModelList.get(position).getPrice()));
        holder.foodName.setText(new StringBuilder().append(cartModelList.get(position).getName()));
        holder.txtQuantity.setText(new StringBuilder().append(cartModelList.get(position).getQuantity()));

        holder.btnNegativeOne.setOnClickListener(view -> {
            minusCartItem(holder, cartModelList.get(position));
        });
        holder.btnPlusOne.setOnClickListener(view -> {
            plusCartItem(holder, cartModelList.get(position));
        });

        holder.btnDelete.setOnClickListener(view -> {
            AlertDialog dialog = new AlertDialog.Builder(context)
                    .setTitle("Delete item")
                    .setMessage("Do you want to delete the item?")
                    .setNegativeButton("CANCEL", (dialog1, which) -> dialog1.dismiss())
                    .setPositiveButton("OK", (dialog2, which) -> {

                        //Temp remove

                        deleteFoodFromFirebase(cartModelList.get(position));
                        notifyItemRemoved(position);
                        notifyItemRangeChanged(position, cartModelList.size());

                        dialog2.dismiss();
                    }).create();
            dialog.show();
        });

    }

    private void deleteFoodFromFirebase(Cart cart) {
        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseDatabase.getInstance("https://intea-delight-default-rtdb.asia-southeast1.firebasedatabase.app")
                .getReference("Users").child(firebaseAuth.getUid()).child("Cart") //Users->UID->Cart->Food
                .child(cart.getKey())
                .removeValue()
                .addOnSuccessListener(aVoid-> EventBus.getDefault().postSticky(new UpdateCartEvent()));
    }

    private void plusCartItem(CartViewHolder holder, Cart cart) {
        cart.setQuantity(cart.getQuantity() + 1);
        cart.setTotalPrice(cart.getQuantity()*Float.parseFloat(cart.getPrice()));

        holder.txtQuantity.setText(new StringBuilder().append(cart.getQuantity()));
        updateFoodFirebase(cart);

    }

    private void minusCartItem(CartViewHolder holder, Cart cart) {
        if(cart.getQuantity()>1)
        {

            cart.setQuantity(cart.getQuantity() - 1);
            cart.setTotalPrice(cart.getQuantity()*Float.parseFloat(cart.getPrice()));

            holder.txtQuantity.setText(new StringBuilder().append(cart.getQuantity()));

            updateFoodFirebase(cart);

        }
    }

    private void updateFoodFirebase(Cart cart) {
        firebaseAuth = FirebaseAuth.getInstance();

        FirebaseDatabase.getInstance("https://intea-delight-default-rtdb.asia-southeast1.firebasedatabase.app")
                .getReference("Users").child(firebaseAuth.getUid()).child("Cart")
                .child(cart.getKey())
                .setValue(cart)
                .addOnSuccessListener(aVoid-> EventBus.getDefault().postSticky(new UpdateCartEvent()));
    }

    @Override
    public int getItemCount() {
        return cartModelList.size();
    }

    public class CartViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.btnPlusOne)
        ImageButton btnPlusOne;
        @BindView(R.id.btnNegativeOne)
        ImageButton btnNegativeOne;
        @BindView(R.id.btnDelete)
        ImageButton btnDelete;
        @BindView(R.id.txtQuantity)
        TextView txtQuantity;
        @BindView(R.id.foodImage)
        ImageView foodImage;
        @BindView(R.id.foodName)
        TextView foodName;
        @BindView(R.id.foodPrice)
        TextView foodPrice;



        Unbinder unbinder;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            unbinder = ButterKnife.bind(this,itemView);
        }
    }

}
