package com.example.dipanshkhandelwal.chess;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class community_activity extends AppCompatActivity {
    private ListView search;
    private ListView followerList;
    private  ListView matchesList;
    private Button buttonFollower;
    private Button buttonSearch;
    private Button buttonMatches;
    private boolean loadingMatches=true;
    private String searched[]={};
    private String follower[]={};
    private String player1[]={};
    private String player2[]={};
    private String date[]={};
    private ArrayList<ArrayList<String>> matchesArray;
    private boolean loadingFollower=true;
    public int scene=1;
    public String numberOfFollower=" ? ";
    private EditText ricerca;
    private Button ricercaButton;
    private SharedPreferences shared;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_community);
        LinearLayout vista=(findViewById(R.id.vista));
        getLayoutInflater().inflate(R.layout.search_layout, vista);
        search=(ListView) findViewById(R.id.searched);
        MyAdapter adapter=new MyAdapter(this,searched,R.id.searched);
        ricerca=(EditText)findViewById(R.id.testoRicerca);
        shared = PreferenceManager.getDefaultSharedPreferences(this);
        ricercaButton=(Button)findViewById(R.id.searchButton_community) ;
        ricercaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    System.out.println("STO CERCANDO " +ricerca.getText().toString());
                    RequestQueue queue= Volley.newRequestQueue(getApplicationContext());
                    String url ="https://community-service-dot-chesseleven.oa.r.appspot.com/search";
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                if (jsonObject.get("success").equals("true")) {
                                    JSONArray arr=jsonObject.getJSONArray("username");
                                    List<String> list = new ArrayList<String>();
                                    for(int i = 0; i < arr.length(); i++){
                                        //qua
                                        if(!arr.getString(i).equals(shared.getString("username","null"))){
                                            list.add(arr.getString(i));}
                                    }
                                    if (scene==1){
                                        searched=list.toArray(new String[list.size()]);
                                        search=(ListView) findViewById(R.id.searched);
                                        MyAdapter adapter=new MyAdapter(getApplicationContext(),searched,R.id.searched);
                                        search.setAdapter(adapter);
                                        search.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                            @Override
                                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                                //Toast.makeText(community_activity.this,searched[position],Toast.LENGTH_LONG).show();
                                                Intent intent= new Intent(getBaseContext(),VisitProfileActivity.class);
                                                intent.putExtra("username",searched[position]);
                                                startActivity(intent);
                                                finish();
                                            }
                                        });
                                    }
                                }
                                else{
                                    Toast.makeText(getApplicationContext(), "No player found!",
                                            Toast.LENGTH_LONG).show();
                                    String empt[]={};
                                    MyAdapter adapter=new MyAdapter(getApplicationContext(),empt,R.id.searched);
                                    search.setAdapter(adapter);
                                }
                            }
                            catch (Exception e){System.out.println(e.toString());}
                        }
                    },new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(getApplicationContext(), "Service unavailable, try later",
                                    Toast.LENGTH_LONG).show();
                            Log.e("VOLLEY", error.toString());
                        }}) {

                        @Override
                        protected Map<String,String> getParams(){
                            Map<String,String> params = new HashMap<String, String>();
                            String tes=ricerca.getText().toString();
                            if(tes.equals("")) tes="  ";
                            params.put("substring",tes);
                            return params;
                        }
                        @Override
                        protected Response<String> parseNetworkResponse(NetworkResponse response) {
                            String responseString = "";
                            if (response != null) {
                                responseString = String.valueOf(response.statusCode);
                            }
                            return super.parseNetworkResponse(response);
                        }
                    };
                    queue.add(stringRequest);
                }
                catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "Service unavailable, try later",
                            Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }
        });
        search.setAdapter(adapter);
        try{
            RequestQueue queue= Volley.newRequestQueue(getApplicationContext());
            String url ="https://community-service-dot-chesseleven.oa.r.appspot.com/getFollower";
            StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        if (jsonObject.get("success").equals("true")) {
                            JSONArray arr=jsonObject.getJSONArray("username");
                            List<String> list = new ArrayList<String>();
                            for(int i = 0; i < arr.length(); i++){
                                list.add(arr.getString(i));
                            }
                            follower=(String[])list.toArray(new String[list.size()]);
                            loadingFollower=false;
                            numberOfFollower=Integer.toString(follower.length);
                            if (scene==2){
                                followerList=(ListView) findViewById(R.id.followerList);
                                MyAdapter adapter=new MyAdapter(getApplicationContext(),follower,R.id.followerList);
                                followerList.setAdapter(adapter);
                                if (!loadingFollower){
                                    findViewById(R.id.waitFollower).setVisibility(View.GONE);
                                    findViewById(R.id.message).setVisibility(View.GONE);
                                    TextView n=findViewById(R.id.numberOfFollower);
                                    n.setText(numberOfFollower);
                                }

                                followerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                       // Toast.makeText(community_activity.this,follower[position],Toast.LENGTH_LONG).show();
                                        Intent intent= new Intent(getBaseContext(),VisitProfileActivity.class);
                                        intent.putExtra("username",follower[position]);
                                        startActivity(intent);
                                        finish();

                                    }
                                });
                            }
                        }
                        else{
                            loadingFollower=false;
                            numberOfFollower="0";
                            if (scene==2){
                            findViewById(R.id.waitFollower).setVisibility(View.GONE);
                            findViewById(R.id.message).setVisibility(View.GONE);
                            TextView n=findViewById(R.id.numberOfFollower);
                            n.setText("0");}

                        }
                    }
                    catch (Exception e){System.out.println(e.toString());}
                }
            },new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getApplicationContext(), "Service unavailable, try later",
                            Toast.LENGTH_LONG).show();
                    Log.e("VOLLEY", error.toString());
                }}) {

                @Override
                protected Map<String,String> getParams(){
                    Map<String,String> params = new HashMap<String, String>();
                    params.put("username",shared.getString("username","null"));
                    return params;
                }
                @Override
                protected Response<String> parseNetworkResponse(NetworkResponse response) {
                    String responseString = "";
                    if (response != null) {
                        responseString = String.valueOf(response.statusCode);
                    }
                    return super.parseNetworkResponse(response);
                }
            };

            queue.add(stringRequest);


        }
        catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Service unavailable, try later",
                    Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
        try{
            RequestQueue queue= Volley.newRequestQueue(getApplicationContext());
            String url ="https://community-service-dot-chesseleven.oa.r.appspot.com/getLastMatches";
            StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        Log.i("VOLLEY", response);
                        JSONObject jsonObject = new JSONObject(response);
                        if (jsonObject.get("success").equals("true")) {
                            JSONArray arr=jsonObject.getJSONArray("username");
                            ArrayList<ArrayList<String>> list = new ArrayList<>();
                            List<String> pl1=new ArrayList<>();
                            List<String> pl2=new ArrayList<>();
                            List<String> dt=new ArrayList<>();
                            for(int i = 0; i < arr.length(); i++){
                                ArrayList<String> list2=new ArrayList<>();
                                for(int j=0;j<arr.getJSONArray(i).length();j++){
                                    list2.add(arr.getJSONArray(i).getString(j));
                                    if(j==1){pl1.add(arr.getJSONArray(i).getString(j));}
                                    if(j==2){pl2.add(arr.getJSONArray(i).getString(j));}
                                    if(j==4){dt.add(arr.getJSONArray(i).getString(j));}
                                }
                                list.add(list2);
                            }
                            matchesArray=list;
                            player1=(String[])pl1.toArray(new String[pl1.size()]);
                            player2=(String[])pl2.toArray(new String[pl2.size()]);
                            date=(String[])dt.toArray(new String[dt.size()]);
                            loadingMatches=false;
                            if(scene==3){
                            findViewById(R.id.waitMatches).setVisibility(View.GONE);
                            findViewById(R.id.messageM).setVisibility(View.GONE);
                                matchesList=(ListView) findViewById(R.id.lastMatches);
                                RecentMatchesAdapter adapter=new RecentMatchesAdapter(getApplicationContext(),player1,player2,date,R.id.lastMatches);
                                matchesList.setAdapter(adapter);
                                matchesList.setOnItemClickListener(new AdapterView.OnItemClickListener()

                                {
                                    @Override
                                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                                        // inflate the layout of the popup window
                                        LayoutInflater inflater = (LayoutInflater)
                                                getSystemService(LAYOUT_INFLATER_SERVICE);
                                        View popupView = inflater.inflate(R.layout.pop_up_matches, null);
                                        TextView winner=popupView.findViewById(R.id.winnnerS);
                                        if(matchesArray.get(position).get(3).equals("1")){
                                            winner.setText(player1[position]);
                                        }
                                        if(matchesArray.get(position).get(3).equals("x")){
                                            winner.setText("Draw");
                                        }
                                        if(matchesArray.get(position).get(3).equals("2")){
                                            winner.setText(player2[position]);
                                        }
                                        TextView date_=popupView.findViewById(R.id.dateS);
                                        date_.setText(date[position]);
                                        TextView nmoves=popupView.findViewById(R.id.numberOfMovesS);
                                        nmoves.setText(matchesArray.get(position).get(5));
                                        TextView nchk=popupView.findViewById(R.id.numberOFChecks);
                                        nchk.setText(matchesArray.get(position).get(6));
                                        // create the popup window
                                        int width = LinearLayout.LayoutParams.WRAP_CONTENT;
                                        int height = LinearLayout.LayoutParams.WRAP_CONTENT;
                                        boolean focusable = true; // lets taps outside the popup also dismiss it
                                        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

                                        // show the popup window
                                        // which view you pass in doesn't matter, it is only used for the window tolken
                                        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

                                        // dismiss the popup window when touched
                                        popupView.setOnTouchListener(new View.OnTouchListener() {
                                            @Override
                                            public boolean onTouch(View v, MotionEvent event) {
                                                popupWindow.dismiss();
                                                return true;
                                            }
                                        });
                                    }
                                });


                            }

                        }
                        else{
                            if(scene==3){
                            Toast.makeText(getApplicationContext(), "No game can be watched",
                                    Toast.LENGTH_LONG).show();}
                            loadingMatches=false;
                            findViewById(R.id.waitMatches).setVisibility(View.GONE);
                            findViewById(R.id.messageM).setVisibility(View.GONE);


                        }
                    }
                    catch (Exception e){System.out.println(e.toString());}
                }
            },new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getApplicationContext(), "Service unavailable, try later",
                            Toast.LENGTH_LONG).show();
                    Log.e("VOLLEY", error.toString());
                }}) {

                @Override
                protected Map<String,String> getParams(){
                    Map<String,String> params = new HashMap<String, String>();
                    params.put("username",shared.getString("username","null"));
                    return params;
                }
                @Override
                protected Response<String> parseNetworkResponse(NetworkResponse response) {
                    String responseString = "";
                    if (response != null) {
                        responseString = String.valueOf(response.statusCode);
                    }
                    return super.parseNetworkResponse(response);
                }
            };
            queue.add(stringRequest);
        }
        catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Service unavailable, try later",
                    Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
        buttonFollower=(Button)findViewById(R.id.buttonMyFollower);
        buttonFollower.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scene=2;
                vista.removeAllViews();
                getLayoutInflater().inflate(R.layout.follower_layout, vista);
                if (!loadingFollower){
                    findViewById(R.id.waitFollower).setVisibility(View.GONE);
                    findViewById(R.id.message).setVisibility(View.GONE);
                    TextView n=findViewById(R.id.numberOfFollower);
                    n.setText(numberOfFollower);
                }
                followerList=(ListView) findViewById(R.id.followerList);
                MyAdapter adapter=new MyAdapter(getApplicationContext(),follower,R.id.followerList);
                followerList.setAdapter(adapter);
                followerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        // Toast.makeText(community_activity.this,follower[position],Toast.LENGTH_LONG).show();
                        Intent intent= new Intent(getBaseContext(),VisitProfileActivity.class);
                        intent.putExtra("username",follower[position]);
                        startActivity(intent);
                        finish();
                        //Toast.makeText(community_activity.this,follower[position],Toast.LENGTH_LONG).show();

                    }
                });

            }
        });
        buttonSearch=(Button)findViewById(R.id.button7);
        buttonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scene=1;
                vista.removeAllViews();
                View v2=getLayoutInflater().inflate(R.layout.search_layout, vista);
                ricercaButton=(Button)v2.findViewById(R.id.searchButton_community);
                ricerca=(EditText) v2.findViewById(R.id.testoRicerca);
                ricercaButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try{
                            RequestQueue queue= Volley.newRequestQueue(getApplicationContext());
                            String url ="https://community-service-dot-chesseleven.oa.r.appspot.com/search";
                            StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    try {
                                        JSONObject jsonObject = new JSONObject(response);
                                        if (jsonObject.get("success").equals("true")) {
                                            JSONArray arr=jsonObject.getJSONArray("username");
                                            List<String> list = new ArrayList<String>();
                                            for(int i = 0; i < arr.length(); i++){
                                                //qua
                                                if(!arr.getString(i).equals(shared.getString("username","null"))){
                                                list.add(arr.getString(i));}
                                            }
                                            if (scene==1){
                                                searched=list.toArray(new String[list.size()]);
                                                search=(ListView) findViewById(R.id.searched);
                                                MyAdapter adapter=new MyAdapter(getApplicationContext(),searched,R.id.searched);
                                                search.setAdapter(adapter);
                                                search.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                                    @Override
                                                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                                       // Toast.makeText(community_activity.this,searched[position],Toast.LENGTH_LONG).show();
                                                        Intent intent= new Intent(getBaseContext(),VisitProfileActivity.class);
                                                        intent.putExtra("username",searched[position]);
                                                        startActivity(intent);
                                                        finish();
                                                    }
                                                });
                                            }
                                        }
                                        else{
                                            String empt[]={};
                                            MyAdapter adapter=new MyAdapter(getApplicationContext(),empt,R.id.searched);
                                            search.setAdapter(adapter);

                                            Toast.makeText(getApplicationContext(), "No player found!",
                                                    Toast.LENGTH_LONG).show();
                                        }

                                    }
                                    catch (Exception e){System.out.println(e.toString());}
                                }
                            },new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Toast.makeText(getApplicationContext(), "Service unavailable, try later",
                                            Toast.LENGTH_LONG).show();
                                    Log.e("VOLLEY", error.toString());
                                }}) {

                                @Override
                                protected Map<String,String> getParams(){
                                    Map<String,String> params = new HashMap<String, String>();
                                    String tes=ricerca.getText().toString();
                                    if(tes.equals(""))tes="  ";
                                    params.put("substring",tes);
                                    return params;
                                }
                                @Override
                                protected Response<String> parseNetworkResponse(NetworkResponse response) {
                                    String responseString = "";
                                    if (response != null) {
                                        responseString = String.valueOf(response.statusCode);
                                    }
                                    return super.parseNetworkResponse(response);
                                }
                            };

                            queue.add(stringRequest);
                        }
                        catch (Exception e) {
                            Toast.makeText(getApplicationContext(), "Service unavailable, try later",
                                    Toast.LENGTH_LONG).show();
                            e.printStackTrace();
                        }
                    }
                });
                search.setAdapter(adapter);
            }

        });
        buttonMatches=(Button) findViewById(R.id.buttonHistory);
        buttonMatches.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scene=3;
                vista.removeAllViews();
                getLayoutInflater().inflate(R.layout.recent_matches_layout, vista);
                if (!loadingMatches){
                    findViewById(R.id.waitMatches).setVisibility(View.GONE);
                    findViewById(R.id.messageM).setVisibility(View.GONE);
                }
                matchesList=(ListView) findViewById(R.id.lastMatches);
                RecentMatchesAdapter adapter=new RecentMatchesAdapter(getApplicationContext(),player1,player2,date,R.id.lastMatches);
                matchesList.setAdapter(adapter);
                matchesList.setOnItemClickListener(new AdapterView.OnItemClickListener()
                {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                         // Toast.makeText(community_activity.this,player1[position]+" vs "+player2[position],Toast.LENGTH_LONG).show();
                        LayoutInflater inflater = (LayoutInflater)
                                getSystemService(LAYOUT_INFLATER_SERVICE);
                        View popupView = inflater.inflate(R.layout.pop_up_matches, null);
                        TextView winner=popupView.findViewById(R.id.winnnerS);
                        if(matchesArray.get(position).get(3).equals("1")){
                            winner.setText(player1[position]);
                        }
                        if(matchesArray.get(position).get(3).equals("x")){
                            winner.setText("Draw");
                        }
                        if(matchesArray.get(position).get(3).equals("2")){
                            winner.setText(player2[position]);
                        }
                        TextView date_=popupView.findViewById(R.id.dateS);
                        date_.setText(date[position]);
                        TextView nmoves=popupView.findViewById(R.id.numberOfMovesS);
                        nmoves.setText(matchesArray.get(position).get(5));
                        TextView nchk=popupView.findViewById(R.id.numberOFChecks);
                        nchk.setText(matchesArray.get(position).get(6));
                        // create the popup window
                        int width = LinearLayout.LayoutParams.WRAP_CONTENT;
                        int height = LinearLayout.LayoutParams.WRAP_CONTENT;
                        boolean focusable = true; // lets taps outside the popup also dismiss it
                        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

                        // show the popup window
                        // which view you pass in doesn't matter, it is only used for the window tolken
                        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

                        // dismiss the popup window when touched
                        popupView.setOnTouchListener(new View.OnTouchListener() {
                            @Override
                            public boolean onTouch(View v, MotionEvent event) {
                                popupWindow.dismiss();
                                return true;
                            }
                        });
                    }
                });

            }
        });



    }
    class MyAdapter extends ArrayAdapter<String>{
        Context context;
        String name[];

        MyAdapter(Context context,String searched[],int layout){
            super(context,R.layout.row_community,layout,searched);
            this.context=context;
            this.name=searched;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View contextView,@NonNull ViewGroup parent){
            LayoutInflater layoutInflater=(LayoutInflater)getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row=layoutInflater.inflate(R.layout.row_community,parent,false);
            TextView names=row.findViewById(R.id.namesearched);
            names.setText(name[position]);
            return row;

        }
    }
    class RecentMatchesAdapter extends ArrayAdapter<String>{
        Context context;
        String player1_[];
        String player2_[];
        String date_[];

        RecentMatchesAdapter(Context context,String player1[],String player2[],String date[],int layout){
            super(context,R.layout.row_last_matches,layout,player1);
            this.context=context;
            this.player1_=player1;
            this.player2_ =player2;
            this.date_ =date;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View contextView,@NonNull ViewGroup parent){
            LayoutInflater layoutInflater=(LayoutInflater)getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row=layoutInflater.inflate(R.layout.row_last_matches,parent,false);
            TextView player1=row.findViewById(R.id.Player1);
            TextView player2=row.findViewById(R.id.Player2);
            TextView date=row.findViewById(R.id.date);
            player1.setText(player1_[position]);
            player2.setText(player2_[position]);
            date.setText(date_[position]);
            return row;

        }}}

