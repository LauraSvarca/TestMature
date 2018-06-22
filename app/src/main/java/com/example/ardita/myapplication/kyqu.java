package com.example.ardita.myapplication;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.app.Activity;
import android.support.v7.widget.Toolbar;
import android.location.Location;
import android.view.Menu;
import android.content.Context;
import android.content.pm.PackageManager;
import android.provider.Settings;
import android.content.DialogInterface;
import android.app.AlertDialog;
import android.Manifest;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View.OnClickListener;
import android.widget.*;
public class kyqu extends AppCompatActivity implements AdapterView.OnItemSelectedListener, OnClickListener {

    String[] Drejtimi = { "Shkenca natyrore", "Shkenca shoqerore", "Mjeksia", "Shkolla teknike" };
    CheckBox matematike,fizike,biologji,gjuheshqipe,gjuheangleze,kimi;
    Button vazhdo;
    Button Location;
    private DrawerLayout drawer;
    private ActionBarDrawerToggle Toggle;
    private static final int REQUEST_LOCATION = 1;
    LocationManager locationManager;
    String lattitude, longitude;
    TextView textView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kyqu);


        ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);


        Spinner spin = (Spinner) findViewById(R.id.Spinner);
        spin.setOnItemSelectedListener(this);
        addListenerOnButtonClick();
        textView = (TextView) findViewById(R.id.get_location);
        Location = (Button)findViewById(R.id.Location);
        vazhdo.setOnClickListener(this);
        Location.setOnClickListener(this);
        drawer = (DrawerLayout) findViewById(R.id.drawer);
        Toggle = new ActionBarDrawerToggle(this, drawer, R.string.Hape, R.string.Mbylle);
        drawer.addDrawerListener(Toggle);
        NavigationView Navigation = (NavigationView) findViewById(R.id.nv);
        Toggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        DrawerContent(Navigation);
        //Navigation.setNavigationItemSelectedListener(this);

        //Creating the ArrayAdapter instance having the country list
        ArrayAdapter aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item,Drejtimi);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        spin.setAdapter(aa);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(Toggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public  void zgjedhAksionin(MenuItem menuItem) {
       // android.support.v4.app.Fragment fragment = null;
        android.support.v4.app.Fragment fragment = null;
        Class fragmentClass;
        switch (menuItem.getItemId()) {
            case R.id.Kryefaqja:
                fragmentClass = kyqu.class;
                break;
            case R.id.histroriku:
                fragmentClass = Historiku.class;
                break;
            case R.id.Location:
                fragmentClass = Rregullime.class;
                break;
            case R.id.Shkyqu:
                fragmentClass = Shkyqu.class;
                break;
                default:
                     fragmentClass = kyqu.class;

        }

        try {
            fragment = (android.support.v4.app.Fragment) fragmentClass.newInstance();

        } catch (Exception e) {
            e.printStackTrace();
        }
        android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.Frame,fragment).commit();
        menuItem.setChecked(true);
        setTitle(menuItem.getTitle());
        drawer.closeDrawers();
    }
    private void DrawerContent (NavigationView navigationView){

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                zgjedhAksionin(item);
                return true;
            }
        });
    }

    private void addListenerOnButtonClick()
    {
        matematike=(CheckBox)findViewById(R.id.lenda1);
        fizike=(CheckBox)findViewById(R.id.lenda2);
        biologji=(CheckBox)findViewById(R.id.lenda3);
        gjuheshqipe=(CheckBox)findViewById(R.id.lenda4);
        gjuheangleze=(CheckBox)findViewById(R.id.lenda5);
        kimi=(CheckBox)findViewById(R.id.lenda6);
        vazhdo=(Button)findViewById(R.id.vazhdo);

    }


    //Performing action onItemSelected and onNothing selected
    @Override
    public void onItemSelected(AdapterView<?> arg0, View arg1, int position,long id) {
        Toast.makeText(getApplicationContext(),Drejtimi[position] ,Toast.LENGTH_LONG).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    @Override
    public void onClick(View v) {
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            buildAlertMessageNoGps();

        } else if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            getLocation();
        }
        switch (v.getId()) {
            case R.id.vazhdo:

                startActivity(new Intent(this, info.class));
                break;
        }



    }

    private void buildAlertMessageNoGps() {

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Please Turn ON your GPS Connection")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        dialog.cancel();
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();

    }

    private void getLocation() {
        if (ActivityCompat.checkSelfPermission(kyqu.this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission
                (kyqu.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(kyqu.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);

        } else {
            Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);


            if (location != null) {
                double latti = location.getLatitude();
                double longi = location.getLongitude();
                lattitude = String.valueOf(latti);
                longitude = String.valueOf(longi);

                textView.setText("Your current location is"+ "\n" + "Lattitude = " + lattitude
                        + "\n" + "Longitude = " + longitude);

            }else{

                Toast.makeText(this,"Unable to Trace your location",Toast.LENGTH_SHORT).show();

            }
        }

    }

 /*   @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.Kryefaqja){
            startActivity(new Intent(this, kyqu.class));
        }
        if (id == R.id.histroriku){

            startActivity(new Intent(this, Historiku.class));
        }
        if (id == R.id.Location){

            startActivity(new Intent(this, Location.class));
        }
        if (id == R.id.Shkyqu){

            startActivity(new Intent(this, Shkyqu.class));
        }


        return false;
    }*/
}
