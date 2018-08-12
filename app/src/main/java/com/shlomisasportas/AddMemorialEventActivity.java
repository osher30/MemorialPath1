package com.shlomisasportas;

import android.app.DatePickerDialog;
import android.icu.text.SimpleDateFormat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.Locale;

public class AddMemorialEventActivity extends AppCompatActivity {


    DatePickerDialog.OnDateSetListener date;
    Calendar myCalendar = Calendar.getInstance();
    EditText datee, place, location;

    Button add;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_memorial_event);

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

        datee = (EditText) findViewById(R.id.date);
        place = (EditText) findViewById(R.id.place);
        location = (EditText) findViewById(R.id.location);

        add = (Button) findViewById(R.id.add);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (datee.getText().toString().equals("") || place.getText().toString().equals("")
                        || location.getText().toString().equals("")){
                    Toast.makeText(AddMemorialEventActivity.this, "Fill all fields first", Toast.LENGTH_LONG).show();
                }else{
                    addData(place.getText().toString(), datee.getText().toString(),
                            location.getText().toString());

                    Toast.makeText(AddMemorialEventActivity.this, "Event added", Toast.LENGTH_LONG).show();
                    finish();
                }
            }
        });

        datee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(AddMemorialEventActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });



    }

    private void updateLabel() {
        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        datee.setText(sdf.format(myCalendar.getTime()));
    }


    public void addData(String place,String dateo,String location){
        FirebaseDatabase database = FirebaseDatabase.getInstance();

        // Log.i("reference",""+SearchActivity.keys.get(MainActivity.allkeys.get( getIntent().getIntExtra("position",0))));

        DatabaseReference posts = database.getReference("Events");//.child(""+MainActivity.allkeys.get(SearchActivity.keys.get(getIntent().getIntExtra("position",0))));
        posts.keepSynced(true);

        String key = MainActivity.allkeys.get(SearchActivity.keys.get(getIntent().getIntExtra("position",0)));

        EventPost eventPost = new EventPost();
        eventPost.setPlace(place);
        eventPost.setDatee(dateo);
        eventPost.setKey(key);
        eventPost.setLocation(location);
        posts.push().setValue(eventPost);


    }
}
