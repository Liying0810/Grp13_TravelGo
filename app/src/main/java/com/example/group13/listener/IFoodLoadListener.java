package com.example.group13.listener;

import com.example.group13.model.Food;

import java.util.List;

public interface IFoodLoadListener {
    void onFoodLoadSuccess(List<Food> foodModelList);
    void onFoodLoadFailed(String message);
}
