package com.example.dipanshkhandelwal.chess;

import java.util.Arrays;
import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TextToCommand {

    HashSet<String> moveCommands;

    HashSet<String> cellNames=new HashSet<>(Arrays.asList("a1","a2","a3","a4","a5","a6","a7","a8","b1","b2","b3","b4","b5","b6","b7","b8","c1","c2","c3","c4","c5","c6","c7","c8","d1","d2","d3","d4","d5","d6","d7","d8","e1","e2","e3","e4","e5","e6","e7","e8","f1","f2","f3","f4","f5","f6","f7","f8","g1","g2","g3","g4","g5","g6","g7","g8","h1","h2","h3","h4","h5","h6","h7","h8"));
    HashSet chatCommands;
    HashSet helpCommands;
    HashSet<String> gameCommmands;

    HashSet tutorial;

    public TextToCommand(){
        moveCommands=new HashSet(Arrays.asList("metti","mettere","sposta","spostare","muovi","muovere","cambia","posizione","cambiare","posizioni","cambio","mossa","mosse","giocate","giocata","trasferisci","trasferire","movimento"));
        chatCommands=new HashSet();
        helpCommands=new HashSet<>(Arrays.asList("suggerimento","suggerimenti","aiuto","migliore","possibile","migliori","aiuti","consiglio","cosigli","assistenza","sostegno","possibili","indicazione","indicazioni","idea","idee"));;
        gameCommmands=new HashSet();
        tutorial= new HashSet<>();
    }


    public String getTriggerCommand(String sentence){
        System.out.println("STO VALUTANDO "+sentence);
        String sentenceLower=sentence.toLowerCase();
        String[] strParts =sentenceLower.split(" ");
        System.out.println(strParts.toString());
        for(String words:strParts) {
            if (moveCommands.contains(words)) return "What move do you want to do?";
            if (helpCommands.contains(words)) return "What kind of help do you want?\n\n'Dimmi le mosse per il pedone in c2'\n\n'Esegui la miglior mossa possibile'\n\n'indietro'";
            if (chatCommands.contains(words)) return "Please says the message you want to send";
            if (gameCommmands.contains(words))return "What command do you want to do?";
        }
        return "I' didn't understand the command";
    }
    public String getMove(String sentence){
        int cellsNumber=0;
        String result="";
        HashSet<String>setSentence=new HashSet(Arrays.asList(sentence.toLowerCase().split(" ")));
        System.out.println("MOSSA CERCATA: "+sentence.toLowerCase());
        for(String cell:cellNames){
            if(setSentence.contains(cell)){result+=cell+" ";cellsNumber+=1;}
        if(cellsNumber==2){break;}
        }
        if( result.equals("")||cellsNumber<2) return "I didn't understand the move";
        else{
            return result;

        }}
        public String getCell(String sentence){
            int cellsNumber=0;
            String result="";
            HashSet<String>setSentence=new HashSet(Arrays.asList(sentence.toLowerCase().split(" ")));
            System.out.println("CELLA CERCATA: "+sentence.toLowerCase());
            for(String cell:cellNames){
                if(setSentence.contains(cell)){result+=cell+" ";cellsNumber+=1;}
                if(cellsNumber==1){break;}
            }
            if( result.equals("")||cellsNumber<1) return "I didn't understand";
            else{
                return result;

            }
        }












}
