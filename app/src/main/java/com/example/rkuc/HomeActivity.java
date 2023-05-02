package com.example.rkuc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.example.rkuc.databinding.ActivityHomeBinding;

public class HomeActivity extends AppCompatActivity {

    ActivityHomeBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        String[] complaintName = {"Academic", "Food", "Sports", "Library", "Ragging", "Other"};
        int[] complaintImage = {R.drawable.college, R.drawable.food, R.drawable.sports, R.drawable.library, R.drawable.ragging, R.drawable.other};

        MenuBarAdapter menuAdapter = new MenuBarAdapter(HomeActivity.this, complaintName, complaintImage);
        binding.complaintCard.setAdapter(menuAdapter);

        binding.complaintCard.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent change_new_complaint = new Intent(HomeActivity.this, UserNewComplaint.class);

                switch (position) {
                    case 0:
                        change_new_complaint.putExtra("cardName", complaintName[position]);
                        change_new_complaint.putExtra("bgImage", R.drawable.college);
                        startActivity(change_new_complaint);
                        break;

                    case 1:
                        change_new_complaint.putExtra("cardName", complaintName[position]);
                        change_new_complaint.putExtra("bgImage", R.drawable.food);
                        startActivity(change_new_complaint);
                        break;

                    case 2:
                        change_new_complaint.putExtra("cardName", complaintName[position]);
                        change_new_complaint.putExtra("bgImage", R.drawable.sports);
                        startActivity(change_new_complaint);
                        break;

                    case 3:
                        change_new_complaint.putExtra("cardName", complaintName[position]);
                        change_new_complaint.putExtra("bgImage", R.drawable.library);
                        startActivity(change_new_complaint);
                        break;

                    case 4:
                        change_new_complaint.putExtra("cardName", complaintName[position]);
                        change_new_complaint.putExtra("bgImage", R.drawable.ragging);
                        startActivity(change_new_complaint);
                        break;

                    case 5:
                        change_new_complaint.putExtra("cardName", complaintName[position]);
                        change_new_complaint.putExtra("bgImage", R.drawable.other);
                        startActivity(change_new_complaint);
                        break;

                    default:
                        Toast.makeText(getApplicationContext(), "Please, select the complaint", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });

    }
}