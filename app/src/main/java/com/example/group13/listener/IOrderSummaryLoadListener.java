package com.example.group13.listener;

import com.example.group13.model.OrderSummaryModel;

import java.util.List;

public interface IOrderSummaryLoadListener {
    void onOrderSummaryLoadSuccess(List<OrderSummaryModel> orderSummaryModelList);
    void onOrderSummaryLoadFailed(String message);
}
