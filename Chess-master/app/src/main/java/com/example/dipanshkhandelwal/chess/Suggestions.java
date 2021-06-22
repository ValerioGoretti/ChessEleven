package com.example.dipanshkhandelwal.chess;

import java.util.ArrayList;

public class Suggestions {

    ArrayList<String> tasks=new ArrayList<>();

    public Suggestions(){
        tasks.add("'Muovi il pedone da c2 a c4'");
        tasks.add("'Dammi un consiglio'");
        tasks.add("'Mostrami le impostazioni'");
        tasks.add("'Proietta lo schermo'");
    }
    public String yesNoSuggestions(){
        return "'yes'\n'no'";
    }

    public String getCommandSuggestions(){
        String text="";
        for(String s: tasks){

            text=text+s+"\n";
        }
        return text;


    }
}
