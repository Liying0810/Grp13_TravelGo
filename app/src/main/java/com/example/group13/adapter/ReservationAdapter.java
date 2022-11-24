package com.example.group13.adapter;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.group13.R;
import com.example.group13.model.Reservation;
import com.example.group13.model.Reservation;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public class ReservationAdapter extends RecyclerView.Adapter<ReservationAdapter.ReservationViewHolder>{
    Context context;
    List<Reservation> reservationList;
    private FirebaseAuth firebaseAuth;

    //constructor
    public ReservationAdapter(Context context, List<Reservation> reservationList){
        this.context=context;
        this.reservationList=reservationList;
    }

    //Display item layout by using the View Holder
    @NonNull
    @Override
    public ReservationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ReservationViewHolder(LayoutInflater.from(context).inflate(R.layout.reservation_item,parent,false));
    }

    //set the rice detail
    @Override
    public void onBindViewHolder(@NonNull ReservationViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return reservationList.size();
    }

    public class ReservationViewHolder extends RecyclerView.ViewHolder {
        private Unbinder unbinder;



        public ReservationViewHolder(@NonNull View itemView) {
            super(itemView);
            unbinder = ButterKnife.bind(this,itemView);
        }
    }
}
