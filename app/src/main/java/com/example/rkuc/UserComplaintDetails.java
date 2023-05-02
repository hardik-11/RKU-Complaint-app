package com.example.rkuc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.rkuc.databinding.ActivityUserComplaintDetailsBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

public class UserComplaintDetails extends AppCompatActivity {

    ActivityUserComplaintDetailsBinding binding;
    FirebaseFirestore firebaseFirestore;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUserComplaintDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        firebaseFirestore = FirebaseFirestore.getInstance();

        String GetType = getIntent().getStringExtra("Type");
        binding.UserCompDetailsTextHeaderTitle.setText(GetType + " Complaint");
        binding.UserCompdetailsDate.setText(getIntent().getStringExtra("Date"));
        binding.UserCompdetailsDescription.setText(getIntent().getStringExtra("Description"));
        binding.UserCompdetailsTitle.setText(getIntent().getStringExtra("Title"));
        String DocID = getIntent().getStringExtra("DocID");
        String Status = getIntent().getStringExtra("Status");
        if (Status.equals("Pending") ) {
            binding.UserCompStatusImage.setImageResource(R.drawable.pendingstatus);
            binding.FeedBackLayout.setVisibility(View.GONE);
            binding.NotApprovedLayout.setVisibility(View.GONE);
            binding.ButtonsLayout.setVisibility(View.VISIBLE);
        }else  if (Status.equals("Cancel") ) {
            binding.UserCompStatusImage.setImageResource(R.drawable.cancelstatus);
            binding.FeedBackLayout.setVisibility(View.GONE);
            binding.NotApprovedLayout.setVisibility(View.VISIBLE);
            binding.ButtonsLayout.setVisibility(View.GONE);
        }else if (Status.equals("Success") ) {
            binding.UserCompStatusImage.setImageResource(R.drawable.success);
            binding.FeedBackLayout.setVisibility(View.VISIBLE);
            binding.NotApprovedLayout.setVisibility(View.GONE);
            binding.ButtonsLayout.setVisibility(View.GONE);
        }



        binding.UserCompdetailsButtonFeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent NewFeedBackIntent = new Intent(UserComplaintDetails.this, NewFeedbackActivity.class);
                String Title = getIntent().getStringExtra("Title");
                String Date = getIntent().getStringExtra("Date");
                String Type = getIntent().getStringExtra("Type");
                String Discription = getIntent().getStringExtra("Description");
                String Status = getIntent().getStringExtra("Status");
                NewFeedBackIntent.putExtra("Title", Title);
                NewFeedBackIntent.putExtra("Date", Date);
                NewFeedBackIntent.putExtra("Type", Type);
                NewFeedBackIntent.putExtra("Description", Discription);
                NewFeedBackIntent.putExtra("Status", Status);

                startActivity(NewFeedBackIntent);
            }
        });
        binding.btnCancelComplaint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseFirestore.collection("Complaints").document(DocID).update("status","Cancel").addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(UserComplaintDetails.this, "Status Changed Succussfully", Toast.LENGTH_SHORT).show();
                        Intent DashboardIntent = new Intent(UserComplaintDetails.this,DashboardActivity.class);
                        startActivity(DashboardIntent);
                    }
                });
            }
        });
        binding.btnSuccessComplaint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                firebaseFirestore.collection("Complaints").document();
                firebaseFirestore.collection("Complaints").document(DocID).update("status","Success").addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(UserComplaintDetails.this, "Status Changed Succussfully", Toast.LENGTH_SHORT).show();
                        Intent DashboardIntent = new Intent(UserComplaintDetails.this,DashboardActivity.class);
                        startActivity(DashboardIntent);
                    }
                });
            }
        });



    }
}