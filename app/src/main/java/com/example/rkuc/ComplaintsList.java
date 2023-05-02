package com.example.rkuc;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.rkuc.databinding.ActivityComplaintsListBinding;
import com.example.rkuc.databinding.ComplaintListItemBinding;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;

public class ComplaintsList extends AppCompatActivity {
    ActivityComplaintsListBinding binding;
    RecyclerView ComplaintsRviev;
    FirebaseFirestore firebaseFirestore;
    FirebaseAuth firebaseAuth;
    DocumentReference documentReference;
    FirestoreRecyclerAdapter adapter;
    LinearLayoutManager linearLayoutManager;
    Query query;
    String UserEmail;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityComplaintsListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Intent intent = getIntent();
        String Status = intent.getStringExtra("Status");

        binding.ComplaintListTitle.setText(Status + " Compalints");


        //
        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        linearLayoutManager = new LinearLayoutManager(ComplaintsList.this, RecyclerView.VERTICAL, false);
        ComplaintsRviev = binding.ComplaintsRview;
        ComplaintsRviev.setLayoutManager(linearLayoutManager);

        //Query

        if (Status.equals("Total")) {
            query = firebaseFirestore.collection("Complaints").orderBy("date", Query.Direction.DESCENDING);
        } else if (Status.equals("Success")) {
            query = firebaseFirestore.collection("Complaints").whereEqualTo("status", "Success").orderBy("date", Query.Direction.DESCENDING);
        } else if (Status.equals("Pending")) {
            query = firebaseFirestore.collection("Complaints").whereEqualTo("status", "Pending").orderBy("date", Query.Direction.DESCENDING);
        } else if (Status.equals("Cancel")) {
            query = firebaseFirestore.collection("Complaints").whereEqualTo("status", "Cancel").orderBy("date", Query.Direction.DESCENDING);
        } else if (Status.equals("User")) {

            String UserId = firebaseAuth.getCurrentUser().getUid();
            documentReference = firebaseFirestore.collection("Users").document(UserId);
            documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
                @Override
                public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                    UserEmail = value.getString("email");
                }
            });


            query = firebaseFirestore.collection("Complaints").whereEqualTo("userid", UserId).orderBy("date", Query.Direction.DESCENDING);
        }


        //RecyclerOption
        FirestoreRecyclerOptions<ComplaintModel> options = new FirestoreRecyclerOptions.Builder<ComplaintModel>()
                .setQuery(query, ComplaintModel.class)
                .build();
//View Holder
        adapter = new FirestoreRecyclerAdapter<ComplaintModel, ComplaintViewHolder>(options) {
            @Override
            protected void onBindViewHolder(ComplaintViewHolder holder, int position, ComplaintModel model) {
                holder.bind(model);
//                holder.Subject.setText(model.getComplaintTitle());
//                holder.Desc.setText(model.getComplaintDescription());
//                holder.Email.setText(model.getEmail());
//                holder.Uid.setText(model.getUserid());
                holder.ArrowCard.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent UserComplaintDetailIntent = new Intent(v.getContext(), UserComplaintDetails.class);
                        UserComplaintDetailIntent.putExtra("Type", model.getComplaintType());
                        UserComplaintDetailIntent.putExtra("Title", model.getComplaintTitle());
                        UserComplaintDetailIntent.putExtra("Description", model.getComplaintDescription());
                        UserComplaintDetailIntent.putExtra("Date", model.getDate());
                        UserComplaintDetailIntent.putExtra("DocID",model.getDocumentid());
                        UserComplaintDetailIntent.putExtra("Email", model.getEmail());
                        UserComplaintDetailIntent.putExtra("Status", model.getStatus());
                        UserComplaintDetailIntent.putExtra("Userid", model.getUserid());
                        v.getContext().startActivity(UserComplaintDetailIntent);
                    }
                });
            }

            @Override
            public ComplaintViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.complaint_list_item, parent, false);
                return new ComplaintViewHolder(view);
            }


        };
        ComplaintsRviev.setHasFixedSize(true);

        ComplaintsRviev.setAdapter(adapter);


    }

    class ComplaintViewHolder extends RecyclerView.ViewHolder {
        ComplaintListItemBinding binding;
        TextView Type, Subject, Desc, Email, Uid, Date, Status;
        ImageView ArrowCard;

        public ComplaintViewHolder(@NonNull View itemView) {
            super(itemView);
            Type = itemView.findViewById(R.id.ComplaintType);
            Date = itemView.findViewById(R.id.Date);
            //Subject = itemView.findViewById(R.id.ComplaintSubject);
//            Desc = itemView.findViewById(R.id.ComplaintDesc);
//            Uid = itemView.findViewById(R.id.Uid);
//            Email = itemView.findViewById(R.id.email);
            ArrowCard = itemView.findViewById(R.id.imageViewArrow);


        }

        public void bind(ComplaintModel complaintModel) {
            Type.setText(complaintModel.getComplaintType() + " Complaint");
            Date.setText(complaintModel.getDate());
//            Subject.setText(complaintModel.getComplaintTitle());
//            Desc.setText(complaintModel.getComplaintDescription());
//            Email.setText(complaintModel.getEmail());
//            Uid.setText(complaintModel.getUserid());


        }

    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }
}