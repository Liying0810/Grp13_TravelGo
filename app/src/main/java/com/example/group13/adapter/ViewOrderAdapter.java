package com.example.group13.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.group13.R;
import com.example.group13.listener.IViewOrderLoadListener;
import com.example.group13.listener.IViewOrderLoadListener;
import com.example.group13.listener.RecyclerViewClickInterface;
import com.example.group13.listener.RecyclerViewClickInterface;
import com.example.group13.model.ViewOrderModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class ViewOrderAdapter extends RecyclerView.Adapter<ViewOrderAdapter.ViewOrderViewHolder> {

    private Context context;
    private List<ViewOrderModel> viewOrderModelList;
    private IViewOrderLoadListener viewOrderLoadListener;
    private RecyclerViewClickInterface recyclerViewClickInterface;

    //Constructor
    public ViewOrderAdapter(Context context, List<ViewOrderModel> viewOrderModelList, IViewOrderLoadListener viewOrderLoadListener, RecyclerViewClickInterface recyclerViewClickInterface) {
        this.context = context;
        this.viewOrderModelList = viewOrderModelList;
        this.viewOrderLoadListener = viewOrderLoadListener;
        this.recyclerViewClickInterface = recyclerViewClickInterface;
    }

    //Display item layout by using the View Holder
    @NonNull
    @Override
    public ViewOrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewOrderViewHolder(LayoutInflater.from(context)
                .inflate(R.layout.layout_view_order, parent, false));
    }

    //Set the Order details: OrderID, Date, Price - To the specific position by using the View Holder
    @Override
    public void onBindViewHolder(@NonNull ViewOrderViewHolder holder, int position) {
        /* Glide.with(context)
                .load(viewOrderModelList.get(position).getImage()) //Get image from the position
                .into(holder.imageView1); */
        holder.txtOrderID.setText(new StringBuilder("Order #").append(viewOrderModelList.get(position).getOrderId()));
        holder.txtDate.setText(new StringBuilder().append(viewOrderModelList.get(position).getDate()));
        holder.txtTime.setText(new StringBuilder().append(viewOrderModelList.get(position).getTime()));
    }

    @Override
    public int getItemCount() {
        return viewOrderModelList.size();
    }

    public class ViewOrderViewHolder extends RecyclerView.ViewHolder {
        //@BindView(R.id.imageView)
        //ImageView imageView1;
        @BindView(R.id.txtOrderID)
        TextView txtOrderID;
        @BindView(R.id.txtDate)
        TextView txtDate;
        @BindView(R.id.txtTime)
        TextView txtTime;

        private Unbinder unbinder;

        public ViewOrderViewHolder(@NonNull View itemView) {
            super(itemView);
            unbinder = ButterKnife.bind(this, itemView);

            itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view){
                    recyclerViewClickInterface.onItemClick(getAdapterPosition());
                }
            });
        }
    }
}
