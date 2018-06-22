package com.example.ardita.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Test extends AppCompatActivity implements View.OnClickListener {

     Button Vazhdo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        Vazhdo = (Button) findViewById(R.id.vazhdo1);
        Vazhdo.setOnClickListener( this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.vazhdo1:

                startActivity(new Intent(this, Matematika.class));
                break;

        }
    }
}
