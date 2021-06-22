package com.example.dipanshkhandelwal.chess;

import com.github.bhlangonijr.chesslib.Piece;
import com.github.bhlangonijr.chesslib.move.Move;

import java.util.LinkedList;
import java.util.List;

public class Player {
    private List<Piece> prom= new LinkedList<Piece>(){{ add(Piece.BLACK_QUEEN); add(Piece.BLACK_BISHOP); add(Piece.BLACK_KNIGHT); add(Piece.BLACK_ROOK);}};

    public Move eseguiMossa(List<Move> moves) {

        int numero = (int) (Math.random()*moves.size());

        return moves.get(numero);
    }

    public Move eseguiMossaPromozione(List<Move> moves) {

        int numeroM = (int) (Math.random()*moves.size());
        int numeroP = (int) (Math.random()*prom.size());

        Move plmo= moves.get(numeroM);
        return new Move(plmo.getFrom(),plmo.getTo(),prom.get(numeroP));
    }

}
