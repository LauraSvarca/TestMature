package com.example.ardita.myapplication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v4.widget.CircularProgressDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
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

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

     private  Button Kyqu;
    private EditText editPerdoruesi, editFjalekalimi;
     private  TextView Regjistrohu;
     private ProgressDialog progressDialog;

    //Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editPerdoruesi = (EditText) findViewById(R.id.editPerdoruesi);
        editFjalekalimi = (EditText) findViewById(R.id.editFjalekalimi);
        Kyqu = (Button) findViewById(R.id.Kyqu);
        Regjistrohu = (TextView) findViewById(R.id.Regjistrohu);
      //  toolbar = (Toolbar) findViewById(R.id.toolbar);
      // toolbar.setTitle("Test Mature");
      //  setSupportActionBar(toolbar);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(" ju lutem prisni ...");
        Kyqu.setOnClickListener(this);
        Regjistrohu.setOnClickListener(this);

    }

    private  void userLogin(){
        final String  Perdoruesi =  editPerdoruesi.getText().toString().trim();
        final String  Fjalekalimi =  editFjalekalimi.getText().toString().trim();
         progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                Constants.URL_LOGIN,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        try {
                            JSONObject obj =  new JSONObject(response);
                            if(!obj.getBoolean("error")){
                                ConstantsLogin.getInstance(getApplicationContext()).userLogin(
                                        obj.getInt("id"),
                                        obj.getString("emri"),
                                        obj.getString("mbiemri"),
                                        obj.getString("perdoruesi")
                                );
                                Toast.makeText(getApplicationContext(), "Perdoruesi u kyq me sukses", Toast.LENGTH_LONG).show();
                            }else {
                                Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();

                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("perdoruesi", Perdoruesi);
                params.put("fjalekalimi", Fjalekalimi);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
        startActivity(new Intent(this, kyqu.class));

    }

    @Override
    public void onClick(View view) {
        if(view == Kyqu)
            userLogin();


            if (view == Regjistrohu)
                startActivity(new Intent(this, Regjistrohu.class));


    }
}
