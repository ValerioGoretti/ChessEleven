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
    private Button communityButton;
    private  Button multiButton;
    private Button logoutButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Button profile=(Button) findViewById(R.id.button_profile);
        Button logout=(Button) findViewById(R.id.logout);
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
        communityButton=findViewById(R.id.buttonCommunity);
        communityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), community_activity.class);
                startActivity(intent);

            }
        });
        multiButton=(Button) findViewById(R.id.button5);
        multiButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getBaseContext(),Matchmaking.class));
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shared.edit().clear();
                startActivity(new Intent(getBaseContext(),Welcome.class));
                finish();
            }
        });


    }
}