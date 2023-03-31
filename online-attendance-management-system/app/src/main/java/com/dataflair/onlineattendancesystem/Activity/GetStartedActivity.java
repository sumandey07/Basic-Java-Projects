package com.dataflair.onlineattendancesystem.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.dataflair.onlineattendancesystem.R;

public class GetStartedActivity extends AppCompatActivity {

    Button getStartedBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_started);
        getStartedBtn = (Button) findViewById(R.id.GetStartedBtn);

        //OnclickListener implementation to change the activity
        getStartedBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
            }
        });
    }

}
