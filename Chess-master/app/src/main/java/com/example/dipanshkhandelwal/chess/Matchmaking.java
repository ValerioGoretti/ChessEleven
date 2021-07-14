package com.example.dipanshkhandelwal.chess;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
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
import java.util.Map;
import java.util.Random;

public class Matchmaking extends AppCompatActivity {


    private String nome;
    private String gameId;
    private SharedPreferences shared;
    private String color="null";
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

        //prova();

    }

    private void makeRoom() {
        Intent i=new Intent(Matchmaking.this, Multiplayer.class);
        Random rand = new Random();

        DatabaseReference waitingRoom= database.getReference();
        //waitingRoom.child("WaitingRoom").setValue("bello");


        //VEDERE SE CI SONO GIA WAITING ROOM
        //SE NON CI SONO


        waitingRoom.child("WaitingRoom").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        if(dataSnapshot.getChildrenCount()==0){
                            Toast.makeText(getApplicationContext(), "NON CI SONO STANZE", Toast.LENGTH_LONG).show();
                            gameId=gameID();
                            waitingRoom.child("WaitingRoom").child(gameId);
                            waitingRoom.child("WaitingRoom").child(gameId).child("W").setValue(nome);
                            waitingRoom.child("WaitingRoom").child(gameId).child("ID").setValue(gameId);
                            waitingRoom.child("game").child(gameId).child("stateW").setValue("true");
                            waitingRoom.child("game").child(gameId).child("stateB").setValue("null");
                            waitingRoom.child("game").child(gameId).child("W").setValue(nome);
                            i.putExtra("color","W");
                            color="W";
                            i.putExtra("game", gameId);

                            waitingRoom.child("game").child(gameId).addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    boolean w=false;
                                    boolean b=false;
                                    for (DataSnapshot ds: snapshot.getChildren()){
                                        if (ds.getKey().equals("stateW") && ds.getValue().equals("true") ){
                                            w=true;
                                        }
                                        if (ds.getKey().equals("stateB") && ds.getValue().equals("true") ){
                                            b=true;
                                        }
                                    }
                                    if (w && b){
                                        //cancella Waiting room
                                        waitingRoom.child("WaitingRoom").child(gameId).removeValue();
                                        startActivity(i);
                                        finish();
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                        }else {
                            Toast.makeText(getApplicationContext(), "Ci sono già delle stanze", Toast.LENGTH_LONG).show();
                            for (DataSnapshot ds: dataSnapshot.getChildren()){
                                gameId = (String) ds.child("ID").getValue();
                                break;
                            }
                            waitingRoom.child("WaitingRoom").child(gameId).child("B").setValue(nome);


                            waitingRoom.child("game").child(gameId).child("stateB").setValue("true");
                            waitingRoom.child("game").child(gameId).child("B").setValue(nome);

                            i.putExtra("color","B");
                            color="B";
                            i.putExtra("game", gameId);

                            startActivity(i);
                            finish();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        //handle databaseError
                    }
                });










/*
           ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int totalsize = (int) snapshot.getChildrenCount();
                //Toast.makeText(getApplicationContext(), "num -> " + totalsize, Toast.LENGTH_LONG).show();
                if (totalsize == 0) {
                    gameId=gameID();
                    DatabaseReference room= database.getReference("WaitingRoom/"+ gameId);
                    DatabaseReference playerW= database.getReference("WaitingRoom/"+gameId+"/W");
                    room.setValue(nome);
                    playerW.setValue(nome);
                    Toast.makeText(getApplicationContext(), "Room creata", Toast.LENGTH_LONG).show();

                    Mandare il giocatore bianco alla scacchiera

        i.putExtra("color","W");
        i.putExtra("game", gameId);
        startActivity(i);
        finish();
    }else {
        for(DataSnapshot ds : snapshot.getChildren()) {
            String gameCod = ds.getKey();
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
        }

        //cancella waiting room
        DatabaseReference waitingQuery = database.getReference().child("WaitingRoom").child(gameId);
        waitingQuery.removeValue();



                    Mandare il giocatore nero alla scacchiera

        i.putExtra("color","B");
    }
    startActivity(i);
    finish();
}


    @Override
    public void onCancelled(@NonNull DatabaseError error) {

    }
};
        waitingRoom.addListenerForSingleValueEvent(valueEventListener);
 */

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

    private void prova(){
        DatabaseReference db=database.getReference("waitingRoom");
        db.setValue("ciao");
        //cancella waiting room
        //db.removeValue();
    }
}