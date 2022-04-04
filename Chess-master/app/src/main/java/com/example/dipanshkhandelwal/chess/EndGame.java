package com.example.dipanshkhandelwal.chess;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

public class EndGame extends AppCompatActivity {

    private TextView resultTV;
    private TextView checkTV;
    private TextView movesTV;
    private TextView playerW;
    private TextView playerB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end_game);
        resultTV = (TextView) findViewById(R.id.Result);
        checkTV = (TextView) findViewById(R.id.Check);
        movesTV = (TextView) findViewById(R.id.Moves);

        String game= getIntent().getExtras().getString("game");
        String winner= getIntent().getExtras().getString("winner");
        String player= getIntent().getExtras().getString("player");
        String check= getIntent().getExtras().getString("check");
        String mosse= getIntent().getExtras().getString("moves");

        if (player.equals(winner)){
            resultTV.setText("HAI VINTO");
            resultTV.setTextColor(Color.parseColor("#adff2f"));
        }else{
            resultTV.setText("HAI PERSO");
            resultTV.setTextColor(Color.parseColor("#ff0000"));
        }
    }
}