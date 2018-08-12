package com.shlomisasportas;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.icu.text.SimpleDateFormat;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

public class AddNewUserActivity extends AppCompatActivity {

    DatePickerDialog.OnDateSetListener date,date2;
    Calendar myCalendar = Calendar.getInstance();
    EditText dob, age, lastName, firstName, dadName, momName, deathLocation, deathDate, profilepicURL,
            part, row, city, gravenumber, parcel, latitude, longitude;
    Button add;
    CheckBox auto;

    GPSTracker gpsTracker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_user);

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

        if (Build.VERSION.SDK_INT >= 23){


            if (checkPermission(android.Manifest.permission.ACCESS_FINE_LOCATION,getApplicationContext(),this)) {
                //You fetch the Location here

                //code to use the
            }
            else
            {
                requestPermission(android.Manifest.permission.ACCESS_FINE_LOCATION,getApplicationContext(),this);
            }

        }

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

        parcel = (EditText) findViewById(R.id.parcel);
        part = (EditText) findViewById(R.id.part);
        gravenumber = (EditText) findViewById(R.id.gravenum);
        city = (EditText) findViewById(R.id.city);
        row = (EditText) findViewById(R.id.row);
        dob = (EditText) findViewById(R.id.dob);
        age = (EditText) findViewById(R.id.age);
        lastName = (EditText) findViewById(R.id.lastName);
        firstName = (EditText) findViewById(R.id.firstName);
        dadName = (EditText) findViewById(R.id.dadName);
        momName = (EditText) findViewById(R.id.momName);
        deathDate = (EditText) findViewById(R.id.deathDate);
        deathLocation = (EditText) findViewById(R.id.deathLocation);
        profilepicURL = (EditText) findViewById(R.id.profilepicurl);
        latitude = (EditText) findViewById(R.id.latitude);
        longitude = (EditText) findViewById(R.id.longitude);

        add = (Button) findViewById(R.id.add);

        dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(AddNewUserActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        deathDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(AddNewUserActivity.this, date2, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });



        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (dob.getText().toString().equals("") || age.getText().toString().equals("") ||
                lastName.getText().toString().equals("") || firstName.getText().toString().equals("") ||
                dadName.getText().toString().equals("") || momName.getText().toString().equals("") ||
                deathDate.getText().toString().equals("") || deathLocation.getText().toString().equals("") ||
                profilepicURL.getText().toString().equals("") || city.getText().toString().equals("")
                        || row.getText().toString().equals("") || gravenumber.getText().toString().equals("")
                        || parcel.getText().toString().equals("") ||part.getText().toString().equals("")
                        || latitude.getText().toString().equals("") || longitude.getText().toString().equals("")){
                    Toast.makeText(AddNewUserActivity.this, "נדרש למלא את כל השדות", Toast.LENGTH_LONG).show();
                }else{
                    addData(firstName.getText().toString(), lastName.getText().toString(), dob.getText().toString(),
                            age.getText().toString(), deathDate.getText().toString(), deathLocation.getText().toString(),
                            momName.getText().toString(), dadName.getText().toString(), profilepicURL.getText().toString());
                    finish();
                    Toast.makeText(AddNewUserActivity.this,"הפרופיל נוצר", Toast.LENGTH_LONG).show();
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

        DatabaseReference posts = database.getReference("allUsers");
        //this code for keep posts even app offline until the app online again
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
        posts.push().setValue(post);
    }

    public  void requestPermission(String strPermission,Context _c,Activity _a){

        if (ActivityCompat.shouldShowRequestPermissionRationale(_a,strPermission)){
            Toast.makeText(AddNewUserActivity.this,"GPS permission allows us to access location data. Please allow in App Settings for additional functionality.",Toast.LENGTH_LONG).show();
        } else {

            ActivityCompat.requestPermissions(_a,new String[]{strPermission},1);
        }
    }

    public static boolean checkPermission(String strPermission,Context _c,Activity _a){
        int result = ContextCompat.checkSelfPermission(_c, strPermission);
        if (result == PackageManager.PERMISSION_GRANTED){

            return true;

        } else {

            return false;

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {

            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                   // fetchLocationData();


                } else {

                    Toast.makeText(getApplicationContext(),"Permission Denied, You cannot access location data.",Toast.LENGTH_LONG).show();
                    finish();
                }
                break;

        }
    }
}
