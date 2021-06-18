package com.example.dipanshkhandelwal.chess;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.dipanshkhandelwal.chess.Pieces.Bishop;
import com.example.dipanshkhandelwal.chess.Pieces.King;
import com.example.dipanshkhandelwal.chess.Pieces.Knight;
import com.example.dipanshkhandelwal.chess.Pieces.Pawn;
import com.example.dipanshkhandelwal.chess.Pieces.Piece;
import com.example.dipanshkhandelwal.chess.Pieces.Queen;
import com.example.dipanshkhandelwal.chess.Pieces.Rook;
import com.github.bhlangonijr.chesslib.Board;
import com.github.bhlangonijr.chesslib.Square;
import com.github.bhlangonijr.chesslib.move.Move;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public Boolean FirstPlayerTurn;
    //public ArrayList<Coordinates> listOfCoordinates = new ArrayList<>();
    public Position[][] Board = new Position[8][8];
    public Position[][] Board2 = new Position[8][8];
    //public Boolean AnythingSelected = false;
    //public Coordinates lastPos = null ;
    public Square c1=null;
    public Square c2=null;
    public Square click = null;
    //public Coordinates clickedPosition = new Coordinates(0, 0);
    public TextView game_over;
    public TextView[][] DisplayBoard = new TextView[8][8];
    public TextView[][] DisplayBoardBackground = new TextView[8][8];
    //public ArrayList<Position[][]> LastMoves = new ArrayList<>();
    public LinearLayout pawn_choices;
    public int numberOfMoves;

    Piece bKing;
    Piece wKing;

    Piece bQueen;
    Piece wQueen;

    Piece bKnight1;
    Piece bKnight2;
    Piece wKnight1;
    Piece wKnight2;

    Piece bRook1;
    Piece bRook2;
    Piece wRook1;
    Piece wRook2;

    Piece bBishop1;
    Piece bBishop2;
    Piece wBishop1;
    Piece wBishop2;

    Piece bPawn1;
    Piece bPawn2;
    Piece bPawn3;
    Piece bPawn4;
    Piece bPawn5;
    Piece bPawn6;
    Piece bPawn7;
    Piece bPawn8;

    Piece wPawn1;
    Piece wPawn2;
    Piece wPawn3;
    Piece wPawn4;
    Piece wPawn5;
    Piece wPawn6;
    Piece wPawn7;
    Piece wPawn8;

    Board board;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Making notification bar transparent
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }
        setContentView(R.layout.activity_main);

        initializeBoard();

        game_over = (TextView) findViewById(R.id.game_over);
        pawn_choices = (LinearLayout) findViewById(R.id.pawn_chioces);

        game_over.setVisibility(View.INVISIBLE);
        pawn_choices.setVisibility(View.INVISIBLE);
    }

    private void initializeBoard() {
        board = new Board();

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
        bKing = new King(false);
        wKing = new King(true);

        bQueen = new Queen(false);
        wQueen = new Queen(true);

        bRook1 = new Rook(false);
        bRook2 = new Rook(false);
        wRook1 = new Rook(true);
        wRook2 = new Rook(true);

        bKnight1 = new Knight(false);
        bKnight2 = new Knight(false);
        wKnight1 = new Knight(true);
        wKnight2 = new Knight(true);

        bBishop1 = new Bishop(false);
        bBishop2 = new Bishop(false);
        wBishop1 = new Bishop(true);
        wBishop2 = new Bishop(true);

        bPawn1 = new Pawn(false);
        bPawn2 = new Pawn(false);
        bPawn3 = new Pawn(false);
        bPawn4 = new Pawn(false);
        bPawn5 = new Pawn(false);
        bPawn6 = new Pawn(false);
        bPawn7 = new Pawn(false);
        bPawn8 = new Pawn(false);

        wPawn1 = new Pawn(true);
        wPawn2 = new Pawn(true);
        wPawn3 = new Pawn(true);
        wPawn4 = new Pawn(true);
        wPawn5 = new Pawn(true);
        wPawn6 = new Pawn(true);
        wPawn7 = new Pawn(true);
        wPawn8 = new Pawn(true);



        Board[0][7].setPiece(bRook1);
        Board[1][7].setPiece(bKnight1);
        Board[2][7].setPiece(bBishop1);
        Board[3][7].setPiece(bQueen);
        Board[4][7].setPiece(bKing);
        Board[5][7].setPiece(bBishop2);
        Board[6][7].setPiece(bKnight2);
        Board[7][7].setPiece(bRook2);

        Board[0][6].setPiece(bPawn1);
        Board[1][6].setPiece(bPawn2);
        Board[2][6].setPiece(bPawn3);
        Board[3][6].setPiece(bPawn4);
        Board[4][6].setPiece(bPawn5);
        Board[5][6].setPiece(bPawn6);
        Board[6][6].setPiece(bPawn7);
        Board[7][6].setPiece(bPawn8);

        Board[0][0].setPiece(wRook1);
        Board[1][0].setPiece(wKnight1);
        Board[2][0].setPiece(wBishop1);
        Board[3][0].setPiece(wQueen);
        Board[4][0].setPiece(wKing);
        Board[5][0].setPiece(wBishop2);
        Board[6][0].setPiece(wKnight2);
        Board[7][0].setPiece(wRook2);

        Board[0][1].setPiece(wPawn1);
        Board[1][1].setPiece(wPawn2);
        Board[2][1].setPiece(wPawn3);
        Board[3][1].setPiece(wPawn4);
        Board[4][1].setPiece(wPawn5);
        Board[5][1].setPiece(wPawn6);
        Board[6][1].setPiece(wPawn7);
        Board[7][1].setPiece(wPawn8);



        ho cancellato l'inizializzazione di displayboard un array di TextView
         */

    }


    @Override
    public void onClick(View v) {
        // Assign the Square clicked by the user to click
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
            case R.id.R67:
                click = Square.G8;
                break;
            case R.id.R77:
                click = Square.H8;
                break;
        }


        ArrayList<Move> allowMoves = new ArrayList<>();
        if (c1==null) {
            c1=click;
            colorMove(c1);
            /**
             * SETTA IL PRIMO TOCCO
             *
             * COLORA LE CELLE
             */
        } else {
            if (c1!=null && c2==null) {
                c2=click;
                Move mo=new Move(c1,c2);
                System.out.println("SECONDO TOCCO");
                if(isaMove(mo)){
                    board.doMove(mo);
                    System.out.println(board.toString());
                    clearDuble();
                    //clearBoardColor();
                }else {
                    System.out.println("NON è una mossa valida");
                    clearDuble();
                    //clearBoardColor();
                }
                /**
                 *Colori
                 */
            }else {
                System.out.println("ALTROOOOOOOOOOOOOOOOO");
            }
        }

    }


    private boolean isaMove(Move mo) {
        for (Move m : board.legalMoves()) {
            if (m.equals(mo))
                return true;
        }
        return false;
    }

    

    /*
        DA FIXARE FA TUTTA LA TABELLA BLU
    clear all color of a board
     */
    @SuppressLint("ResourceAsColor")
    private void clearBoardColor(){
        List<TextView> dark= new LinkedList<TextView>(){{
           add((TextView) findViewById(R.id.R000));
           add((TextView) findViewById(R.id.R002));
           add((TextView) findViewById(R.id.R004));
           add((TextView) findViewById(R.id.R006));

           add((TextView) findViewById(R.id.R017));
           add((TextView) findViewById(R.id.R015));
           add((TextView) findViewById(R.id.R013));
           add((TextView) findViewById(R.id.R011));

           add((TextView) findViewById(R.id.R020));
           add((TextView) findViewById(R.id.R022));
           add((TextView) findViewById(R.id.R024));
           add((TextView) findViewById(R.id.R026));

            add((TextView) findViewById(R.id.R037));
            add((TextView) findViewById(R.id.R035));
            add((TextView) findViewById(R.id.R033));
            add((TextView) findViewById(R.id.R031));

            add((TextView) findViewById(R.id.R040));
            add((TextView) findViewById(R.id.R042));
            add((TextView) findViewById(R.id.R044));
            add((TextView) findViewById(R.id.R046));

            add((TextView) findViewById(R.id.R057));
            add((TextView) findViewById(R.id.R055));
            add((TextView) findViewById(R.id.R053));
            add((TextView) findViewById(R.id.R051));

            add((TextView) findViewById(R.id.R060));
            add((TextView) findViewById(R.id.R062));
            add((TextView) findViewById(R.id.R064));
            add((TextView) findViewById(R.id.R066));

            add((TextView) findViewById(R.id.R077));
            add((TextView) findViewById(R.id.R075));
            add((TextView) findViewById(R.id.R073));
            add((TextView) findViewById(R.id.R071));
        }};

        for (TextView[] d:DisplayBoardBackground){
            for(TextView t:d){
                if(dark.contains(t)){
                    t.setBackgroundColor(R.color.colorBoardDark);
                }else {
                    t.setBackgroundColor(R.color.colorBoardLight);
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
    @SuppressLint("ResourceAsColor")
    private void colorMove (Square s) {
        for (Move m : board.legalMoves()) {
            if (m.getFrom() == s) {
                System.out.println(m.getTo());
                System.out.println(parseMove(m.getTo()).get(0));
                System.out.println(parseMove(m.getTo()).get(1));
                DisplayBoardBackground[parseMove(m.getTo()).get(0)][parseMove(m.getTo()).get(1)].setBackgroundColor(R.color.colorSelected);

            }


        }
    }
        /*
        for (Move x : allowMoves) {
            List<Integer> position = parseMove(x.getTo());
            System.out.println(position.toString());
            //DisplayBoard[Integer.parseInt(position.get(0))][Integer.parseInt(position.get(1))].setBackgroundColor(Color.parseColor("#1e5cb8"));

        }*/



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


    public void undo (View view){
    }

    public void pawnChoice (View view){
    }

}