package com.example.dipanshkhandelwal.chess;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Home extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Button profile=(Button) findViewById(R.id.button_profile);
        TextView text= (TextView) findViewById(R.id.textView6);
        SharedPreferences shared=PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        text.setText("Welcome\n"+shared.getString("username","null"));
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), Profile.class);
                startActivity(intent);
            }
        });

    }
}