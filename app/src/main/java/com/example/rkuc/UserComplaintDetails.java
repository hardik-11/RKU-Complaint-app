package com.example.rkuc;

import static android.content.ContentValues.TAG;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.rkuc.databinding.ActivityUserComplaintDetailsBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class UserComplaintDetails extends AppCompatActivity {

    ActivityUserComplaintDetailsBinding binding;
    FirebaseFirestore firebaseFirestore;
    FirebaseAuth firebaseAuth;
    DocumentReference documentReference;
    int UserType;
    String UserId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUserComplaintDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();




        String GetType = getIntent().getStringExtra("Type");
        binding.UserCompDetailsTextHeaderTitle.setText(GetType + " Complaint");
        binding.UserCompdetailsDate.setText(getIntent().getStringExtra("Date"));
        binding.UserCompdetailsDescription.setText(getIntent().getStringExtra("Description"));
        binding.UserCompdetailsTitle.setText(getIntent().getStringExtra("Title"));
        String DocID = getIntent().getStringExtra("DocID");
        String Status = getIntent().getStringExtra("Status");

        UserId = firebaseAuth.getCurrentUser().getUid();
        documentReference = firebaseFirestore.collection("Users").document(UserId);
        documentReference.addSnapshotListener(UserComplaintDetails.this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error != null) {
                    Log.w(TAG, "Listen failed.", error);
                    return;
                }

                if (value != null && value.exists()) {
                    UserType = Integer.parseInt(value.getString("userType"));
                    // Do something with Email
                    Log.d(TAG, "Current data: ----------------------------------------------------------------------"+UserType);
                    if (UserType == 0) {
                        if (Status.equals("Pending")) {
                            binding.UserCompStatusImage.setImageResource(R.drawable.pendingstatus);
                            binding.FeedBackLayout.setVisibility(View.GONE);
                            binding.NotApprovedLayout.setVisibility(View.GONE);
//        binding.ButtonsLayout.setVisibility(View.VISIBLE);
                        } else if (Status.equals("Cancel")) {
                            binding.UserCompStatusImage.setImageResource(R.drawable.cancelstatus);
                            binding.FeedBackLayout.setVisibility(View.GONE);
                            binding.NotApprovedLayout.setVisibility(View.VISIBLE);
                            binding.ButtonsLayout.setVisibility(View.GONE);
                        } else if (Status.equals("Success")) {
                            binding.UserCompStatusImage.setImageResource(R.drawable.success);
                            binding.FeedBackLayout.setVisibility(View.VISIBLE);
                            binding.NotApprovedLayout.setVisibility(View.GONE);
                            binding.ButtonsLayout.setVisibility(View.GONE);
                        }

                    }else{
                        if (UserType == 1) {
                            if (Status.equals("Pending")) {
                                binding.UserCompStatusImage.setImageResource(R.drawable.pendingstatus);
                                binding.FeedBackLayout.setVisibility(View.GONE);
                                binding.NotApprovedLayout.setVisibility(View.GONE);
        binding.ButtonsLayout.setVisibility(View.VISIBLE);
                            } else if (Status.equals("Cancel")) {
                                binding.UserCompStatusImage.setImageResource(R.drawable.cancelstatus);
                                binding.FeedBackLayout.setVisibility(View.GONE);
                                binding.NotApprovedLayout.setVisibility(View.VISIBLE);
                                binding.ButtonsLayout.setVisibility(View.GONE);
                            } else if (Status.equals("Success")) {
                                binding.UserCompStatusImage.setImageResource(R.drawable.success);
//            binding.FeedBackLayout.setVisibility(View.VISIBLE);
                                binding.NotApprovedLayout.setVisibility(View.GONE);
                                binding.ButtonsLayout.setVisibility(View.GONE);
                            }

                        }

                    }

                }
            }
        });



        binding.UserCompdetailsButtonFeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent NewFeedBackIntent = new Intent(UserComplaintDetails.this, NewFeedbackActivity.class);
                String DocID = getIntent().getStringExtra("DocID");
                NewFeedBackIntent.putExtra("DocID", DocID);
                startActivity(NewFeedBackIntent);
            }
        });
        binding.btnCancelComplaint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseFirestore.collection("Complaints").document(DocID).update("status", "Cancel").addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(UserComplaintDetails.this, "Status Changed Succussfully", Toast.LENGTH_SHORT).show();
                        Intent DashboardIntent = new Intent(UserComplaintDetails.this, DashboardActivity.class);
                        startActivity(DashboardIntent);
                    }
                });
            }
        });
        binding.btnSuccessComplaint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                firebaseFirestore.collection("Complaints").document();
                firebaseFirestore.collection("Complaints").document(DocID).update("status", "Success").addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(UserComplaintDetails.this, "Status Changed Succussfully", Toast.LENGTH_SHORT).show();
                        Intent DashboardIntent = new Intent(UserComplaintDetails.this, DashboardActivity.class);
                        startActivity(DashboardIntent);
                    }
                });
            }
        });


    }
}