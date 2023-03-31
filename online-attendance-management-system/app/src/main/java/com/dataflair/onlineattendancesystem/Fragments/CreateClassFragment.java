package com.dataflair.onlineattendancesystem.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.dataflair.onlineattendancesystem.Adapters.CreateClassAdapter;
import com.dataflair.onlineattendancesystem.Model.Model;
import com.dataflair.onlineattendancesystem.R;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.NotNull;

public class CreateClassFragment extends Fragment {

    CreateClassAdapter adapter;
    RecyclerView recyclerView;
    EditText createClassEditTxt;
    Button createClassBtn;
    DatabaseReference databaseReference;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // inflating the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_create_class, container, false);

        //assigning the Recyclerview to display all created classes
        recyclerView = (RecyclerView) view.findViewById(R.id.AllClassesRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.hasFixedSize();

        //database path
        databaseReference = FirebaseDatabase.getInstance().getReference().child("AllClasses");

        //assigning the addresses of the android materials
        createClassEditTxt = (EditText) view.findViewById(R.id.ClassNameEditTxt);
        createClassBtn = (Button) view.findViewById(R.id.AddClassBtn);

        //Implementing onClickListener
        createClassBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Getting the data form editText
                String className = createClassEditTxt.getText().toString();
                if (className.isEmpty()) {
                    Toast.makeText(getContext(), "Please,Enter Class Name", Toast.LENGTH_SHORT).show();
                } else {
                    //Generating the unique key
                    String key = databaseReference.push().getKey().toString();
                    //Setting the data to firebase
                    databaseReference.child(key).child("className").setValue(className)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull @NotNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        //Showing the toast message
                                        Toast.makeText(getContext(), "Class Added Successfully", Toast.LENGTH_SHORT).show();
                                        //Setting the edit text to null
                                        createClassEditTxt.setText("");
                                    } else {
                                        //Showing the toast message to user
                                        Toast.makeText(getContext(), "Please Try again", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            }
        });

        //Firebase Recycler Options to get the data form firebase database using model class and reference
        FirebaseRecyclerOptions<Model> options =
                new FirebaseRecyclerOptions.Builder<Model>()
                        .setQuery(databaseReference, Model.class)
                        .build();

        adapter = new CreateClassAdapter(options);

        //setting the adapter to the recyclerview
        recyclerView.setAdapter(adapter);


        return view;
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