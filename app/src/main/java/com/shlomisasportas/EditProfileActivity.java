package com.shlomisasportas;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.Locale;

public class EditProfileActivity extends AppCompatActivity {


    DatePickerDialog.OnDateSetListener date,date2;
    Calendar myCalendar = Calendar.getInstance();
    EditText dob, age, lastName, firstName, dadName, momName, deathLocation, deathDate, profilepicURL,
            part, row, city, gravenumber, parcel, latitude, longitude ;
    Button edit;
    CheckBox auto;

    GPSTracker gpsTracker;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);


        gpsTracker = new GPSTracker(this);
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

        int itemPosition = getIntent().getIntExtra("position",0);

        auto = (CheckBox) findViewById(R.id.auto);

        auto.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                                            @Override
                                            public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
                                                if (isChecked){

                                                    longitude.setText(gpsTracker.getLongitude()+"");
                                                    latitude.setText(gpsTracker.getLatitude()+"");

                                                }else {
                                                    latitude.setText("");
                                                    longitude.setText("");
                                                }
                                            }
                                        }
        );

        latitude = (EditText) findViewById(R.id.latitude); latitude.setText(ShowSearchDataActivity.latitude);
        longitude = (EditText) findViewById(R.id.longitude); longitude.setText(ShowSearchDataActivity.longitude);


        parcel = (EditText) findViewById(R.id.parcel); parcel.setText(MainActivity.allUsers.get(SearchActivity.keys.get(itemPosition)).getParcel());
        part = (EditText) findViewById(R.id.part); part.setText(MainActivity.allUsers.get(SearchActivity.keys.get(itemPosition)).getParcel());
        gravenumber = (EditText) findViewById(R.id.gravenum);gravenumber.setText(MainActivity.allUsers.get(SearchActivity.keys.get(itemPosition)).getGravenumber());
        city = (EditText) findViewById(R.id.city);city.setText(MainActivity.allUsers.get(SearchActivity.keys.get(itemPosition)).getCity());
        row = (EditText) findViewById(R.id.row);row.setText(MainActivity.allUsers.get(SearchActivity.keys.get(itemPosition)).getRow());
        dob = (EditText) findViewById(R.id.dob);    dob.setText(MainActivity.allUsers.get(SearchActivity.keys.get(itemPosition)).getDateOfBirth());
        age = (EditText) findViewById(R.id.age);    age.setText(MainActivity.allUsers.get(SearchActivity.keys.get(itemPosition)).getAge());
        lastName = (EditText) findViewById(R.id.lastName);  lastName.setText(MainActivity.allUsers.get(SearchActivity.keys.get(itemPosition)).getLastName());
        firstName = (EditText) findViewById(R.id.firstName);    firstName.setText(MainActivity.allUsers.get(SearchActivity.keys.get(itemPosition)).getFirstName());
        dadName = (EditText) findViewById(R.id.dadName);    dadName.setText(MainActivity.allUsers.get(SearchActivity.keys.get(itemPosition)).getDadName());
        momName = (EditText) findViewById(R.id.momName);    momName.setText(MainActivity.allUsers.get(SearchActivity.keys.get(itemPosition)).getMomName());
        deathDate = (EditText) findViewById(R.id.deathDate);    deathDate.setText(MainActivity.allUsers.get(SearchActivity.keys.get(itemPosition)).getDateOfDeath());
        deathLocation = (EditText) findViewById(R.id.deathLocation);    deathLocation.setText(MainActivity.allUsers.get(SearchActivity.keys.get(itemPosition)).getDeathLocation());
        profilepicURL = (EditText) findViewById(R.id.profilepicurl);    profilepicURL.setText(MainActivity.allUsers.get(SearchActivity.keys.get(itemPosition)).getProfilePicture());

        edit = (Button) findViewById(R.id.edit);

        dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(EditProfileActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        deathDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(EditProfileActivity.this, date2, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });


        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (dob.getText().toString().equals("") || age.getText().toString().equals("") ||
                        lastName.getText().toString().equals("") || firstName.getText().toString().equals("") ||
                        dadName.getText().toString().equals("") || momName.getText().toString().equals("") ||
                        deathDate.getText().toString().equals("") || deathLocation.getText().toString().equals("") ||
                        profilepicURL.getText().toString().equals("")){
                    Toast.makeText(EditProfileActivity.this, "נדרש למלא את כל השדות", Toast.LENGTH_LONG).show();
                }else{
                    addData(firstName.getText().toString(), lastName.getText().toString(), dob.getText().toString(),
                            age.getText().toString(), deathDate.getText().toString(), deathLocation.getText().toString(),
                            momName.getText().toString(), dadName.getText().toString(), profilepicURL.getText().toString());

                    Toast.makeText(EditProfileActivity.this,"פרופיל עודכן", Toast.LENGTH_LONG).show();
                    Intent i = new Intent(EditProfileActivity.this, MainActivity.class);
                    startActivity(i);
                    finish();

                }
            }
        });
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

    public void addData(String firstName,String lastName,String dayOfBirth,String Age,String DateOfDeath,
                        String deathLocation,String momsName,String dadName,String profilepicture){
        FirebaseDatabase database = FirebaseDatabase.getInstance();

       // Log.i("reference",""+SearchActivity.keys.get(MainActivity.allkeys.get( getIntent().getIntExtra("position",0))));

        DatabaseReference posts = database.getReference("allUsers").child(""+MainActivity.allkeys.get(SearchActivity.keys.get(getIntent().getIntExtra("position",0))));
        posts.keepSynced(true);

        Post post = new Post();
        post.setFirstName(firstName);
        post.setLastName(lastName);
        post.setAge(Age);
        post.setDateOfBirth(dayOfBirth);
        post.setDadName(dadName);
        post.setMomName(momsName);
        post.setDateOfDeath(DateOfDeath);
        post.setDeathLocation(deathLocation);
        post.setProfilePicture(profilepicture);
        post.setCity(city.getText().toString());
        post.setGravenumber(gravenumber.getText().toString());
        post.setRow(row.getText().toString());
        post.setParcel(parcel.getText().toString());
        post.setPart(part.getText().toString());
        post.setLatitude(latitude.getText().toString());
        post.setLongitude(longitude.getText().toString());
        posts.setValue(post);
    }

}
