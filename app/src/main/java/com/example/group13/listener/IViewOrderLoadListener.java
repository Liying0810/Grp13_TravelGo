package com.example.group13.listener;


import com.example.group13.model.ViewOrderModel;

import java.util.List;

public interface IViewOrderLoadListener {
    void onViewOrderLoadSuccess(List<ViewOrderModel> viewOrderModelList);
    void onViewOrderLoadFailed(String message);
}
