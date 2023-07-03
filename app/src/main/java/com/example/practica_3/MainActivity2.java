package com.example.practica_3;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        TextView txtToken= (TextView) findViewById(R.id.txtvToken);
        TextView txtDatos= (TextView) findViewById(R.id.txtvDatos);
        Bundle b = this.getIntent().getExtras();
        txtToken.setText("TOKEN: " + b.getString("TOKEN"));

        RequestQueue queue = Volley.newRequestQueue(this);
            String url = "https://api.uealecpeterson.net/public/productos/search";

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            txtDatos.setText(response.toString());
                            Log.d(TAG, "Response: " + response.toString());
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.e(TAG, "Error en la solicitud: " + error.getMessage());
                            // Maneja los errores de la solicitud
                        }
                    }) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> headers = new HashMap<>();
                    headers.put("Content-Type", "application/json");
                    headers.put("Authorization", "Bearer " + b.getString("TOKEN"));
                    // Agrega otros encabezados según tus necesidades
                    return headers;
                }
            };

            queue.add(jsonObjectRequest);
        }

    }
