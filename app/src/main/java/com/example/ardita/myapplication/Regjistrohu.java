package com.example.ardita.myapplication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Regjistrohu extends AppCompatActivity implements View.OnClickListener {

    private Button Regjistrohu;
     private EditText editEmri, editMbiemri, editPerdoruesi, editFjalekalimi;
     private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regjistrohu);
        Regjistrohu = (Button) findViewById(R.id.Regjistrohu);
        editEmri = (EditText) findViewById(R.id.editEmri);
        editMbiemri = (EditText) findViewById(R.id.editMbiemri);
        editPerdoruesi = (EditText) findViewById(R.id.editPerdoruesi);
        editFjalekalimi = (EditText) findViewById(R.id.editFjalekalimi);
        progressDialog = new ProgressDialog(this);
        Regjistrohu.setOnClickListener(this);
    }

    private void registerUser(){
        final String Emri = editEmri.getText().toString().trim();
        final String Mbiemri = editMbiemri.getText().toString().trim();
        final String Perdoruesi = editPerdoruesi.getText().toString().trim();
        final String Fjalekalimi = editFjalekalimi.getText().toString().trim();
        progressDialog.setMessage("Duke regjistruar perdoruesin...");
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                Constants.URL_REGISTER,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                       try {
                            JSONObject jsonObject = new JSONObject(response);

                            Toast.makeText(getApplicationContext(), jsonObject.getString("message"), Toast.LENGTH_LONG).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error){
        progressDialog.hide();
                       Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("emri", Emri);
                params.put("mbiemri", Mbiemri);
                params.put("perdoruesi", Perdoruesi);
                params.put("fjalekalimi", Fjalekalimi);
                return params;

            }
        };
      RequestQueue requestQueue = Volley.newRequestQueue(this);
      requestQueue.add(stringRequest);
    }

    @Override
    public void onClick(View v) {
     if ( v == Regjistrohu)
         registerUser();
       /* switch (v.getId()){
            case R.id.Regjistrohu:
                startActivity(new Intent(this, MainActivity.class));
        }*/
    }
}
