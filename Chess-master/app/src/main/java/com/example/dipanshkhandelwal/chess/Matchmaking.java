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
                                        waitingRoom.child("game").child(gameId).removeEventListener(this);
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

    }


    protected void onPause() {
        super.onPause();
        DatabaseReference mDatabase = database.getReference("WaitingRoom/" + gameId);
        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (!snapshot.hasChild("B")) {
                    Toast.makeText(getApplicationContext(), "sei solo", Toast.LENGTH_LONG).show();
                    mDatabase.removeValue();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
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