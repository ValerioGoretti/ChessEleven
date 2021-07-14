package com.example.dipanshkhandelwal.chess;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.security.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class Matchmaking extends AppCompatActivity {


    private String nome;
    private String gameId;
    private SharedPreferences shared;

    private  ArrayList<String> user = new ArrayList<String>() {
        {
            add("Valerio");
            add("Franco");
            add("Peppe");
            add("Bello");
            add("Brutto");
            add("Giovanni");
            add("Pirullo");
            add("Lollo");
            add("BlaBla");
        }
    };

    private FirebaseDatabase database= FirebaseDatabase.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matchmaking);
        shared= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        this.nome=shared.getString("username","username");
        makeRoom();


    }

    private void makeRoom() {
        Intent i=new Intent(Matchmaking.this, Multiplayer.class);
        Random rand = new Random();
        DatabaseReference waitingRoom= database.getReference("WaitingRoom");

        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int totalsize = (int) snapshot.getChildrenCount();
                //Toast.makeText(getApplicationContext(), "num -> " + totalsize, Toast.LENGTH_LONG).show();
                if (totalsize == 0) {

                    Toast.makeText(getApplicationContext(), "Bianco", Toast.LENGTH_LONG).show();

                    gameId=gameID();

                    Toast.makeText(getApplicationContext(), " "+ gameId, Toast.LENGTH_LONG).show();

                    DatabaseReference room= database.getReference("WaitingRoom/"+ gameId);
                    DatabaseReference playerW= database.getReference("WaitingRoom/"+gameId+"/W");
                    room.setValue(nome);
                    playerW.setValue(nome);
                    //Toast.makeText(getApplicationContext(), "Room creata", Toast.LENGTH_LONG).show();
                    /*
                    Mandare il giocatore bianco alla scacchiera
                    */
                    i.putExtra("color","W");
                    i.putExtra("game", gameId);
                    DatabaseReference stateW = database.getReference("game/"+ gameId+"/stateW");
                    DatabaseReference stateB= database.getReference("game/"+ gameId+"/stateB");
                    stateW.setValue("true");
                    stateB.setValue("null");
                    //startActivity(i);
                    //finish();
                }else {
                    for(DataSnapshot ds : snapshot.getChildren()) {
                        Toast.makeText(getApplicationContext(), "nero", Toast.LENGTH_LONG).show();
                        //inizializza il tuo stato
                        String gameCod = ds.getKey();

                        Toast.makeText(getApplicationContext(), " "+gameCod, Toast.LENGTH_LONG).show();
                        DatabaseReference stateB= database.getReference("game/"+ gameCod+"/stateB");
                        stateB.setValue("true");

                        DatabaseReference playerB= database.getReference("WaitingRoom/"+gameCod+"/B");
                        DatabaseReference playerGameB= database.getReference("game/"+ gameCod+"/B");
                        i.putExtra("game", gameCod);
                        playerB.setValue(nome);
                        playerGameB.setValue(nome);


                        DatabaseReference playerGameW= database.getReference("game/"+ gameCod+"/W");
                        //Toast.makeText(getApplicationContext(), " " + ds.getChildrenCount(), Toast.LENGTH_LONG).show();
                        for (DataSnapshot d: ds.getChildren()){
                            if (d.getKey().equals("W")){
                                playerGameW.setValue(d.getValue());
                            }
                        }

                        i.putExtra("color","B");
                        startActivity(i);
                        finish();
                        break;
                    }


                    /*
                    Mandare il giocatore nero alla scacchiera
                     */

                }

                Query waitingQuery = database.getReference().child("WaitingRoom");

                waitingQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot sn: dataSnapshot.getChildren()) {
                            sn.getRef().removeValue();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

                DatabaseReference db = database.getReference("game/"+ gameId);

                db.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        boolean w=false;
                        boolean b=false;
                        for (DataSnapshot sn: dataSnapshot.getChildren()){
                            if (sn.getKey().equals("stateW") && sn.getValue().equals("true")){
                                //bianco true
                                w=true;
                            }
                            if (sn.getKey().equals("stateB") && sn.getValue().equals("true")){
                                //nero true
                                b=true;
                            }
                        }
                        if (w && b){
                            Toast.makeText(getApplicationContext(), "PARTITA INIZIATA", Toast.LENGTH_LONG).show();
                            startActivity(i);
                            finish();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError error) {
                        // Failed to read value
                        Log.e("Hey", "Failed to read app title value.", error.toException());
                    }
                });

                //startActivity(i);
                //finish();
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        waitingRoom.addValueEventListener(valueEventListener);
    }


    public void nome(View view) {
        Random rand = new Random();
        int ind= rand.nextInt(4);
        this.nome=user.get(ind);
    }

    private String gameID(){
        char[] chars = "abcdefghijklmnopqrstuvwxyz".toCharArray();
        StringBuilder sb = new StringBuilder(20);
        Random random = new Random();
        for (int i = 0; i < 20; i++) {
            char c = chars[random.nextInt(chars.length)];
            sb.append(c);
        }
        return  sb.toString();
    }
}