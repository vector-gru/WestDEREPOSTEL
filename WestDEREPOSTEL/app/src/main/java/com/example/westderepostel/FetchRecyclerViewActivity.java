package com.example.westderepostel;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Objects;

public class FetchRecyclerViewActivity extends AppCompatActivity {

    RecyclerView recyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fetch_recycler_view);

        DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference();

        databaseReference.child("PdfUploadUrls").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                // actually called for individual items at the database  reference...
                String fileName=snapshot.getKey();// return the fileName
                String url=snapshot.getValue(String.class);//return url for 'fileName'

                ((MyAdapter) Objects.requireNonNull(recyclerView.getAdapter())).update(fileName,url);

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        recyclerView=findViewById(R.id.idRecyclerViewFetch);
        //custom adapters always
        //populate the recycler view with items...
        recyclerView.setLayoutManager(new LinearLayoutManager(FetchRecyclerViewActivity.this));
        MyAdapter myAdapter=new MyAdapter(recyclerView,FetchRecyclerViewActivity.this,new ArrayList<String>(),new ArrayList<String>());
        recyclerView.setAdapter(myAdapter);

    }
}
