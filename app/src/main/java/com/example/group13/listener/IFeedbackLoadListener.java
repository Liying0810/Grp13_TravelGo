package com.example.group13.listener;


import com.example.group13.model.FeedbackModel;

import java.util.List;

public interface IFeedbackLoadListener {
    void onFeedbackLoadSuccess(List<FeedbackModel> feedbackModelList);
    void onFeedbackLoadFailed(String message);
}
