package com.example.group13.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.group13.listener.IFeedbackLoadListener;
import com.example.group13.model.FeedbackModel;
import com.example.group13.R;
import com.example.group13.listener.IFeedbackLoadListener;
import com.example.group13.model.FeedbackModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class FeedbackAdapter extends RecyclerView.Adapter<FeedbackAdapter.FeedbackViewHolder> {
    private Context context;
    private List<FeedbackModel> feedbackModelList;
    private IFeedbackLoadListener feedbackLoadListener;

    //Display item layout by using the View Holder
    @NonNull
    @Override
    public FeedbackViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        return new FeedbackViewHolder(LayoutInflater.from(context)
                .inflate(R.layout.layout_feedback,parent,false));
    }

    //Set the Order summary: orderId,image,name,price,quantity,totalPrice,time,date,
    //To the specific position by using the View Holder
    @Override
    public void onBindViewHolder(@NonNull FeedbackViewHolder holder, int position) {
       /* Glide.with(context)
                .load(feedbackModelList.get(position).getImage()) //Get image from the position
                .into(holder.imageView2); */
        holder.txtFeedback.setText(new StringBuilder().append(feedbackModelList.get(position).getFeedback()));
        holder.DateFeed.setText(new StringBuilder().append(feedbackModelList.get(position).getDate()));
        holder.TimeFeed.setText(new StringBuilder().append(feedbackModelList.get(position).getTime()));
        holder.FeedbackTitle.setText(new StringBuilder("Feedback for ").append(feedbackModelList.get(position).getFood()));

    }

    @Override
    public int getItemCount(){
        return feedbackModelList.size();
    }

    public class FeedbackViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.txtFeedback)
        TextView txtFeedback;
        @BindView(R.id.DateFeed)
        TextView DateFeed;
        @BindView(R.id.TimeFeed)
        TextView TimeFeed;
        @BindView(R.id.FeedbackTitle)
        TextView FeedbackTitle;

        private Unbinder unbinder;

        public FeedbackViewHolder(@NonNull View itemView) {
            super(itemView);
            unbinder = ButterKnife.bind(this,itemView);
        }
    }

    //Constructor
    public FeedbackAdapter(Context context, List<FeedbackModel> feedbackModelList, IFeedbackLoadListener feedbackLoadListener) {
        this.context = context;
        this.feedbackModelList = feedbackModelList;
        this.feedbackLoadListener = feedbackLoadListener;
    }

}
