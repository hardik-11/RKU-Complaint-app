package com.example.rkuc;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.rkuc.databinding.ActivityUserNewComplaintBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

public class UserNewComplaint extends AppCompatActivity {

    ActivityUserNewComplaintBinding binding;
    FirebaseFirestore firebaseFirestore;
    FirebaseAuth firebaseAuth;
    DocumentReference documentReference;
    String UserId;
    String Email;
    String Date;
    String DocID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUserNewComplaintBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        Intent intent = getIntent();
        String getCardName = intent.getStringExtra("cardName");
        int getImageName = intent.getExtras().getInt("bgImage");
        if (Objects.equals(getCardName, "Other")) {
            binding.UserComplaintEditBoxType.setEnabled(true);
            binding.complaintTitle.setText(getCardName + " Complaint");
            binding.UserComplaintHeaderImage.setImageResource(getImageName);
        } else {
            binding.UserComplaintEditBoxType.setText(getCardName);
            binding.complaintTitle.setText(getCardName + " Complaint");
            binding.UserComplaintHeaderImage.setImageResource(getImageName);
        }

        UserId = firebaseAuth.getCurrentUser().getUid();
        documentReference = firebaseFirestore.collection("Users").document(UserId);
        documentReference.addSnapshotListener(UserNewComplaint.this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                Email = value.getString("email");


            }
        });
        Date = getTodaysDate().toString();

        binding.NewComplaintButtonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // assign values to string which we get from input
                String ComplainType = binding.UserComplaintEditBoxType.getText().toString();
                String ComplainTitle = binding.UserComplaintEditBoxTitle.getText().toString().trim();
                String ComplainDescription = binding.UserComplaintEditBoxDescription.getText().toString();
                //object of Loading box

                //Chake value is not empty
                if (ComplainType.length() == 0) {
                    Toast.makeText(UserNewComplaint.this, "Please, enter your name filed", Toast.LENGTH_SHORT).show();
                } else if (ComplainTitle.length() == 0) {
                    Toast.makeText(UserNewComplaint.this, "Please, enter your email filed", Toast.LENGTH_SHORT).show();
                } else if (ComplainDescription.length() == 0) {
                    Toast.makeText(UserNewComplaint.this, "Please, enter your password filed", Toast.LENGTH_SHORT).show();
                } else {


                    DocID = firebaseFirestore.collection("Complaint").document().getId();
                    ComplaintModel complain = new ComplaintModel(ComplainDescription, ComplainTitle, ComplainType, Date,DocID, Email, "Pending", UserId);

                    firebaseFirestore.collection("Complaints").document(DocID).set(complain).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {

                            Log.d("Document id","---------------------------------------"+DocID);
                        }
                    });
                    Toast.makeText(UserNewComplaint.this, "Complaint Registered Successfully", Toast.LENGTH_SHORT).show();

                    Intent SidebarIntent = new Intent(UserNewComplaint.this, MenuBarActivty.class);
                    startActivity(SidebarIntent);

                }
            }
        });


    }

    private String getTodaysDate() {
        return new SimpleDateFormat("yyyy/MM/dd", Locale.getDefault()).format(new Date());
    }
}