package com.example.dipanshkhandelwal.chess;

import com.github.bhlangonijr.chesslib.move.Move;

import java.util.List;

public class Player {


    public void scegliMossa(List<Move> moves){
        int numero = (int) (Math.random()*moves.size());
        System.out.println(moves.get(numero).toString());
    }

}
