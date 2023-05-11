package com.example.rkuc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.rkuc.databinding.ActivityRegisterBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

import Model.UserModel;

public class RegisterActivity extends AppCompatActivity {

    ActivityRegisterBinding binding;
    FirebaseAuth firebaseAuth;
    FirebaseDatabase firebaseDatabase;
    FirebaseFirestore firebaseFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        final LoadingBar loadingBar = new LoadingBar(RegisterActivity.this);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();

        binding.SignUpButtonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingBar.show();
// assign values to string which we get from input
                String name = binding.SignUpEditBoxFullName.getText().toString();
                String email = binding.SignUpEditBoxEmail.getText().toString().trim();
                String password = binding.SignUpEditBoxPassword.getText().toString();
                String RePassword = binding.SignUpEditBoxRePassword.getText().toString();
                //object of Loading box

                //Chake value is not empty
                if (name.length() == 0) {
                    Toast.makeText(RegisterActivity.this, "Please, enter your name filed", Toast.LENGTH_SHORT).show();
                } else if (email.length() == 0) {
                    Toast.makeText(RegisterActivity.this, "Please, enter your email filed", Toast.LENGTH_SHORT).show();
                } else if (password.length() == 0) {
                    Toast.makeText(RegisterActivity.this, "Please, enter your password filed", Toast.LENGTH_SHORT).show();
                } else if (!password.matches(RePassword)) {
                    Toast.makeText(RegisterActivity.this, "Password And Re-Password Does not Match", Toast.LENGTH_SHORT).show();
                } else {

                    //Create user in firebase
                    firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                //Add data in firebase database
                                String uid = task.getResult().getUser().getUid();

                                UserModel user = new UserModel(uid, name, email, password, 0);
                                firebaseDatabase.getReference().child("Users").child(uid).setValue(user);
                                firebaseFirestore.collection("Users").document(uid).set(user);

                                //Login activity Intent
                                Intent LoginIntent = new Intent(RegisterActivity.this, MainActivity.class);
                                startActivity(LoginIntent);

                                //toast message
                                Toast.makeText(RegisterActivity.this, "User Registered Successfully", Toast.LENGTH_SHORT).show();
                                loadingBar.cancel();
                            } else {

                                Toast.makeText(RegisterActivity.this, "User Not Registered", Toast.LENGTH_SHORT).show();
                                loadingBar.cancel();
                            }
                        }
                    })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(RegisterActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });
                }


            }
        });

        binding.SignUpLinkLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this, MainActivity.class));
            }
        });
    }
}