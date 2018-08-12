package com.shlomisasportas;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class UpComingEventsActivity extends AppCompatActivity {

    ListView listView ;
    public static ArrayList<EventPost> allEvents= new ArrayList<>();
    public static ArrayList<String> allkeys= new ArrayList<>();
    public ArrayList<String> eventplace=new ArrayList<>();
    public ArrayList<String> dates = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_up_coming_events);

        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yy");
        for (int i = 0; i < 8; i++) {
            Calendar calendar = new GregorianCalendar();
            calendar.add(Calendar.DATE, i);
            String day = sdf.format(calendar.getTime());
            Log.i("dates", day);
            dates.add(day);
        }

        FirebaseDatabase mFirebaseDatabase = FirebaseDatabase.getInstance();
        final DatabaseReference databaseReference =    mFirebaseDatabase.getReference("Events");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot childDataSnapshot : dataSnapshot.getChildren()) {
                    Log.v("Database",""+ childDataSnapshot.getKey()); //displays the key for the node

                    if (dates.contains(childDataSnapshot.child("datee").getValue())){

                        allkeys.add(""+childDataSnapshot.child("key").getValue());
                        eventplace.add("מהות האירוע: "+childDataSnapshot.child("place").getValue()+"\nתאריך: "
                                +""+childDataSnapshot.child("datee").getValue()+"\nמיקום:  "+childDataSnapshot.child("location").getValue());
                    }


                }

                listView = (ListView) findViewById(R.id.list);

                final ArrayAdapter<String> adapter = new ArrayAdapter<String>(UpComingEventsActivity.this,
                        android.R.layout.simple_list_item_1, android.R.id.text1, eventplace);


                // Assign adapter to ListView
                listView.setAdapter(adapter);

                // ListView Item Click Listener
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> parent, View view,
                                            int position, long id) {

                        // ListView Clicked item index
                        int itemPosition     = position;

                        // ListView Clicked item value
                        String  itemValue    = (String) listView.getItemAtPosition(position);

                        Intent i = new Intent(UpComingEventsActivity.this,EventInfoActivity.class);
                        i.putExtra("event", eventplace.get(position));
                        i.putExtra("key", allkeys.get(position));
                        startActivity(i);
                    }

                });

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
