package com.example.rkuc;

import static androidx.core.content.ContentProviderCompat.requireContext;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rkuc.databinding.ActivitySettingsBinding;
import com.google.firebase.auth.FirebaseAuth;

public class SettingsActivity extends AppCompatActivity {

    private ActivitySettingsBinding binding;
    private String tutorials[] = {"Profile", "Change Password", "Logout"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySettingsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ArrayAdapter<String> arr = new ArrayAdapter<String>(getApplicationContext(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, tutorials);
        binding.UserSettingsList.setAdapter(arr);

        binding.UserSettingsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = (String) parent.getItemAtPosition(position);

                if (selectedItem == "Profile") {
                    startActivity(new Intent(SettingsActivity.this, ProfileActivity.class));
                } else if (selectedItem == "Change Password") {
                    startActivity(new Intent(SettingsActivity.this, ChangePasswordActivity.class));
                } else if (selectedItem == "Logout") {
//                    FirebaseAuth.getInstance().signOut();
//                    startActivity(new Intent(SettingsActivity.this, MainActivity.class));
                    logoutDialogBox();
                }
            }
        });
    }

    public void logoutDialogBox() {
        AlertDialog.Builder builder = new AlertDialog.Builder(SettingsActivity.this);
        LayoutInflater inflater = SettingsActivity.this.getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.logoutbox, null));
        builder.setCancelable(true);

        AlertDialog dialog;
        dialog = builder.create();
        dialog.show();

        TextView btnYes = (TextView) dialog.findViewById(R.id.Logout_Button_Yes);
        TextView btnNo = (TextView) dialog.findViewById(R.id.Logout_Button_No);

        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent LoginIntent = new Intent(view.getContext(), MainActivity.class);
                startActivity(LoginIntent);
                Toast.makeText(SettingsActivity.this, "Logout Successfully", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });

        btnNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(SettingsActivity.this, "Sorry! You are not logout.", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });
    }

}