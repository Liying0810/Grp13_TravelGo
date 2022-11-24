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
import com.example.group13.R;
import com.example.group13.listener.IOrderSummaryLoadListener;
import com.example.group13.listener.IOrderSummaryLoadListener;
import com.example.group13.model.OrderSummaryModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class OrderSummaryAdapter extends RecyclerView.Adapter<OrderSummaryAdapter.OrderSummaryViewHolder> {
    private Context context;
    private List<OrderSummaryModel> orderSummaryModelList;
    private IOrderSummaryLoadListener orderSummaryLoadListener;

    //Display item layout by using the View Holder
    @NonNull
    @Override
    public OrderSummaryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        return new OrderSummaryViewHolder(LayoutInflater.from(context)
                .inflate(R.layout.layout_order_summary,parent,false));
    }

    //Set the Order summary: orderId,image,name,price,quantity,totalPrice,time,date,
    //To the specific position by using the View Holder
    @Override
    public void onBindViewHolder(@NonNull OrderSummaryViewHolder holder, int position) {
        Glide.with(context)
                .load(orderSummaryModelList.get(position).getImage()) //Get image from the position
                .into(holder.imageView2);
        holder.txtfoodName.setText(new StringBuilder().append(orderSummaryModelList.get(position).getName()));
        holder.txtfoodPrice.setText(new StringBuilder("$").append(orderSummaryModelList.get(position).getPrice()));
        holder.txtfoodQty.setText(new StringBuilder("x").append(orderSummaryModelList.get(position).getQuantity()));
    }

    @Override
    public int getItemCount(){
        return orderSummaryModelList.size();
    }

    public class OrderSummaryViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.imageView2)
        ImageView imageView2;
        @BindView(R.id.txtfoodName)
        TextView txtfoodName;
        @BindView(R.id.txtfoodPrice)
        TextView txtfoodPrice;
        @BindView(R.id.txtfoodQty)
        TextView txtfoodQty;


        private Unbinder unbinder;

        public OrderSummaryViewHolder(@NonNull View itemView) {
            super(itemView);
            unbinder = ButterKnife.bind(this,itemView);
        }
    }

    //Constructor
    public OrderSummaryAdapter(Context context, List<OrderSummaryModel> orderSummaryModelList, IOrderSummaryLoadListener orderSummaryLoadListener) {
        this.context = context;
        this.orderSummaryModelList = orderSummaryModelList;
        this.orderSummaryLoadListener = orderSummaryLoadListener;
    }

}

