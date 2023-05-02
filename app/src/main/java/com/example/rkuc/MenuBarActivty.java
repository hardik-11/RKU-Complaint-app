package com.example.rkuc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.example.rkuc.databinding.ActivityMenuBarActivtyBinding;

public class MenuBarActivty extends AppCompatActivity {

    ActivityMenuBarActivtyBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMenuBarActivtyBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        String[] menuName = {"New Complaint", "Dashboard", "Complaints", "Feedbacks", "New Admin", "Settings"};
        int[] menuImage = {R.drawable.bx_home, R.drawable.bxs_dashboard, R.drawable.book_reader_solid, R.drawable.bxs_message_dots, R.drawable.user_plus_solid, R.drawable.settings_icon};

        MenuBarAdapter menuAdapter = new MenuBarAdapter(MenuBarActivty.this, menuName, menuImage);
        binding.gridMenu.setAdapter(menuAdapter);


        binding.gridMenu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                switch (position) {
                    case 0:
                        startActivity(new Intent(MenuBarActivty.this, HomeActivity.class));
                        break;

                    case 1:
                        startActivity(new Intent(MenuBarActivty.this, DashboardActivity.class));
                        break;

                    case 2:
                        Intent ComplaintListIntent = new Intent(MenuBarActivty.this, ComplaintsList.class);
                        ComplaintListIntent.putExtra("Status", "User");
                        startActivity(ComplaintListIntent);
                        break;

                    case 3:
                        startActivity(new Intent(MenuBarActivty.this, FeedBackList.class));
                        break;

                    case 4:
                        startActivity(new Intent(MenuBarActivty.this, NewAdminActivity.class));
                        break;

                    case 5:
                        startActivity(new Intent(MenuBarActivty.this, SettingsActivity.class));
                        break;

                    default:
                        Toast.makeText(MenuBarActivty.this, "Error", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });
    }
}