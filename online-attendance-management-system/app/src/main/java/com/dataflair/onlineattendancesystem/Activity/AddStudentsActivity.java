package com.dataflair.onlineattendancesystem.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.dataflair.onlineattendancesystem.Adapters.AddStudentsAdapter;
import com.dataflair.onlineattendancesystem.Model.Model;
import com.dataflair.onlineattendancesystem.R;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddStudentsActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    AddStudentsAdapter adapter;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_students);

        //Getting data from the past intent
        String className = getIntent().getStringExtra("className");
        Toast.makeText(getApplicationContext(), className, Toast.LENGTH_SHORT).show();

        //Assigning the Recyclerview to display all food items
        recyclerView = (RecyclerView) findViewById(R.id.StudentsRecylerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.hasFixedSize();

        //Database path
        databaseReference = FirebaseDatabase.getInstance().getReference().child("users").child("Students");

        //Firebase Recycler Options to get the data form firebase database using model class and reference
        FirebaseRecyclerOptions<Model> options =
                new FirebaseRecyclerOptions.Builder<Model>()
                        .setQuery(databaseReference, Model.class)
                        .build();

        adapter = new AddStudentsAdapter(options, className);


        //setting the adapter to the recyclerview
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onStart() {
        super.onStart();

        //starting adapter
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();

        //Stopping adapter from listening data form firebase
        adapter.stopListening();
    }
}