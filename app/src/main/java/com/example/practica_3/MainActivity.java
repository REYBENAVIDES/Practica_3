package com.example.practica_3;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void btnAcceder(View view){

        EditText txtUsuario=(EditText) findViewById(R.id.txtUsuario);
        EditText txtContraseña=(EditText) findViewById(R.id.txtContraseña);
        TextView txtvolley = findViewById(R.id.txtLista);

        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="https://api.uealecpeterson.net/public/login";
        Bundle b = new Bundle();

        JSONObject requestBody = new JSONObject();
        try {
            requestBody.put("correo", txtUsuario.getText());
            requestBody.put("clave", txtContraseña.getText());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, requestBody,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String token = response.getString("access_token");
                            b.putString("TOKEN", token);
                            Intent intent=new Intent(MainActivity.this,MainActivity2.class);
                            intent.putExtras(b);
                            startActivity(intent);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        txtvolley.setText(error.networkResponse.statusCode +"  Error");
                    }
                });

        queue.add(jsonObjectRequest);

    }
}