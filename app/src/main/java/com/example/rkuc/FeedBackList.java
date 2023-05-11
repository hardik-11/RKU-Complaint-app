package com.example.rkuc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rkuc.databinding.ActivityFeedBackListBinding;
import com.example.rkuc.databinding.ComplaintListItemBinding;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import Model.ComplaintModel;

//
public class FeedBackList extends AppCompatActivity {
    ActivityFeedBackListBinding binding;
    RecyclerView ComplaintsRviev;
    FirebaseFirestore firebaseFirestore;
    FirebaseAuth firebaseAuth;

    FirestoreRecyclerAdapter adapter;
    LinearLayoutManager linearLayoutManager;
    Query query;
    String UserEmail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityFeedBackListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        //
        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        linearLayoutManager = new LinearLayoutManager(FeedBackList.this, RecyclerView.VERTICAL, false);
        ComplaintsRviev = binding.FeedbackRView;
        ComplaintsRviev.setLayoutManager(linearLayoutManager);
        query = firebaseFirestore.collection("Complaints").orderBy("date", Query.Direction.DESCENDING);
        //RecyclerOption
        FirestoreRecyclerOptions<ComplaintModel> options = new FirestoreRecyclerOptions.Builder<ComplaintModel>()
                .setQuery(query, ComplaintModel.class)
                .build();
        //View Holder
        adapter = new FirestoreRecyclerAdapter<ComplaintModel, ComplaintViewHolder>(options) {
            @Override
            protected void onBindViewHolder(ComplaintViewHolder holder, int position, ComplaintModel model) {
                holder.bind(model);

                holder.ArrowCard.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(FeedBackList.this, "Feedback", Toast.LENGTH_SHORT).show();
                        Intent FeedbackDetailsIntent = new Intent(v.getContext(), FeedbackDetailsActivity.class);
                        FeedbackDetailsIntent.putExtra("FeedbackTitle", model.getFeedbackTitle());
                        FeedbackDetailsIntent.putExtra("FeedbackDetail", model.getFeedbackDeitail());
                        FeedbackDetailsIntent.putExtra("FeedbackDate", model.getFeedbackDate());
                        v.getContext().startActivity(FeedbackDetailsIntent);
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
        TextView FeedbackTitle,  Date;
        ImageView ArrowCard;

        public ComplaintViewHolder(@NonNull View itemView) {
            super(itemView);
            FeedbackTitle = itemView.findViewById(R.id.ComplaintType);
            Date = itemView.findViewById(R.id.Date);
            ArrowCard = itemView.findViewById(R.id.imageViewArrow);


        }

        public void bind(ComplaintModel complaintModel) {
            FeedbackTitle.setText(complaintModel.getFeedbackTitle() );
            Date.setText(complaintModel.getFeedbackDate());



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

