package com.dataflair.onlineattendancesystem.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.dataflair.onlineattendancesystem.Adapters.StudentsAttendanceAdapter;
import com.dataflair.onlineattendancesystem.Model.Model;
import com.dataflair.onlineattendancesystem.R;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class StudentsAttendaceActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    StudentsAttendanceAdapter adapter;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_students_attendace);

        String className=getIntent().getStringExtra("className");

        //Assigning the Recyclerview to display all classes
        recyclerView = (RecyclerView) findViewById(R.id.StudentsAttendanceRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.hasFixedSize();

        databaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child(className);

        //Firebase Recycler Options to get the data form firebase database using model class and reference
        FirebaseRecyclerOptions<Model> options =
                new FirebaseRecyclerOptions.Builder<Model>()
                        .setQuery(databaseReference, Model.class)
                        .build();

        adapter = new StudentsAttendanceAdapter(options,className);


        //setting the adapter to the recyclerview
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onStart() {
        super.onStart();

        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();

        adapter.stopListening();
    }
}