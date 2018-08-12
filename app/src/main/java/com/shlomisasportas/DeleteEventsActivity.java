package com.shlomisasportas;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DeleteEventsActivity extends AppCompatActivity {


    public static ArrayList<EventPost> allEvents;
    public static ArrayList<String> allkeys;
    public ArrayList<String> eventplace;
    ListView listView ;

    ImageView profile, video, picture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_events);
        allEvents = new ArrayList<>();
        allkeys = new ArrayList<>();
        eventplace = new ArrayList<>();

        profile = (ImageView) findViewById(R.id.profile);
        video = (ImageView) findViewById(R.id.video);
        picture = (ImageView) findViewById(R.id.music);

        picture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(DeleteEventsActivity.this, PhotoActivity.class);
                i.putExtra("position", getIntent().getIntExtra("position", 0));
                i.putExtra("data", getIntent().getStringExtra("data"));
                i.putExtra("url", getIntent().getStringExtra("url"));
                startActivity(i);
            }
        });


        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(DeleteEventsActivity.this, IndiviualsInfoActivity.class);
                i.putExtra("position", getIntent().getIntExtra("position",0));
                i.putExtra("data", getIntent().getStringExtra("data"));
                i.putExtra("url",getIntent().getStringExtra("url"));
                startActivity(i);
            }
        });


        video.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(DeleteEventsActivity.this, YoutubeVideoActivity.class);
                i.putExtra("position", getIntent().getIntExtra("position", 0));
                i.putExtra("data", getIntent().getStringExtra("data"));
                i.putExtra("url", getIntent().getStringExtra("url"));
                startActivity(i);
            }
        });

        FirebaseDatabase mFirebaseDatabase = FirebaseDatabase.getInstance();
        final DatabaseReference databaseReference =    mFirebaseDatabase.getReference("Events");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot childDataSnapshot : dataSnapshot.getChildren()) {
                    Log.v("Database",""+ childDataSnapshot.getKey()); //displays the key for the node

                    EventPost post = new EventPost();
                    String key = MainActivity.allkeys.get(SearchActivity.keys.get(getIntent().getIntExtra("position",0)));
                    if (key.equals(""+childDataSnapshot.child("key").getValue())){
                        post.setPlace(""+childDataSnapshot.child("place").getValue());
                        post.setDatee(""+childDataSnapshot.child("datee").getValue());
                        post.setLocation(""+childDataSnapshot.child("location").getValue());
                        allkeys.add(childDataSnapshot.getKey());

                        allEvents.add(post);

                        eventplace.add("מיקום: "+childDataSnapshot.child("place").getValue()+"\n,תאריך: "
                        +""+childDataSnapshot.child("datee").getValue());

                    }else {
                        Log.i("datanot", ""+childDataSnapshot.child("place").getValue());
                        Log.i("datanot", ""+childDataSnapshot.child("datee").getValue());
                        Log.i("datanot", ""+childDataSnapshot.child("location").getValue());
                        Log.i("datanot", ""+key);
                        Log.i("datanot", ""+childDataSnapshot.child("key").getValue());

                    }


                }

                listView = (ListView) findViewById(R.id.list);


                // Define a new Adapter
                // First parameter - Context
                // Second parameter - Layout for the row
                // Third parameter - ID of the TextView to which the data is written
                // Forth - the Array of data

                final ArrayAdapter<String> adapter = new ArrayAdapter<String>(DeleteEventsActivity.this,
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


                    }

                });

                listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
                                                   final int pos, long id) {


                        AlertDialog.Builder builder = new AlertDialog.Builder(DeleteEventsActivity.this);
                        builder.setMessage("האם אתה בטוח שברצונך למחוק אירוע זה ?")
                                .setCancelable(false)
                                .setPositiveButton(" כן", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        databaseReference.child(allkeys.get(pos)).removeValue();
                                        allkeys.remove(pos);
                                        allEvents.remove(pos);
                                        eventplace.remove(pos);

                                        listView.invalidateViews();
                                    }
                                })
                                .setNegativeButton("לא", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                });
                        AlertDialog alert = builder.create();
                        alert.show();

                        return true;
                    }
                });


            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add:
                Intent i = new Intent(DeleteEventsActivity.this, AddMemorialEventActivity.class);
                i.putExtra("position", getIntent().getIntExtra("position", 0));
                i.putExtra("data", getIntent().getStringExtra("data"));
                i.putExtra("url", getIntent().getStringExtra("url"));
                startActivity(i);
                return true;

        }
        return true;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.addeve, menu);
        return true;
    }

}
