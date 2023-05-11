package com.example.rkuc;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.rkuc.databinding.ActivityNewAdminBinding;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

import Model.UserModel;

public class NewAdminActivity extends AppCompatActivity {
    ActivityNewAdminBinding binding;
    FirebaseAuth firebaseAuth;
    FirebaseDatabase firebaseDatabase;
    FirebaseFirestore firebaseFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityNewAdminBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        binding.newAdminButtonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // assign values to string which we get from input
                String name = binding.newAdminEditBoxFullName.getText().toString();
                String email = binding.newAdminEditBoxEmail.getText().toString().trim();
                String password = binding.newAdminEditBoxPassword.getText().toString();
                //object of Loading box

                //Chake value is not empty
                if (name.length() == 0) {
                    Toast.makeText(NewAdminActivity.this, "Please, enter your name filed", Toast.LENGTH_SHORT).show();
                } else if (email.length() == 0) {
                    Toast.makeText(NewAdminActivity.this, "Please, enter your email filed", Toast.LENGTH_SHORT).show();
                } else if (password.length() == 0) {
                    Toast.makeText(NewAdminActivity.this, "Please, enter your password filed", Toast.LENGTH_SHORT).show();
                } else {

                    //Create user in firebase
                    firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                //Add data in firebase database
                                String uid = task.getResult().getUser().getUid();

                                UserModel user = new UserModel(uid, name, email, password, 1);
                                firebaseDatabase.getReference().child("Users").child(uid).setValue(user);
                                firebaseFirestore.collection("Users").document(uid).set(user);

                                //Login activity Intent
                                Intent LoginIntent = new Intent(NewAdminActivity.this, MenuBarActivty.class);
                                startActivity(LoginIntent);

                                //toast message
                                Toast.makeText(NewAdminActivity.this, "Admin Registered Successfully", Toast.LENGTH_SHORT).show();

                            } else {

                                Toast.makeText(NewAdminActivity.this, "Admin Not Registered", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }


            }
        });

    }
}