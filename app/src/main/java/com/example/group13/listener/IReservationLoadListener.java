package com.example.group13.listener;

import com.example.group13.model.Reservation;

import java.util.List;

public interface IReservationLoadListener {

    void onReservationLoadSuccess(List<Reservation> ReservationModelList);
    void onReservationLoadFailed(String message);
}
