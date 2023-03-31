package com.dataflair.onlineattendancesystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dataflair.onlineattendancesystem.Activity.GetStartedActivity;
import com.dataflair.onlineattendancesystem.Activity.TeacherActivity;
import com.dataflair.onlineattendancesystem.Fragments.ProfileFragment;
import com.dataflair.onlineattendancesystem.Model.Model;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity {

    CircleImageView circleImageView;
    TextView nameTxt, mailTxt, totalWorkingDaysTxt, totalPresentsTxt, totalAbsentsTxt, percentageTxt;
    Button askPermissonBtn, signOutBtn;
    DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Database Path
        databaseReference = FirebaseDatabase.getInstance().getReference();

        //Address of the android materials
        circleImageView = (CircleImageView) findViewById(R.id.ProfileImageView);
        nameTxt = (TextView) findViewById(R.id.UserNameTxt);
        mailTxt = (TextView) findViewById(R.id.MailTxt);
        totalWorkingDaysTxt = (TextView) findViewById(R.id.TotalWorkingDaysTxt);
        totalPresentsTxt = (TextView) findViewById(R.id.TotalPresentsTxt);
        totalAbsentsTxt = (TextView) findViewById(R.id.TotalAbsentsTxt);
        percentageTxt = (TextView) findViewById(R.id.AttendancePercentageTxt);


        askPermissonBtn = (Button) findViewById(R.id.AskPermissionBtn);
        signOutBtn = (Button) findViewById(R.id.SignOutBtn);

        //Getting user detials from GoogleSignin
        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(getApplicationContext());
        if (acct != null) {
            nameTxt.setText(acct.getDisplayName());
            Picasso.get().load(acct.getPhotoUrl()).into(circleImageView);
            mailTxt.setText(acct.getEmail());
        }


        //Implementing OnClickListener
        askPermissonBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Calling intent to open mail app and sending mail
                Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
                String[] recipients = new String[]{"xyz@gmail.com", "",};
                emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, recipients);
                emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Subject Text Here....");
                emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, "Mail Text Here....");
                emailIntent.setType("text/plain");
                startActivity(Intent.createChooser(emailIntent, "Send mail using..."));
            }
        });


        //Implementing OnClickListener
        signOutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                GoogleSignInOptions gso = new GoogleSignInOptions.
                        Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).
                        build();

                //GoogleSignInClient to access the current user
                GoogleSignInClient googleSignInClient = GoogleSignIn.getClient(getApplicationContext(), gso);
                googleSignInClient.signOut().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            //User Signout
                            FirebaseAuth.getInstance().signOut();

                            //Redirecting to starting Activity
                            Intent intent = new Intent(getApplicationContext(), GetStartedActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);

                        }

                    }
                });

            }
        });

    }


    @Override
    protected void onStart() {
        super.onStart();
        //checking user already logged in  or not
        FirebaseApp.initializeApp(this);
        FirebaseUser mUser = FirebaseAuth.getInstance().getCurrentUser();
        if (mUser == null) {
            //if user not signed in then we will redirect this activity to LoginActivity
            Intent intent = new Intent(MainActivity.this, GetStartedActivity.class);
            startActivity(intent);
        } else {
            //Getting the id of the current student
            String id = GoogleSignIn.getLastSignedInAccount(getApplicationContext()).getId();

            //getting the data from the database using the id
            databaseReference.child("AllUsers").child(id).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {

                    //Assigning the values to model class
                    Model model = snapshot.getValue(Model.class);
                    String role = model.getRole();
                    if (role.equals("Teacher")) {
                        //navigating to the main activity after user successfully registers
                        Intent intent = new Intent(getApplicationContext(), TeacherActivity.class);
                        //Clears older activities and tasks
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    } else {

                        //Getting user detials from GoogleSignin
                        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(getApplicationContext());
                        databaseReference.child("users").child("Students").child(acct.getId())
                                .addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {

                                        //Assigning the values to model class
                                        Model model = snapshot.getValue(Model.class);
                                        String userClass = model.getClassName();

                                        //getting the attendance details and setting to textview
                                        String totalWorkingDays = model.getTotalDays().trim();
                                        String totalPresentDays = model.getAttendance().trim();

                                        int workingDays = Integer.parseInt(totalWorkingDays);
                                        int presentDays = Integer.parseInt(totalPresentDays);
                                        int absentDays = workingDays - presentDays;


                                        //Calculating the percentage and checking for 0 value
                                        if (workingDays == 0) {
                                            int percentage = 0;
                                            percentageTxt.setText("Attendance Percentage(%): " + String.valueOf(percentage));
                                        } else {
                                            int percentage = (presentDays * 100) / workingDays;
                                            percentageTxt.setText("Attendance Percentage(%): " + String.valueOf(percentage));
                                        }

                                        //Setting the values to textView
                                        totalWorkingDaysTxt.setText("Total Working Days: " + model.getTotalDays());
                                        totalPresentsTxt.setText("Total Present Days: " + model.getAttendance());
                                        totalAbsentsTxt.setText("Total Absent Days: " + String.valueOf(absentDays));


                                    }

                                    @Override
                                    public void onCancelled(@NonNull @NotNull DatabaseError error) {

                                    }
                                });


                    }
                }

                @Override
                public void onCancelled(@NonNull @NotNull DatabaseError error) {

                }
            });
        }
    }
}