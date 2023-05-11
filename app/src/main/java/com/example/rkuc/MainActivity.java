package com.example.rkuc;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.rkuc.databinding.ActivityMainBinding;
import com.example.rkuc.databinding.ActivityMenuBarActivtyBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    FirebaseAuth firebaseAuth;
    FirebaseDatabase firebaseDatabase;
    FirebaseFirestore firebaseFirestore;
    DocumentReference documentReference;
    String UserId;
    int Usertype;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        final LoadingBar loadingBar = new LoadingBar(MainActivity.this);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();


        binding.ButtonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingBar.show();
                //assign values
                String email = binding.SignInEditBoxEmail.getText().toString().trim();
                String password = binding.SignInEditBoxPassword.getText().toString().trim();
                // Calling loading dialog box method

                if (email.length() != 0 && password.length() != 0) {

                    // Show Loading Dialog Box Call Method


                    // Check The User Email address and Password check in firebase database
                    firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            // Login SuccessFully Condition True
                            if (task.isSuccessful()) {
                                String uid = task.getResult().getUser().getUid();
                                firebaseDatabase.getReference().child("Users").child(uid).child("userType").addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        int UserType = snapshot.getValue(Integer.class);
                                        // Call Change Activity Intent Object
                                        Intent MenubarIntent = new Intent(MainActivity.this, MenuBarActivty.class);

                                        if (UserType == 0) {

                                            // Send Data In User Side
                                            MenubarIntent.putExtra("UserType","0");
                                            startActivity(MenubarIntent);
                                            loadingBar.cancel();
                                        } else if (UserType == 1) {

                                            // Send Data In Admin Side
                                            MenubarIntent.putExtra("UserType","1");
                                            startActivity(MenubarIntent);
                                            loadingBar.cancel();
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });

                            }

                            // Login SuccessFully Condition False
                            else {

                                Toast.makeText(getApplicationContext(), "Please, Check Your Email And Password", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });


                } else {
                    Toast.makeText(MainActivity.this, "Please, enter your filed", Toast.LENGTH_SHORT).show();
                }
            }
        });

        binding.ForgotPassword.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent ResetPasswordIntent = new Intent(MainActivity.this,ChangePasswordActivity.class);
        startActivity(ResetPasswordIntent);
    }
});
        binding.SignInLinkRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, RegisterActivity.class));
            }
        });
    }
}