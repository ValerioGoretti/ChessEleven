package com.example.dipanshkhandelwal.chess;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.bhlangonijr.chesslib.Board;
import com.github.bhlangonijr.chesslib.Piece;
import com.github.bhlangonijr.chesslib.Side;
import com.github.bhlangonijr.chesslib.Square;
import com.github.bhlangonijr.chesslib.move.Move;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class Multiplayer extends AppCompatActivity  implements View.OnClickListener{

    public Square c1=null;
    public Square c2=null;
    public Square click = null;
    public TextView game_over;
    public TextView[][] DisplayBoard = new TextView[8][8];
    public TextView[][] DisplayBoardBackground = new TextView[8][8];
    public TextView[][] DisplayBoardBackgroundSelected = new TextView[8][8];
    public LinearLayout pawn_choices;
    public Board board;
    private Piece lastChoice=null;
    private TextView returnedText;
    private Suggestions suggestions=new Suggestions();
    private Player player= new Player();
    private boolean ismyturn=true;
    private LinearLayout settingsMenu;
    private boolean out;
    private String gameId;
    private String colorPiece;

    private FirebaseDatabase database= FirebaseDatabase.getInstance();
    private DatabaseReference boardDb;
    //private DatabaseReference lastMoveDb;
    private DatabaseReference nMove;
    private DatabaseReference ncheck;
    private DatabaseReference fen;
    private TextView col;
<<<<<<< HEAD
<<<<<<< HEAD
=======
>>>>>>> parent of 7f8aed8 (fix multiplayer)


    //DONE: Vedere perchè da sempre true quando giochi con il bianco
    //DONE: Vedere su youtube come si refresha la scacchiera, in che punto leggere i dati dal db e aggiornare la scacchiera dell altra persona

    //TODO: AGGIORNARE NEL LISTNER NELL'ONCREATE ANCHE L'ENVIRONMENT            IDEA: METTERE UN CAMPO PER L'ULTIMA MOSSA COSì CHE LA AGGIORNA QUANDO LO VEDE (?)

<<<<<<< HEAD
=======
    private boolean blackOnline = false;
>>>>>>> parent of 5e9889f (Update Multiplayer.java)
=======
>>>>>>> parent of 7f8aed8 (fix multiplayer)
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }
        setContentView(R.layout.activity_multiplayer);

        col=(TextView) findViewById(R.id.buttonColor);
        game_over = (TextView) findViewById(R.id.game_over);
        pawn_choices = (LinearLayout) findViewById(R.id.pawn_chioces);
        game_over.setVisibility(View.INVISIBLE);
        pawn_choices.setVisibility(View.INVISIBLE);
        settingsMenu=(LinearLayout) findViewById(R.id.settingsMenu);
        returnedText =(TextView) findViewById(R.id.textAssistent);

        colorPiece= getIntent().getExtras().getString("color");
        gameId= getIntent().getExtras().getString("game");

        System.out.println("GAME ID NEL Create " + gameId);

        col.setText(colorPiece);
        Toast.makeText(getApplicationContext(), "il tuo colore è -> "+ colorPiece, Toast.LENGTH_LONG).show();
        Toast.makeText(getApplicationContext(), "id partita -> "+ gameId, Toast.LENGTH_LONG).show();

        DatabaseReference db = database.getReference("game/"+ gameId);
        db.child("board").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String appTitle = dataSnapshot.getValue().toString();
                Log.e("Hey is changed ", appTitle);
                System.out.println("Hey is changed "+ appTitle);
                //TODO:  FARE IL TODO A RIGA 67 QUI -> board.loadFromFen(appTitle);
                moveBoard(parseBoard(appTitle));
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.e("Hey", "Failed to read app title value.", error.toException());
            }
        });

        db.child("fen").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String appTitle = dataSnapshot.getValue().toString();
                board.loadFromFen(appTitle);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.e("Hey", "Failed to read app title value.", error.toException());
            }
        });
        initializeBoard();
    }


    private void initializeBoard() {
        board = new Board();

        if(0==0){
            DisplayBoard[0][0] = (TextView) findViewById(R.id.R00);
            DisplayBoard[0][0].setBackgroundResource(R.drawable.wrook);
            DisplayBoard[1][0] = (TextView) findViewById(R.id.R10);
            DisplayBoard[1][0].setBackgroundResource(R.drawable.wknight);
            DisplayBoard[2][0] = (TextView) findViewById(R.id.R20);
            DisplayBoard[2][0].setBackgroundResource(R.drawable.wbishop);
            DisplayBoard[3][0] = (TextView) findViewById(R.id.R30);
            DisplayBoard[3][0].setBackgroundResource(R.drawable.wqueen);
            DisplayBoard[4][0] = (TextView) findViewById(R.id.R40);
            DisplayBoard[4][0].setBackgroundResource(R.drawable.wking);
            DisplayBoard[5][0] = (TextView) findViewById(R.id.R50);
            DisplayBoard[5][0].setBackgroundResource(R.drawable.wbishop);
            DisplayBoard[6][0] = (TextView) findViewById(R.id.R60);
            DisplayBoard[6][0].setBackgroundResource(R.drawable.wknight);
            DisplayBoard[7][0] = (TextView) findViewById(R.id.R70);
            DisplayBoard[7][0].setBackgroundResource(R.drawable.wrook);


            DisplayBoard[0][1] = (TextView) findViewById(R.id.R01);
            DisplayBoard[0][1].setBackgroundResource(R.drawable.wpawn);
            DisplayBoard[1][1] = (TextView) findViewById(R.id.R11);
            DisplayBoard[1][1].setBackgroundResource(R.drawable.wpawn);
            DisplayBoard[2][1] = (TextView) findViewById(R.id.R21);
            DisplayBoard[2][1].setBackgroundResource(R.drawable.wpawn);
            DisplayBoard[3][1] = (TextView) findViewById(R.id.R31);
            DisplayBoard[3][1].setBackgroundResource(R.drawable.wpawn);
            DisplayBoard[4][1] = (TextView) findViewById(R.id.R41);
            DisplayBoard[4][1].setBackgroundResource(R.drawable.wpawn);
            DisplayBoard[5][1] = (TextView) findViewById(R.id.R51);
            DisplayBoard[5][1].setBackgroundResource(R.drawable.wpawn);
            DisplayBoard[6][1] = (TextView) findViewById(R.id.R61);
            DisplayBoard[6][1].setBackgroundResource(R.drawable.wpawn);
            DisplayBoard[7][1] = (TextView) findViewById(R.id.R71);
            DisplayBoard[7][1].setBackgroundResource(R.drawable.wpawn);

            DisplayBoard[0][2] = (TextView) findViewById(R.id.R02);
            DisplayBoard[1][2] = (TextView) findViewById(R.id.R12);
            DisplayBoard[2][2] = (TextView) findViewById(R.id.R22);
            DisplayBoard[3][2] = (TextView) findViewById(R.id.R32);
            DisplayBoard[4][2] = (TextView) findViewById(R.id.R42);
            DisplayBoard[5][2] = (TextView) findViewById(R.id.R52);
            DisplayBoard[6][2] = (TextView) findViewById(R.id.R62);
            DisplayBoard[7][2] = (TextView) findViewById(R.id.R72);
            DisplayBoard[0][3] = (TextView) findViewById(R.id.R03);
            DisplayBoard[1][3] = (TextView) findViewById(R.id.R13);
            DisplayBoard[2][3] = (TextView) findViewById(R.id.R23);
            DisplayBoard[3][3] = (TextView) findViewById(R.id.R33);
            DisplayBoard[4][3] = (TextView) findViewById(R.id.R43);
            DisplayBoard[5][3] = (TextView) findViewById(R.id.R53);
            DisplayBoard[6][3] = (TextView) findViewById(R.id.R63);
            DisplayBoard[7][3] = (TextView) findViewById(R.id.R73);
            DisplayBoard[0][4] = (TextView) findViewById(R.id.R04);
            DisplayBoard[1][4] = (TextView) findViewById(R.id.R14);
            DisplayBoard[2][4] = (TextView) findViewById(R.id.R24);
            DisplayBoard[3][4] = (TextView) findViewById(R.id.R34);
            DisplayBoard[4][4] = (TextView) findViewById(R.id.R44);
            DisplayBoard[5][4] = (TextView) findViewById(R.id.R54);
            DisplayBoard[6][4] = (TextView) findViewById(R.id.R64);
            DisplayBoard[7][4] = (TextView) findViewById(R.id.R74);
            DisplayBoard[0][5] = (TextView) findViewById(R.id.R05);
            DisplayBoard[1][5] = (TextView) findViewById(R.id.R15);
            DisplayBoard[2][5] = (TextView) findViewById(R.id.R25);
            DisplayBoard[3][5] = (TextView) findViewById(R.id.R35);
            DisplayBoard[4][5] = (TextView) findViewById(R.id.R45);
            DisplayBoard[5][5] = (TextView) findViewById(R.id.R55);
            DisplayBoard[6][5] = (TextView) findViewById(R.id.R65);
            DisplayBoard[7][5] = (TextView) findViewById(R.id.R75);
            DisplayBoard[0][6] = (TextView) findViewById(R.id.R06);
            DisplayBoard[0][6].setBackgroundResource(R.drawable.bpawn);
            DisplayBoard[1][6] = (TextView) findViewById(R.id.R16);
            DisplayBoard[1][6].setBackgroundResource(R.drawable.bpawn);
            DisplayBoard[2][6] = (TextView) findViewById(R.id.R26);
            DisplayBoard[2][6].setBackgroundResource(R.drawable.bpawn);
            DisplayBoard[3][6] = (TextView) findViewById(R.id.R36);
            DisplayBoard[3][6].setBackgroundResource(R.drawable.bpawn);
            DisplayBoard[4][6] = (TextView) findViewById(R.id.R46);
            DisplayBoard[4][6].setBackgroundResource(R.drawable.bpawn);
            DisplayBoard[5][6] = (TextView) findViewById(R.id.R56);
            DisplayBoard[5][6].setBackgroundResource(R.drawable.bpawn);
            DisplayBoard[6][6] = (TextView) findViewById(R.id.R66);
            DisplayBoard[6][6].setBackgroundResource(R.drawable.bpawn);
            DisplayBoard[7][6] = (TextView) findViewById(R.id.R76);
            DisplayBoard[7][6].setBackgroundResource(R.drawable.bpawn);
            DisplayBoard[0][7] = (TextView) findViewById(R.id.R07);
            DisplayBoard[0][7].setBackgroundResource(R.drawable.brook);
            DisplayBoard[1][7] = (TextView) findViewById(R.id.R17);
            DisplayBoard[1][7].setBackgroundResource(R.drawable.bknight);
            DisplayBoard[2][7] = (TextView) findViewById(R.id.R27);
            DisplayBoard[2][7].setBackgroundResource(R.drawable.bbishop);
            DisplayBoard[3][7] = (TextView) findViewById(R.id.R37);
            DisplayBoard[3][7].setBackgroundResource(R.drawable.bqueen);
            DisplayBoard[4][7] = (TextView) findViewById(R.id.R47);
            DisplayBoard[4][7].setBackgroundResource(R.drawable.bking);
            DisplayBoard[5][7] = (TextView) findViewById(R.id.R57);
            DisplayBoard[5][7].setBackgroundResource(R.drawable.bbishop);
            DisplayBoard[6][7] = (TextView) findViewById(R.id.R67);
            DisplayBoard[6][7].setBackgroundResource(R.drawable.bknight);
            DisplayBoard[7][7] = (TextView) findViewById(R.id.R77);
            DisplayBoard[7][7].setBackgroundResource(R.drawable.brook);

            DisplayBoardBackground[0][0] = (TextView) findViewById(R.id.R000);
            DisplayBoardBackground[1][0] = (TextView) findViewById(R.id.R010);
            DisplayBoardBackground[2][0] = (TextView) findViewById(R.id.R020);
            DisplayBoardBackground[3][0] = (TextView) findViewById(R.id.R030);
            DisplayBoardBackground[4][0] = (TextView) findViewById(R.id.R040);
            DisplayBoardBackground[5][0] = (TextView) findViewById(R.id.R050);
            DisplayBoardBackground[6][0] = (TextView) findViewById(R.id.R060);
            DisplayBoardBackground[7][0] = (TextView) findViewById(R.id.R070);

            DisplayBoardBackground[0][1] = (TextView) findViewById(R.id.R001);
            DisplayBoardBackground[1][1] = (TextView) findViewById(R.id.R011);
            DisplayBoardBackground[2][1] = (TextView) findViewById(R.id.R021);
            DisplayBoardBackground[3][1] = (TextView) findViewById(R.id.R031);
            DisplayBoardBackground[4][1] = (TextView) findViewById(R.id.R041);
            DisplayBoardBackground[5][1] = (TextView) findViewById(R.id.R051);
            DisplayBoardBackground[6][1] = (TextView) findViewById(R.id.R061);
            DisplayBoardBackground[7][1] = (TextView) findViewById(R.id.R071);

            DisplayBoardBackground[0][2] = (TextView) findViewById(R.id.R002);
            DisplayBoardBackground[1][2] = (TextView) findViewById(R.id.R012);
            DisplayBoardBackground[2][2] = (TextView) findViewById(R.id.R022);
            DisplayBoardBackground[3][2] = (TextView) findViewById(R.id.R032);
            DisplayBoardBackground[4][2] = (TextView) findViewById(R.id.R042);
            DisplayBoardBackground[5][2] = (TextView) findViewById(R.id.R052);
            DisplayBoardBackground[6][2] = (TextView) findViewById(R.id.R062);
            DisplayBoardBackground[7][2] = (TextView) findViewById(R.id.R072);

            DisplayBoardBackground[0][3] = (TextView) findViewById(R.id.R003);
            DisplayBoardBackground[1][3] = (TextView) findViewById(R.id.R013);
            DisplayBoardBackground[2][3] = (TextView) findViewById(R.id.R023);
            DisplayBoardBackground[3][3] = (TextView) findViewById(R.id.R033);
            DisplayBoardBackground[4][3] = (TextView) findViewById(R.id.R043);
            DisplayBoardBackground[5][3] = (TextView) findViewById(R.id.R053);
            DisplayBoardBackground[6][3] = (TextView) findViewById(R.id.R063);
            DisplayBoardBackground[7][3] = (TextView) findViewById(R.id.R073);

            DisplayBoardBackground[0][4] = (TextView) findViewById(R.id.R004);
            DisplayBoardBackground[1][4] = (TextView) findViewById(R.id.R014);
            DisplayBoardBackground[2][4] = (TextView) findViewById(R.id.R024);
            DisplayBoardBackground[3][4] = (TextView) findViewById(R.id.R034);
            DisplayBoardBackground[4][4] = (TextView) findViewById(R.id.R044);
            DisplayBoardBackground[5][4] = (TextView) findViewById(R.id.R054);
            DisplayBoardBackground[6][4] = (TextView) findViewById(R.id.R064);
            DisplayBoardBackground[7][4] = (TextView) findViewById(R.id.R074);

            DisplayBoardBackground[0][5] = (TextView) findViewById(R.id.R005);
            DisplayBoardBackground[1][5] = (TextView) findViewById(R.id.R015);
            DisplayBoardBackground[2][5] = (TextView) findViewById(R.id.R025);
            DisplayBoardBackground[3][5] = (TextView) findViewById(R.id.R035);
            DisplayBoardBackground[4][5] = (TextView) findViewById(R.id.R045);
            DisplayBoardBackground[5][5] = (TextView) findViewById(R.id.R055);
            DisplayBoardBackground[6][5] = (TextView) findViewById(R.id.R065);
            DisplayBoardBackground[7][5] = (TextView) findViewById(R.id.R075);

            DisplayBoardBackground[0][6] = (TextView) findViewById(R.id.R006);
            DisplayBoardBackground[1][6] = (TextView) findViewById(R.id.R016);
            DisplayBoardBackground[2][6] = (TextView) findViewById(R.id.R026);
            DisplayBoardBackground[3][6] = (TextView) findViewById(R.id.R036);
            DisplayBoardBackground[4][6] = (TextView) findViewById(R.id.R046);
            DisplayBoardBackground[5][6] = (TextView) findViewById(R.id.R056);
            DisplayBoardBackground[6][6] = (TextView) findViewById(R.id.R066);
            DisplayBoardBackground[7][6] = (TextView) findViewById(R.id.R076);

            DisplayBoardBackground[0][7] = (TextView) findViewById(R.id.R007);
            DisplayBoardBackground[1][7] = (TextView) findViewById(R.id.R017);
            DisplayBoardBackground[2][7] = (TextView) findViewById(R.id.R027);
            DisplayBoardBackground[3][7] = (TextView) findViewById(R.id.R037);
            DisplayBoardBackground[4][7] = (TextView) findViewById(R.id.R047);
            DisplayBoardBackground[5][7] = (TextView) findViewById(R.id.R057);
            DisplayBoardBackground[6][7] = (TextView) findViewById(R.id.R067);
            DisplayBoardBackground[7][7] = (TextView) findViewById(R.id.R077);

        /*
        -----------------
         */
            DisplayBoardBackgroundSelected[0][0] = (TextView) findViewById(R.id.RO000);
            DisplayBoardBackgroundSelected[1][0] = (TextView) findViewById(R.id.RO010);
            DisplayBoardBackgroundSelected[2][0] = (TextView) findViewById(R.id.RO020);
            DisplayBoardBackgroundSelected[3][0] = (TextView) findViewById(R.id.RO030);
            DisplayBoardBackgroundSelected[4][0] = (TextView) findViewById(R.id.RO040);
            DisplayBoardBackgroundSelected[5][0] = (TextView) findViewById(R.id.RO050);
            DisplayBoardBackgroundSelected[6][0] = (TextView) findViewById(R.id.RO060);
            DisplayBoardBackgroundSelected[7][0] = (TextView) findViewById(R.id.RO070);

            DisplayBoardBackgroundSelected[0][1] = (TextView) findViewById(R.id.RO001);
            DisplayBoardBackgroundSelected[1][1] = (TextView) findViewById(R.id.RO011);
            DisplayBoardBackgroundSelected[2][1] = (TextView) findViewById(R.id.RO021);
            DisplayBoardBackgroundSelected[3][1] = (TextView) findViewById(R.id.RO031);
            DisplayBoardBackgroundSelected[4][1] = (TextView) findViewById(R.id.RO041);
            DisplayBoardBackgroundSelected[5][1] = (TextView) findViewById(R.id.RO051);
            DisplayBoardBackgroundSelected[6][1] = (TextView) findViewById(R.id.RO061);
            DisplayBoardBackgroundSelected[7][1] = (TextView) findViewById(R.id.RO071);

            DisplayBoardBackgroundSelected[0][2] = (TextView) findViewById(R.id.RO002);
            DisplayBoardBackgroundSelected[1][2] = (TextView) findViewById(R.id.RO012);
            DisplayBoardBackgroundSelected[2][2] = (TextView) findViewById(R.id.RO022);
            DisplayBoardBackgroundSelected[3][2] = (TextView) findViewById(R.id.RO032);
            DisplayBoardBackgroundSelected[4][2] = (TextView) findViewById(R.id.RO042);
            DisplayBoardBackgroundSelected[5][2] = (TextView) findViewById(R.id.RO052);
            DisplayBoardBackgroundSelected[6][2] = (TextView) findViewById(R.id.RO062);
            DisplayBoardBackgroundSelected[7][2] = (TextView) findViewById(R.id.RO072);

            DisplayBoardBackgroundSelected[0][3] = (TextView) findViewById(R.id.RO003);
            DisplayBoardBackgroundSelected[1][3] = (TextView) findViewById(R.id.RO013);
            DisplayBoardBackgroundSelected[2][3] = (TextView) findViewById(R.id.RO023);
            DisplayBoardBackgroundSelected[3][3] = (TextView) findViewById(R.id.RO033);
            DisplayBoardBackgroundSelected[4][3] = (TextView) findViewById(R.id.RO043);
            DisplayBoardBackgroundSelected[5][3] = (TextView) findViewById(R.id.RO053);
            DisplayBoardBackgroundSelected[6][3] = (TextView) findViewById(R.id.RO063);
            DisplayBoardBackgroundSelected[7][3] = (TextView) findViewById(R.id.RO073);

            DisplayBoardBackgroundSelected[0][4] = (TextView) findViewById(R.id.RO004);
            DisplayBoardBackgroundSelected[1][4] = (TextView) findViewById(R.id.RO014);
            DisplayBoardBackgroundSelected[2][4] = (TextView) findViewById(R.id.RO024);
            DisplayBoardBackgroundSelected[3][4] = (TextView) findViewById(R.id.RO034);
            DisplayBoardBackgroundSelected[4][4] = (TextView) findViewById(R.id.RO044);
            DisplayBoardBackgroundSelected[5][4] = (TextView) findViewById(R.id.RO054);
            DisplayBoardBackgroundSelected[6][4] = (TextView) findViewById(R.id.RO064);
            DisplayBoardBackgroundSelected[7][4] = (TextView) findViewById(R.id.RO074);

            DisplayBoardBackgroundSelected[0][5] = (TextView) findViewById(R.id.RO005);
            DisplayBoardBackgroundSelected[1][5] = (TextView) findViewById(R.id.RO015);
            DisplayBoardBackgroundSelected[2][5] = (TextView) findViewById(R.id.RO025);
            DisplayBoardBackgroundSelected[3][5] = (TextView) findViewById(R.id.RO035);
            DisplayBoardBackgroundSelected[4][5] = (TextView) findViewById(R.id.RO045);
            DisplayBoardBackgroundSelected[5][5] = (TextView) findViewById(R.id.RO055);
            DisplayBoardBackgroundSelected[6][5] = (TextView) findViewById(R.id.RO065);
            DisplayBoardBackgroundSelected[7][5] = (TextView) findViewById(R.id.RO075);

            DisplayBoardBackgroundSelected[0][6] = (TextView) findViewById(R.id.RO006);
            DisplayBoardBackgroundSelected[1][6] = (TextView) findViewById(R.id.RO016);
            DisplayBoardBackgroundSelected[2][6] = (TextView) findViewById(R.id.RO026);
            DisplayBoardBackgroundSelected[3][6] = (TextView) findViewById(R.id.RO036);
            DisplayBoardBackgroundSelected[4][6] = (TextView) findViewById(R.id.RO046);
            DisplayBoardBackgroundSelected[5][6] = (TextView) findViewById(R.id.RO056);
            DisplayBoardBackgroundSelected[6][6] = (TextView) findViewById(R.id.RO066);
            DisplayBoardBackgroundSelected[7][6] = (TextView) findViewById(R.id.RO076);

            DisplayBoardBackgroundSelected[0][7] = (TextView) findViewById(R.id.RO007);
            DisplayBoardBackgroundSelected[1][7] = (TextView) findViewById(R.id.RO017);
            DisplayBoardBackgroundSelected[2][7] = (TextView) findViewById(R.id.RO027);
            DisplayBoardBackgroundSelected[3][7] = (TextView) findViewById(R.id.RO037);
            DisplayBoardBackgroundSelected[4][7] = (TextView) findViewById(R.id.RO047);
            DisplayBoardBackgroundSelected[5][7] = (TextView) findViewById(R.id.RO057);
            DisplayBoardBackgroundSelected[6][7] = (TextView) findViewById(R.id.RO067);
            DisplayBoardBackgroundSelected[7][7] = (TextView) findViewById(R.id.RO077);
        }

        System.out.println("GAME ID NEL INITIALIZE " + gameId);
        boardDb= database.getReference("game/"+gameId+"/board");
        fen= database.getReference("game/"+gameId+"/fen");
        //lastMoveDb= database.getReference("game/"+gameId+"/last");
        nMove= database.getReference("game/"+gameId+"/Nmove");
        ncheck= database.getReference("game/"+gameId+"/Ncheck");

        boardDb.setValue(board.toString());
        fen.setValue(board.getFen());
        //lastMoveDb.setValue("0");
        nMove.setValue(0);
        ncheck.setValue(0);


    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.R00:
                click = Square.A1;
                break;
            case R.id.R10:
                click = Square.B1;
                break;
            case R.id.R20:
                click = Square.C1;
                break;
            case R.id.R30:
                click = Square.D1;
                break;
            case R.id.R40:
                click = Square.E1;
                break;
            case R.id.R50:
                click = Square.F1;
                break;
            case R.id.R60:
                click = Square.G1;
                break;
            case R.id.R70:
                click = Square.H1;
                break;

            case R.id.R01:
                click = Square.A2;
                break;
            case R.id.R11:
                click = Square.B2;
                break;
            case R.id.R21:
                click = Square.C2;
                break;
            case R.id.R31:
                click = Square.D2;
                break;
            case R.id.R41:
                click = Square.E2;
                break;
            case R.id.R51:
                click = Square.F2;
                break;
            case R.id.R61:
                click = Square.G2;
                break;
            case R.id.R71:
                click = Square.H2;
                break;

            case R.id.R02:
                click = Square.A3;
                break;
            case R.id.R12:
                click = Square.B3;
                break;
            case R.id.R22:
                click = Square.C3;
                break;
            case R.id.R32:
                click = Square.D3;
                break;
            case R.id.R42:
                click = Square.E3;
                break;
            case R.id.R52:
                click = Square.F3;
                break;
            case R.id.R62:
                click = Square.G3;
                break;
            case R.id.R72:
                click = Square.H3;
                break;

            case R.id.R03:
                click = Square.A4;
                break;
            case R.id.R13:
                click = Square.B4;
                break;
            case R.id.R23:
                click = Square.C4;
                break;
            case R.id.R33:
                click = Square.D4;
                break;
            case R.id.R43:
                click = Square.E4;
                break;
            case R.id.R53:
                click = Square.F4;
                break;
            case R.id.R63:
                click = Square.G4;
                break;
            case R.id.R73:
                click = Square.H4;
                break;

            case R.id.R04:
                click = Square.A5;
                break;
            case R.id.R14:
                click = Square.B5;
                break;
            case R.id.R24:
                click = Square.C5;
                break;
            case R.id.R34:
                click = Square.D5;
                break;
            case R.id.R44:
                click = Square.E5;
                break;
            case R.id.R54:
                click = Square.F5;
                break;
            case R.id.R64:
                click = Square.G5;
                break;
            case R.id.R74:
                click = Square.H5;
                break;

            case R.id.R05:
                click = Square.A6;
                break;
            case R.id.R15:
                click = Square.B6;
                break;
            case R.id.R25:
                click = Square.C6;
                break;
            case R.id.R35:
                click = Square.D6;
                break;
            case R.id.R45:
                click = Square.E6;
                break;
            case R.id.R55:
                click = Square.F6;
                break;
            case R.id.R65:
                click = Square.G6;
                break;
            case R.id.R75:
                click = Square.H6;
                break;

            case R.id.R06:
                click = Square.A7;
                break;
            case R.id.R16:
                click = Square.B7;
                break;
            case R.id.R26:
                click = Square.C7;
                break;
            case R.id.R36:
                click = Square.D7;
                break;
            case R.id.R46:
                click = Square.E7;
                break;
            case R.id.R56:
                click = Square.F7;
                break;
            case R.id.R66:
                click = Square.G7;
                break;
            case R.id.R76:
                click = Square.H7;
                break;

            case R.id.R07:
                click = Square.A8;
                break;
            case R.id.R17:
                click = Square.B8;
                break;
            case R.id.R27:
                click = Square.C8;
                break;
            case R.id.R37:
                click = Square.D8;
                break;
            case R.id.R47:
                click = Square.E8;
                break;
            case R.id.R57:
                click = Square.F8;
                break;
            case R.id.R67:
                click = Square.G8;
                break;
            case R.id.R77:
                click = Square.H8;
                break;
        }

        ArrayList<Move> allowMoves = new ArrayList<>();

        if(ismyturn()){
            if (c1==null) {
                c1=click;
                colorMove(c1);

            } else {
                if (c1!=null && c2==null) {
                    c2=click;
                    Move mo=new Move(c1,c2);
                    if(isaMove(mo)){
                        if(board.getPiece(mo.getFrom())== Piece.WHITE_PAWN && (mo.getTo()==Square.A8 || mo.getTo()==Square.B8 || mo.getTo()==Square.C8 || mo.getTo()==Square.D8 || mo.getTo()==Square.E8 || mo.getTo()==Square.F8 || mo.getTo()==Square.G8 || mo.getTo()==Square.H8)){
                            pawn_choices.setVisibility(View.VISIBLE);
                            return;
                        }else {
                            if(board.getPiece(mo.getFrom())== Piece.BLACK_PAWN && (mo.getTo()==Square.A1 || mo.getTo()==Square.B1 || mo.getTo()==Square.C1 || mo.getTo()==Square.D1 || mo.getTo()==Square.E1 || mo.getTo()==Square.F1 || mo.getTo()==Square.G1 || mo.getTo()==Square.H1)){
                                pawn_choices.setVisibility(View.VISIBLE);
                                return;
                            }
                        }
                        board.doMove(mo);
                        //INIZIO: INCREMENTO MOSSE
                        DatabaseReference db = database.getReference("game/"+ gameId);
                        db.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                for (DataSnapshot sn: dataSnapshot.getChildren()) {
                                    if(sn.getKey().equals("Nmove")){
                                        DatabaseReference move = database.getReference("game/"+ gameId+"/Nmove");
                                        Long s= (Long) sn.getValue();
                                        move.setValue(s+1);
                                    }
                                    if(sn.getKey().equals("board")){
                                        DatabaseReference bo = database.getReference("game/"+ gameId+"/board");
                                        bo.setValue(board.toString());

                                    }
                                    if(sn.getKey().equals("fen")){
                                        DatabaseReference bo = database.getReference("game/"+ gameId+"/fen");
                                        bo.setValue(board.getFen());

                                    }
                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
                        //FINE: INCREMENTO MOSSE

                        clearBoardColor();
                        moveBoard(parseBoard());

                        if (board.isKingAttacked()){
                            colorRedking(parseBoard());
                        }
                        if (board.isMated() || board.isDraw() || board.isStaleMate() || board.isInsufficientMaterial() || board.isRepetition()){

                            game_over.setVisibility(View.VISIBLE);
                            Intent intent = getIntent();
                            finish();
                            startActivity(intent);
                        }

                        if (board.isMated() || board.isDraw() || board.isStaleMate() || board.isInsufficientMaterial() || board.isRepetition()){
                            //System.out.println("scacco matto or Draw or stallo");
                            game_over.setVisibility(View.VISIBLE);
                            Intent intent = getIntent();
                            finish();
                            startActivity(intent);
                        }
                        clearDuble();
                    }else {
                        clearBoardColor();
                        c1=c2;
                        c2=null;
                        c1=click;
                        colorMove(c1);
                    }
                }
            }
            if (board.isMated() || board.isDraw() || board.isStaleMate() || board.isInsufficientMaterial() || board.isRepetition()){
                //System.out.println("scacco matto or Draw or stallo");
                game_over.setVisibility(View.VISIBLE);
                Intent intent = getIntent();
                finish();
                startActivity(intent);
            }
        }

    }

    private boolean ismyturn() {

        //INIZIO: INCREMENTO MOSSE
        DatabaseReference db = database.getReference("game/"+ gameId);
        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot sn: dataSnapshot.getChildren()) {
                    if(sn.getKey().equals("Nmove")){
                        long m = (long) sn.getValue();
                        System.out.println("il numero di mosse fatte è "+ m);
                        switch (colorPiece){
                            case "W":
                                if(m %2==0){
                                    System.out.println("il giocatore è bianco ed è il suo turno");
                                    out=true;
                                }
                                else {
                                    System.out.println("il giocatore è bianco ed NON è il suo turno");
                                    out= false;
                                }
                                break;
                            case "B":
                                if(m %2!=0){
                                    System.out.println("il giocatore è nero ed è il suo turno");
                                    out= true;
                                }
                                else{
                                    System.out.println("il giocatore è nero ed NON è il suo turno");
                                    out= false;
                                }
                                break;
                            default:
                                throw new IllegalStateException("Unexpected value: " + colorPiece);
                        }
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        System.out.println(out);
        return  out;
        //FINE: INCREMENTO MOSSE
        //return  true;
        /*
        System.out.println("è pari o dispari? " + move[0] %2);

         */

    }


    public void pawnChoice (View view){
        pawn_choices.setVisibility(View.INVISIBLE);
        returnedText.setText("Please chose among:\n'Regina'\n'Alfiere'\n'Torre'\n'Cavallo'");
        TextView t= (TextView) view;
        switch (t.getText().toString()){
            case "Queen":   lastChoice = (board.getSideToMove().equals(Side.WHITE)) ?  Piece.WHITE_QUEEN :  Piece.BLACK_QUEEN;
                break;
            case "Bishop":   lastChoice = (board.getSideToMove().equals(Side.WHITE)) ?  Piece.WHITE_BISHOP :  Piece.BLACK_BISHOP;
                break;
            case "Rock":   lastChoice = (board.getSideToMove().equals(Side.WHITE)) ?  Piece.WHITE_ROOK:  Piece.BLACK_ROOK;
                break;
            case "Knight":   lastChoice = (board.getSideToMove().equals(Side.WHITE)) ?  Piece.WHITE_KNIGHT :  Piece.BLACK_KNIGHT;
                break;
        }

        board.doMove(new Move(c1,c2,lastChoice));
        //INIZIO: INCREMENTO MOSSE
        DatabaseReference db = database.getReference("game/"+ gameId);
        db.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot sn: dataSnapshot.getChildren()) {

                    if(sn.getKey().equals("Nmove")){
                        DatabaseReference move = database.getReference("game/"+ gameId+"/Nmove");
                        Long s= (Long) sn.getValue();
                        move.setValue(s+1);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        //FINE: INCREMENTO MOSSE


        clearBoardColor();
        moveBoard(parseBoard());
        returnedText.setText(suggestions.getFirstMessage());

        clearDuble();
    }


    private boolean isaMove(Move mo) {

        for (Move m : board.legalMoves()) {
            if (m.equals(mo) || m.toString().contains(mo.toString()))
                return true;
        }
        return false;
    }


    private  Move searchMove(String mosse) {
        mosse= mosse.toUpperCase(Locale.ROOT);
        String[] pos=mosse.split(" ");
        Square a=null;
        Square b=null;
        try {
            a= Square.fromValue(pos[0]);
            b= Square.fromValue(pos[1]);
        }catch (IllegalArgumentException e){
            return null;
        }
        for (Move m : board.legalMoves()) {
            if ((m.getTo().equals(a) && m.getFrom().equals(b)) || (m.getTo().equals(b) && m.getFrom().equals(a)) )
                return m;
        }
        return null;
    }

    /**
     * This function parse the board and create a matrix that return
     * @return return a matrix represented the board
     */
    private String [][] parseBoard(){
        String str=board.toString();
        //String str=boardString;
        String[] array = str.split("", -1);
        ArrayList<String> pos= new ArrayList<>();
        boolean cont=true;
        for(int i=0; i<array.length;i++){
            if(cont){
                if (array[i].equals("S")){
                    cont=false;
                }else{
                    if (!array[i].equals("\n")){
                        pos.add(array[i]);
                    }
                }
            }
        }

        List<String> r1=  pos.subList(1,9);
        List<String> r2=  pos.subList(9,17);
        List<String> r3=  pos.subList(17,25);
        List<String> r4=  pos.subList(25,33);
        List<String> r5=  pos.subList(33,41);
        List<String> r6=  pos.subList(41,49);
        List<String> r7=  pos.subList(49,57);
        List<String> r8=  pos.subList(57,65);


        String [][] matrix=new String[8][8];

        matrix [0]= r8.toArray(new String[0]);
        matrix [1]= r7.toArray(new String[0]);
        matrix [2]= r6.toArray(new String[0]);
        matrix [3]= r5.toArray(new String[0]);
        matrix [4]= r4.toArray(new String[0]);
        matrix [5]= r3.toArray(new String[0]);
        matrix [6]= r2.toArray(new String[0]);
        matrix [7]= r1.toArray(new String[0]);

        return matrix;
    }




    /**
     * This function parse the board and create a matrix that return
     * @return return a matrix represented the board
     */
    private String [][] parseBoard(String boardString){
        String str=boardString;
        String[] array = str.split("", -1);
        ArrayList<String> pos= new ArrayList<>();
        boolean cont=true;
        for(int i=0; i<array.length;i++){
            if(cont){
                if (array[i].equals("S")){
                    cont=false;
                }else{
                    if (!array[i].equals("\n")){
                        pos.add(array[i]);
                    }
                }
            }
        }

        List<String> r1=  pos.subList(1,9);
        List<String> r2=  pos.subList(9,17);
        List<String> r3=  pos.subList(17,25);
        List<String> r4=  pos.subList(25,33);
        List<String> r5=  pos.subList(33,41);
        List<String> r6=  pos.subList(41,49);
        List<String> r7=  pos.subList(49,57);
        List<String> r8=  pos.subList(57,65);


        String [][] matrix=new String[8][8];

        matrix [0]= r8.toArray(new String[0]);
        matrix [1]= r7.toArray(new String[0]);
        matrix [2]= r6.toArray(new String[0]);
        matrix [3]= r5.toArray(new String[0]);
        matrix [4]= r4.toArray(new String[0]);
        matrix [5]= r3.toArray(new String[0]);
        matrix [6]= r2.toArray(new String[0]);
        matrix [7]= r1.toArray(new String[0]);

        return matrix;
    }

    /**
     *
     *
     * Remark: Java is Row first, but the display JAVA è ROW FIRST MA IL DISPLAY BOARD E TUTTA L'ACTIVITY è STATA INIZIALIZZATA AL CONTRARIO
     */
    private void moveBoard(String[][] matrix){
        for (int row=0;row<8;row++){
            for (int col=0; col<8; col++){
                switch (matrix[row][col]){
                    case "r":
                        DisplayBoard[col][row].setBackgroundResource(R.drawable.brook);
                        break;
                    case "n":
                        DisplayBoard[col][row].setBackgroundResource(R.drawable.bknight);
                        break;
                    case "b":
                        DisplayBoard[col][row].setBackgroundResource(R.drawable.bbishop);
                        break;
                    case "q":
                        DisplayBoard[col][row].setBackgroundResource(R.drawable.bqueen);
                        break;
                    case "k":
                        DisplayBoard[col][row].setBackgroundResource(R.drawable.bking);
                        break;
                    case "p":
                        DisplayBoard[col][row].setBackgroundResource(R.drawable.bpawn);
                        break;
                    case "R":
                        DisplayBoard[col][row].setBackgroundResource(R.drawable.wrook);
                        break;
                    case "N":
                        DisplayBoard[col][row].setBackgroundResource(R.drawable.wknight);
                        break;
                    case "B":
                        DisplayBoard[col][row].setBackgroundResource(R.drawable.wbishop);
                        break;
                    case "Q":
                        DisplayBoard[col][row].setBackgroundResource(R.drawable.wqueen);
                        break;
                    case "K":
                        DisplayBoard[col][row].setBackgroundResource(R.drawable.wking);
                        break;
                    case "P":
                        DisplayBoard[col][row].setBackgroundResource(R.drawable.wpawn);
                        break;
                    case ".":
                        DisplayBoard[col][row].setBackgroundResource(0);
                        break;

                }
            }
        }
    }

    /**
     * @param pos Final square of the moves
     * @return List of coordinates
     */
    private Integer parseYAssis (int pos){

        HashMap<Integer, Integer> Y = new HashMap<Integer, Integer>() {{
            put(0,7);
            put(1,6);
            put(2,5);
            put(3,4);
            put(4,3);
            put(5,2);
            put(6,1);
            put(7,0);
        }};

        return Y.get(pos);
    }

    /**
     * Clear all color of a boards
     */
    @SuppressLint("ResourceAsColor")
    private void clearBoardColor(){
        List<TextView> dark= new LinkedList<TextView>(){{
            add((TextView) findViewById(R.id.RO000));
            add((TextView) findViewById(R.id.RO002));
            add((TextView) findViewById(R.id.RO004));
            add((TextView) findViewById(R.id.RO006));

            add((TextView) findViewById(R.id.RO017));
            add((TextView) findViewById(R.id.RO015));
            add((TextView) findViewById(R.id.RO013));
            add((TextView) findViewById(R.id.RO011));

            add((TextView) findViewById(R.id.RO020));
            add((TextView) findViewById(R.id.RO022));
            add((TextView) findViewById(R.id.RO024));
            add((TextView) findViewById(R.id.RO026));

            add((TextView) findViewById(R.id.RO037));
            add((TextView) findViewById(R.id.RO035));
            add((TextView) findViewById(R.id.RO033));
            add((TextView) findViewById(R.id.RO031));

            add((TextView) findViewById(R.id.RO040));
            add((TextView) findViewById(R.id.RO042));
            add((TextView) findViewById(R.id.RO044));
            add((TextView) findViewById(R.id.RO046));

            add((TextView) findViewById(R.id.RO057));
            add((TextView) findViewById(R.id.RO055));
            add((TextView) findViewById(R.id.RO053));
            add((TextView) findViewById(R.id.RO051));

            add((TextView) findViewById(R.id.RO060));
            add((TextView) findViewById(R.id.RO062));
            add((TextView) findViewById(R.id.RO064));
            add((TextView) findViewById(R.id.RO066));

            add((TextView) findViewById(R.id.RO077));
            add((TextView) findViewById(R.id.RO075));
            add((TextView) findViewById(R.id.RO073));
            add((TextView) findViewById(R.id.RO071));
        }};


        for (TextView[] d : DisplayBoardBackgroundSelected){
            for(TextView t:d){
                if(dark.contains(t)){
                    t.setBackgroundColor(Color.parseColor("#0586c8"));
                }else {
                    t.setBackgroundColor(Color.parseColor("#ffffff"));
                }
            }
        }


    }

    /**
     * Reset clicks
     */
    public void clearDuble(){
        c1=null;
        c2=null;
    }

    /**
     *This function color the squares in which it is possible to move
     * @param s Init moves square
     */
    @SuppressLint({"ResourceAsColor", "Range"})
    private void colorMove (Square s) {
        for (Move m : board.legalMoves()) {
            if (m.getFrom() == s) {
                DisplayBoardBackgroundSelected[parseMove(m.getTo()).get(0)][parseMove(m.getTo()).get(1)].setBackgroundColor(Color.parseColor("#A67FFFD4"));

            }
        }
    }

    /**
     * This function color the king position if is under attak
     */
    private void colorRedking(String[][] matrix){

        String king;
        if (board.getSideToMove()== Side.WHITE){
            king="K";
        }else {
            king="k";
        }

        for (int row=0;row<8;row++){
            for (int col=0; col<8; col++){
                if (matrix[row][col].equals(king))
                    DisplayBoardBackgroundSelected[col][row].setBackgroundColor(Color.parseColor("#A6fc0703"));
            }
        }
    }

    /**
     * @param pos Final square of the moves
     * @return List of coordinates
     */
    private List<Integer> parseMove (Square pos){

        HashMap<String, Integer> X = new HashMap<String, Integer>() {{
            put("A", 0);
            put("B", 1);
            put("C", 2);
            put("D", 3);
            put("E", 4);
            put("F", 5);
            put("G", 6);
            put("H", 7);
        }};

        HashMap<String, Integer> Y = new HashMap<String, Integer>() {{
            put("1", 0);
            put("2", 1);
            put("3", 2);
            put("4", 3);
            put("5", 4);
            put("6", 5);
            put("7", 6);
            put("8", 7);
        }};

        String posS = (String) pos.name();
        String col = posS.substring(0, 1);
        String row = posS.substring(1, 2);

        LinkedList<Integer> ret = new LinkedList<>();
        ret.add(X.get(col));
        ret.add(Y.get(row));
        return ret;
    }

    /**
     * giving s cell return all moves
     */
    private List<Move> cellMoves(Square s){
        List<Move> moveList= new LinkedList<>();
        for (Move mo: board.legalMoves()){
            if (mo.getFrom().equals(s)){
                moveList.add(mo);
            }
        }
        return moveList;
    }

    public void bestmove(View view) {
        Move mo=player.eseguiMossa(board.legalMoves());
        List<Integer> coordinate_from=parseMove(mo.getFrom());
        String coordinate_f="R"+coordinate_from.get(0) +""+coordinate_from.get(1);
        View from=findViewById(getResources().getIdentifier(coordinate_f,"id", getBaseContext().getPackageName()));
        onClick(from);
        List<Integer> coordinate_to=parseMove(mo.getTo());
        String coordinate_t="R"+coordinate_to.get(0) +""+coordinate_to.get(1);
        View to=findViewById(getResources().getIdentifier(coordinate_t,"id", getBaseContext().getPackageName()));
        onClick(to);
    }

    public void settings(View view) {
        if (settingsMenu.getVisibility() != View.VISIBLE){
            settingsMenu.setVisibility(View.VISIBLE);
        }else {
            settingsMenu.setVisibility(View.GONE);
            returnedText.setText(suggestions.getFirstMessage());
        }

    }

    public void cast(View view) {
        System.out.println("cast");
        startActivity(new Intent("android.settings.CAST_SETTINGS"));
    }

    public void restart(View view) {
        Intent intent = getIntent();
        finish();
        startActivity(intent);
    }

    public void exit(View view){
        finish();
    }

    public void Back(View view) {
        settingsMenu.setVisibility(View.GONE);
        returnedText.setText(suggestions.getFirstMessage());
    }
}