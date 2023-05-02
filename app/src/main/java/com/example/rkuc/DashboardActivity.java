package com.example.rkuc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.View;

import com.example.rkuc.databinding.ActivityDashboardBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.AggregateQuery;
import com.google.firebase.firestore.AggregateQuerySnapshot;
import com.google.firebase.firestore.AggregateSource;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class DashboardActivity extends AppCompatActivity {
    ActivityDashboardBinding binding;
    FirebaseFirestore firebaseFirestore;
    AggregateQuery aggregateQuery;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDashboardBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        firebaseFirestore = FirebaseFirestore.getInstance();
        final LoadingBar loadingBar = new LoadingBar(DashboardActivity.this);
        loadingBar.show();
        new android.os.Handler(Looper.getMainLooper()).postDelayed(
                new Runnable() {
                    public void run() {
                        loadingBar.cancel();
                    }
                },
                3000);
        //Total
        Query Total = firebaseFirestore.collection("Complaints");
        aggregateQuery = Total.count();
        aggregateQuery.get(AggregateSource.SERVER).addOnCompleteListener(new OnCompleteListener<AggregateQuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<AggregateQuerySnapshot> task) {
                if (task.isSuccessful()) {
                    AggregateQuerySnapshot snapshot = task.getResult();
                    binding.AdminDashTextCardTotal.setText(String.valueOf(snapshot.getCount()));
                }
            }
        });
        //Success
        Query Success = firebaseFirestore.collection("Complaints").whereEqualTo("status", "Success");
        aggregateQuery = Success.count();
        aggregateQuery.get(AggregateSource.SERVER).addOnCompleteListener(new OnCompleteListener<AggregateQuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<AggregateQuerySnapshot> task) {
                if (task.isSuccessful()) {
                    AggregateQuerySnapshot snapshot = task.getResult();
                    binding.AdminDashTextCardSuccess.setText(String.valueOf(snapshot.getCount()));
                }
            }
        });

        //Pending
        Query Pending = firebaseFirestore.collection("Complaints").whereEqualTo("status", "Pending");
        aggregateQuery = Pending.count();
        aggregateQuery.get(AggregateSource.SERVER).addOnCompleteListener(new OnCompleteListener<AggregateQuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<AggregateQuerySnapshot> task) {
                if (task.isSuccessful()) {
                    AggregateQuerySnapshot snapshot = task.getResult();
                    binding.AdminDashTextCardPadding.setText(String.valueOf(snapshot.getCount()));
                }
            }
        });
        //Cancel
        Query Cancel = firebaseFirestore.collection("Complaints").whereEqualTo("status", "Cancel");
        aggregateQuery = Cancel.count();
        aggregateQuery.get(AggregateSource.SERVER).addOnCompleteListener(new OnCompleteListener<AggregateQuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<AggregateQuerySnapshot> task) {
                if (task.isSuccessful()) {
                    AggregateQuerySnapshot snapshot = task.getResult();
                    binding.AdminDashTextCardCancel.setText(String.valueOf(snapshot.getCount()));
                }
            }
        });


        Intent ComplainListIntent = new Intent(DashboardActivity.this, ComplaintsList.class);


        binding.CardTotal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ComplainListIntent.putExtra("Status", "Total");
                startActivity(ComplainListIntent);
            }
        });

        binding.CardSuccess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ComplainListIntent.putExtra("Status", "Success");
                startActivity(ComplainListIntent);

            }
        });

        binding.CardPanding.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ComplainListIntent.putExtra("Status", "Pending");
                startActivity(ComplainListIntent);
            }
        });

        binding.CardCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ComplainListIntent.putExtra("Status", "Cancel");
                startActivity(ComplainListIntent);
            }
        });


    }
}