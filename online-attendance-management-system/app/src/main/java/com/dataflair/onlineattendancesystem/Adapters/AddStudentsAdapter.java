package com.dataflair.onlineattendancesystem.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dataflair.onlineattendancesystem.Model.Model;
import com.dataflair.onlineattendancesystem.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

public class AddStudentsAdapter extends FirebaseRecyclerAdapter<Model, AddStudentsAdapter.Viewholder> {

    String className;

    public AddStudentsAdapter(@NonNull FirebaseRecyclerOptions<Model> options, String className) {

        super(options);

        //Getting classname form the fragment
        this.className = className;

    }

    @Override
    protected void onBindViewHolder(@NonNull AddStudentsAdapter.Viewholder holder, int position, @NonNull Model model) {

        //Getting data from database using model class and assigning
        holder.studentNameTxt.setText(model.getName());
        holder.addStudetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //HashMap to store data and to add it to firebase
                HashMap studentDetails = new HashMap();
                studentDetails.put("name", model.getName());
                studentDetails.put("mail", model.getMail());
                studentDetails.put("id", model.getId());
                studentDetails.put("attendance", "0");
                studentDetails.put("className", className);
                studentDetails.put("totalDays", "0");

                //Adding data to firebase using path
                FirebaseDatabase.getInstance().getReference().child("Classes").child(className).child(model.getId())
                        .setValue(studentDetails).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull @NotNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            //Adding data to firebase using path
                            FirebaseDatabase.getInstance().getReference().child("users").child("Students").child(model.getId())
                                    .updateChildren(studentDetails).addOnCompleteListener(new OnCompleteListener() {
                                @Override
                                public void onComplete(@NonNull @NotNull Task task) {
                                    if (task.isSuccessful()) {

                                        //Adding data to firebase using path
                                        FirebaseDatabase.getInstance().getReference().child("users").child("Students").child(model.getId())
                                                .child("class").setValue(className)
                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull @NotNull Task<Void> task) {
                                                        if (task.isSuccessful()) {
                                                            //Showing Toast Message to user
                                                            Toast.makeText(view.getContext(), "Student Added Successfully", Toast.LENGTH_SHORT).show();
                                                        } else {
                                                            //Showing Toast Message to user
                                                            Toast.makeText(view.getContext(), "Please,Try Again", Toast.LENGTH_SHORT).show();
                                                        }

                                                    }
                                                });
                                    } else {
                                        //Showing Toast Message to user
                                        Toast.makeText(view.getContext(), "Please,Try Again", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        } else {
                            //Showing Toast Message to user
                            Toast.makeText(view.getContext(), "Please,Try Again", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });


    }


    @NonNull
    @Override
    public AddStudentsAdapter.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        //the data objects are inflated into the xml file single_data_item
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.add_student_file, parent, false);
        return new AddStudentsAdapter.Viewholder(view);
    }

    //we need view holder to hold each objet form recyclerview and to show it in recyclerview
    class Viewholder extends RecyclerView.ViewHolder {


        TextView studentNameTxt;
        Button addStudetBtn;

        public Viewholder(@NonNull View itemView) {
            super(itemView);

            //assigning the address of the materials
            studentNameTxt = (TextView) itemView.findViewById(R.id.StudentNameTxt);
            addStudetBtn = (Button) itemView.findViewById(R.id.AddStudentBtn);

        }
    }

}

