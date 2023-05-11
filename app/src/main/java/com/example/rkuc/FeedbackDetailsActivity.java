package com.example.rkuc;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.rkuc.databinding.ActivityFeedbackDetailsBinding;

public class FeedbackDetailsActivity extends AppCompatActivity {
ActivityFeedbackDetailsBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding =ActivityFeedbackDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        String FeedbackTitle = getIntent().getStringExtra("FeedbackTitle");
        String FeedbackDetail = getIntent().getStringExtra("FeedbackDetail");
        String FeedbackDate =getIntent().getStringExtra("FeedbackDate");

        binding.TextViewFeedBackTitle.setText(FeedbackTitle);
        binding.TextViewFeedBackDateTime.setText(FeedbackDate);
        binding.TextViewFeedBackDescription.setText(FeedbackDetail);


    }
}