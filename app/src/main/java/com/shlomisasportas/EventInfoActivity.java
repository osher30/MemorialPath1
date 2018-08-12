package com.shlomisasportas;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class EventInfoActivity extends AppCompatActivity {

    String data;
    TextView tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_info);

        data = getIntent().getStringExtra("event");
        tv = (TextView) findViewById(R.id.info);

        FirebaseDatabase mFirebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference =    mFirebaseDatabase.getReference("allUsers").child(getIntent().getStringExtra("key"));

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String firstname = (String)dataSnapshot.child("firstName").getValue();
                String lastname = (String)dataSnapshot.child("lastName").getValue();

                data =data + "\nשם פרטי:"+firstname+
                        "\nשם משפחה: "+lastname;

                tv.setText(data);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
