package com.example.dipanshkhandelwal.chess;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
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

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class VisitProfileActivity extends AppCompatActivity {

    private String user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visit_profile);
        final TextView username=(TextView) findViewById(R.id.profile_username);
        TextView wins=(TextView) findViewById((R.id.profile_win));
        TextView losses=(TextView) findViewById((R.id.profile_losses));
        TextView draws=(TextView) findViewById((R.id.profile_draws));
        TextView total=(TextView) findViewById((R.id.profile_total));
        TextView email=(TextView) findViewById(R.id.profile_email);
        Button follow=(Button) findViewById(R.id.followButton);
        ProgressBar pr=(ProgressBar)findViewById(R.id.progress_visita);
        LinearLayout ln=(LinearLayout)findViewById(R.id.linearLayout);
        SharedPreferences shared=PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        user=getIntent().getStringExtra("username");
        username.setText(user);
        follow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button f=(Button)v;
                if (f.getText().toString().equals("Follow")){
                    //Case follow--------------------------------
                    try{
                        final EditText usname=(EditText)findViewById(R.id.login_us) ;
                        final EditText pw=(EditText)findViewById(R.id.login_pw) ;
                        RequestQueue queue=Volley.newRequestQueue(getApplicationContext());
                        String url ="https://community-service-dot-chesseleven.oa.r.appspot.com/addFollower";
                        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                                try {
                                    System.out.println(response);
                                    Log.i("VOLLEY", response);
                                    JSONObject jsonObject = new JSONObject(response);
                                    System.out.println(jsonObject.toString());
                                    if (jsonObject.get("success").equals("true")) {
                                        Toast.makeText(getApplicationContext(), "You are follower of "+user,
                                                Toast.LENGTH_LONG).show();
                                        f.setText("Unfollow");
                                    }
                                    else{
                                        Toast.makeText(getApplicationContext(), "Error",
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
                                params.put("follower",shared.getString("username","null"));
                                params.put("followed",user);
                                return params;
                            }
                            @Override
                            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                                String responseString = "";
                                if (response != null) {
                                    responseString = String.valueOf(response.statusCode);
                                    // can get more details such as response.headers
                                }
                                //return Response.success(responseString, HttpHeaderParser.parseCacheHeaders(response));
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
                else{
                    //Case Unfollow--------------------------

                    try{
                        final EditText usname=(EditText)findViewById(R.id.login_us) ;
                        final EditText pw=(EditText)findViewById(R.id.login_pw) ;
                        RequestQueue queue=Volley.newRequestQueue(getApplicationContext());
                        String url ="https://community-service-dot-chesseleven.oa.r.appspot.com/removeFollower";
                        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                                try {
                                    System.out.println(response);
                                    Log.i("VOLLEY", response);
                                    JSONObject jsonObject = new JSONObject(response);
                                    System.out.println(jsonObject.toString());
                                    if (jsonObject.get("success").equals("true")) {
                                        Toast.makeText(getApplicationContext(), "You are no more follower of "+user,
                                                Toast.LENGTH_LONG).show();
                                        f.setText("Follow");
                                    }
                                    else{
                                        Toast.makeText(getApplicationContext(), "Error",
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
                                params.put("follower",shared.getString("username","null"));
                                params.put("followed",user);
                                return params;
                            }
                            @Override
                            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                                String responseString = "";
                                if (response != null) {
                                    responseString = String.valueOf(response.statusCode);
                                    // can get more details such as response.headers
                                }
                                //return Response.success(responseString, HttpHeaderParser.parseCacheHeaders(response));
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
            }
        });
        try{
            RequestQueue queue= Volley.newRequestQueue(getApplicationContext());
            String url ="https://profileservice-dot-chesseleven.oa.r.appspot.com/getProfile";
            StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        pr.setVisibility(View.GONE);
                        ln.setVisibility(View.VISIBLE);
                        System.out.println(response);
                        Log.i("VOLLEY", response);
                        JSONObject jsonObject = new JSONObject(response);
                        System.out.println(jsonObject.toString());
                        if (jsonObject.get("success").equals("true")) {
                            wins.setText(jsonObject.getString("win"));
                            draws.setText(jsonObject.getString("draws"));
                            losses.setText(jsonObject.getString("losses"));
                            int totalScore =Integer.parseInt(wins.getText().toString())*10-Integer.parseInt(losses.getText().toString())*10-Integer.parseInt(draws.getText().toString())*5;
                            total.setText(Integer.toString(totalScore));
                            email.setText(jsonObject.getString("email"));

                        }
                        else{
                            Toast.makeText(getApplicationContext(), "Wrong username",
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
                    params.put("username",user);
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
            String url ="https://community-service-dot-chesseleven.oa.r.appspot.com/isFollower";
            StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        pr.setVisibility(View.GONE);
                        ln.setVisibility(View.VISIBLE);
                        System.out.println(response);
                        Log.i("VOLLEY", response);
                        JSONObject jsonObject = new JSONObject(response);
                        System.out.println(jsonObject.toString());
                        if (jsonObject.get("success").equals("true")) {
                            follow.setText("Unfollow");
                            follow.setVisibility(View.VISIBLE);
                        }
                        else{
                            follow.setText("Follow");
                            follow.setVisibility(View.VISIBLE);
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
                    params.put("follower",shared.getString("username","username"));
                    params.put("followed",user);
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
            //e.printStackTrace();
            System.out.println("oooooooooooh "+e.toString());
        }
    }
    private void callFollow(){}
    private void callUnfollow(){}
}