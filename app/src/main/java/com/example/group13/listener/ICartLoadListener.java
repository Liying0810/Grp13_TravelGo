package com.example.group13.listener;

import com.example.group13.model.Cart;

import java.util.List;

public interface ICartLoadListener {
    void onCartLoadSuccess(List<Cart> cartModelList);
    void onCartLoadFailed(String message);
}
