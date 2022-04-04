package com.example.dipanshkhandelwal.chess;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.EditText;
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
import java.util.concurrent.TimeUnit;

public class Profile extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        final TextView username=(TextView) findViewById(R.id.profile_username);
        TextView wins=(TextView) findViewById((R.id.profile_win));
        TextView losses=(TextView) findViewById((R.id.profile_losses));
        TextView draws=(TextView) findViewById((R.id.profile_draws));
        TextView total=(TextView) findViewById((R.id.profile_total));
        TextView email=(TextView) findViewById(R.id.profile_email);
        username.setText(PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("username","none"));

        try{

            RequestQueue queue= Volley.newRequestQueue(getApplicationContext());
            String url ="https://profileservice-dot-chesseleven.oa.r.appspot.com/getProfile";
            StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    try {
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
                    params.put("username",username.getText().toString());
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
}