package com.example.rkuc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.rkuc.databinding.ActivityNewFeedbackBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class NewFeedbackActivity extends AppCompatActivity {
    ActivityNewFeedbackBinding binding;
    FirebaseFirestore firebaseFirestore;
    String Date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityNewFeedbackBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        firebaseFirestore = FirebaseFirestore.getInstance();
        String DocID = getIntent().getStringExtra("DocID");

        Date = getTodaysDate().toString();
        binding.NewFeedBackButtonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String feedbackTitle = binding.NewFeedBackTextBoxFeedbackTitle.getText().toString();
                String feedbackDetail = binding.NewFeedBackTextBoxFeedbackDescription.getText().toString();
                if (feedbackTitle.length() == 0) {
                    Toast.makeText(NewFeedbackActivity.this, "Enter Your Feedback Title", Toast.LENGTH_SHORT).show();
                } else if (feedbackDetail.length() == 0) {
                    Toast.makeText(NewFeedbackActivity.this, "Enter Your Feedback Detail", Toast.LENGTH_SHORT).show();
                } else {
                    firebaseFirestore.collection("Complaints").document(DocID).update("feedbackDate", Date);
                    firebaseFirestore.collection("Complaints").document(DocID).update("feedbackDeitail", feedbackDetail);
                    firebaseFirestore.collection("Complaints").document(DocID).update("feedbackTitle", feedbackTitle).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Toast.makeText(NewFeedbackActivity.this, "Feedback Added Successfully", Toast.LENGTH_SHORT).show();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(NewFeedbackActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });

                }
            }
        });
    }

    private String getTodaysDate() {
        return new SimpleDateFormat("yyyy/MM/dd", Locale.getDefault()).format(new Date());
    }
}