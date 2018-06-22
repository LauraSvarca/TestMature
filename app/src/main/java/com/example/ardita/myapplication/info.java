package com.example.ardita.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class info extends AppCompatActivity implements View.OnClickListener {

    Button fillo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        fillo = (Button) findViewById(R.id.fillo);
        fillo.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.fillo:
                startActivity(new Intent(this, Test.class));
                break;
        }
    }
}
