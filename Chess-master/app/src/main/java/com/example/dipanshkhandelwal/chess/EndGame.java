package com.example.dipanshkhandelwal.chess;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

public class EndGame extends AppCompatActivity {

    private TextView resultTV;
    private TextView checkTV;
    private TextView movesTV;
    private TextView playerW;
    private TextView playerB;

    private String game;
    private String winner;
    private String player;
    private String playerWh;
    private String playerBl;
    private String moves;
    private String check;
    private FirebaseDatabase database= FirebaseDatabase.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end_game);

        resultTV = (TextView) findViewById(R.id.Result);
        checkTV = (TextView) findViewById(R.id.Check);
        movesTV = (TextView) findViewById(R.id.Moves);
        playerW = (TextView) findViewById(R.id.WPlayer);
        playerB = (TextView) findViewById(R.id.BPlayer);

        game= getIntent().getExtras().getString("game");
        winner= getIntent().getExtras().getString("winner");
        player= getIntent().getExtras().getString("player");



        //
        DatabaseReference db = database.getReference("game/"+ game);

        db.child("W").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                }
                else {
                    String w= (String) task.getResult().getValue();
                    //Toast.makeText(getApplicationContext(), "w ->  "+ w, Toast.LENGTH_LONG).show();
                    playerWh=w;
                    playerW.setText(w);
                }
            }
        });
        db.child("B").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                }
                else {
                    String b= (String) task.getResult().getValue();
                    //Toast.makeText(getApplicationContext(), "b ->  "+ b, Toast.LENGTH_LONG).show();
                    playerBl=b;
                    playerB.setText(b);
                }
            }
        });
        db.child("Nmove").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                }
                else {
                    String b= task.getResult().getValue().toString();
                    //Toast.makeText(getApplicationContext(), "b ->  "+ b, Toast.LENGTH_LONG).show();
                    moves= b;
                    movesTV.setText("Number of moves "+b);
                }
            }
        });

        db.child("Ncheck").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                }
                else {
                    Long b= (Long) task.getResult().getValue();
                    if(b==0){
                        b+=1;
                    }
                    check=Long.toString(b);
                    //Toast.makeText(getApplicationContext(), "b ->  "+ b, Toast.LENGTH_LONG).show();
                    checkTV.setText("Number of checks "+Long.toString(b));
                }
            }
        });
        //

        if (player.equals(winner)){
            resultTV.setText("You Win");
            resultTV.setTextColor(Color.parseColor("#adff2f"));

            /*
            SPAZIO PER INSERIRE LE PARTITE NEL DB
            SOLO CHI VINCE SCRIVE SIL DB
             */




        }else{
            resultTV.setText("You Lost");
            resultTV.setTextColor(Color.parseColor("#ff0000"));
        }

    }
}