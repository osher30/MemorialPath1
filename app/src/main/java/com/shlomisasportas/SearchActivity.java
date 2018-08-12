package com.shlomisasportas;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class SearchActivity extends AppCompatActivity {

    Calendar myCalendar = Calendar.getInstance();
    DatePickerDialog.OnDateSetListener date,date2;
    EditText dob, age, lastName, firstName, dadName, momName, deathLocation, deathDate;
    Button search;


    public static ArrayList<Integer> keys;
    ImageView expand;
    Boolean checker = true;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        setContentView(R.layout.activity_search);

        date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };

        date2 = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel2();
            }

        };


        expand = (ImageView) findViewById(R.id.expand);
        expand.setBackgroundResource(R.drawable.show);

        expand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checker == true){
                    checker = false;
                    expand.setBackgroundResource(R.drawable.hide);
                    dob.setVisibility(View.VISIBLE);
                    age.setVisibility(View.VISIBLE);
                    dadName.setVisibility(View.VISIBLE);
                    momName.setVisibility(View.VISIBLE);
                    deathLocation.setVisibility(View.VISIBLE);
                }else {
                    checker = true;
                    expand.setBackgroundResource(R.drawable.show);
                    dob.setVisibility(View.INVISIBLE);
                    age.setVisibility(View.INVISIBLE);
                    dadName.setVisibility(View.INVISIBLE);
                    momName.setVisibility(View.INVISIBLE);
                    deathLocation.setVisibility(View.INVISIBLE);
                }
            }
        });

        dob = (EditText) findViewById(R.id.dob);
        age = (EditText) findViewById(R.id.age);
        lastName = (EditText) findViewById(R.id.lastName);
        firstName = (EditText) findViewById(R.id.firstName);
        dadName = (EditText) findViewById(R.id.dadName);
        momName = (EditText) findViewById(R.id.momName);
        deathDate = (EditText) findViewById(R.id.deathDate);
        deathLocation = (EditText) findViewById(R.id.deathLocation);
        search = (Button) findViewById(R.id.search);

        dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(SearchActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        deathDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(SearchActivity.this, date2, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (dob.getText().toString().equals("") & age.getText().toString().equals("") &
                        lastName.getText().toString().equals("") & firstName.getText().toString().equals("") &
                        dadName.getText().toString().equals("") & momName.getText().toString().equals("") &
                        deathDate.getText().toString().equals("") & deathLocation.getText().toString().equals("")){
                    Toast.makeText(SearchActivity.this, "", Toast.LENGTH_SHORT).show();
                }else{

                    String[] allInputs = {firstName.getText().toString()
                    ,lastName.getText().toString() , dadName.getText().toString()
                    ,momName.getText().toString(), age.getText().toString()
                    ,dob.getText().toString(), deathDate.getText().toString()
                    , deathLocation.getText().toString()};

                    keys = new ArrayList<>();

                    for (int i = 0; i < MainActivity.allUsers.size(); i++){
                        boolean result = true;
                        if (!allInputs[0].equals("")){
                           if( MainActivity.allUsers.get(i).getFirstName().contains(allInputs[0])){

                           }else {
                               result = false;
                           }
                        }

                        if (!allInputs[1].equals("")){
                            if( MainActivity.allUsers.get(i).getLastName().contains(allInputs[1])){

                            }else {
                                result = false;
                            }
                        }

                        if (!allInputs[2].equals("")){
                            if( MainActivity.allUsers.get(i).getDadName().equals(allInputs[2])){

                            }else {
                                result = false;
                            }
                        }

                        if (!allInputs[3].equals("")){
                            if( MainActivity.allUsers.get(i).getMomName().equals(allInputs[3])){

                            }else {
                                result = false;
                            }
                        }

                        if (!allInputs[4].equals("")){
                            if( MainActivity.allUsers.get(i).getAge().equals(allInputs[4])){

                            }else {
                                result = false;
                            }
                        }

                        if (!allInputs[5].equals("")){
                            if( MainActivity.allUsers.get(i).getDateOfBirth().equals(allInputs[5])){

                            }else {
                                result = false;
                            }
                        }

                        if (!allInputs[6].equals("")){
                            if( MainActivity.allUsers.get(i).getDateOfDeath().equals(allInputs[6])){

                            }else {
                                result = false;
                            }
                        }

                        if (!allInputs[7].equals("")){
                            if( MainActivity.allUsers.get(i).getDeathLocation().equals(allInputs[7])){

                            }else {
                                result = false;
                            }
                        }

                        if (result == true){
                            Log.i("found", " : "+i);
                            keys.add(i);
                        }

                    }
                        Intent i = new Intent(SearchActivity.this, ShowSearchDataActivity.class);
                        startActivity(i);
                }

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return true;
    }

    private void updateLabel() {
        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        dob.setText(sdf.format(myCalendar.getTime()));
    }

    private void updateLabel2() {
        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        deathDate.setText(sdf.format(myCalendar.getTime()));
    }
}
